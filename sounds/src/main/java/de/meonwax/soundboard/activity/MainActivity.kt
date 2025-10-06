package de.meonwax.soundboard.activity

import android.Manifest
import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import de.meonwax.soundboard.R
import de.meonwax.soundboard.filepicker.FilePickerDialogFragment
import de.meonwax.soundboard.filepicker.dir.Directory
import de.meonwax.soundboard.sound.Sound
import de.meonwax.soundboard.sound.SoundAdapter
import de.meonwax.soundboard.sound.SoundPoolBuilder
import de.meonwax.soundboard.util.FileUtils
import java.io.File

class MainActivity : AppCompatActivity() {
    private var hasRequestedPermissions = false

    private var soundPool: SoundPool? = null
    private var sounds: MutableList<Sound>? = null
    private var soundAdapter: SoundAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<View?>(R.id.toolbar) as Toolbar?)
        init()

        // We were called by a view intent from another app
        if (Intent.ACTION_VIEW == getIntent().getAction()) {
            onSendFile(getIntent())
        }
    }

    private fun init() {
        // Create sound pool
        soundPool = SoundPoolBuilder.build()

        // Init sound adapter
        sounds = ArrayList<Sound>()
        soundAdapter = SoundAdapter(this, sounds)
        val soundList = findViewById<View?>(R.id.sound_list) as ListView
        soundList.setAdapter(soundAdapter)

        // Populate sound files
        val soundFiles = FileUtils.getInternalFiles(this)
        if (!soundFiles.isEmpty()) {
            for (file in soundFiles) {
                addSound(file)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Check for runtime permissions on supported devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkForPermission()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun checkForPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!hasRequestedPermissions) {
                hasRequestedPermissions = true
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_ASK_PERMISSIONS
                )
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Workaround for bug
                    // https://code.google.com/p/android-developer-preview/issues/detail?id=2982
                    Process.killProcess(Process.myPid())
                } else {
                    AlertDialog.Builder(this)
                        .setMessage(
                            Html.fromHtml(
                                getString(
                                    R.string.error_permission_denied,
                                    getString(R.string.app_name)
                                )
                            )
                        )
                        .setPositiveButton(R.string.button_ok, null)
                        .setOnDismissListener(object : DialogInterface.OnDismissListener {
                            override fun onDismiss(dialog: DialogInterface?) {
                                finish()
                            }
                        })
                        .show()
                }
                return
            }
        }
        hasRequestedPermissions = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_new -> {
                val filePickerFragment: DialogFragment = FilePickerDialogFragment()
                filePickerFragment.show(getSupportFragmentManager(), "filePicker")
            }

            R.id.action_remove_all -> removeAll()
            R.id.action_info -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }

            R.id.action_exit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onDirectoryAdded(directory: Directory?) {
        var fileCount = 0
        if (directory != null) {
            for (file in directory.files) {
                if (onFileAdded(file)) {
                    fileCount++
                }
            }
        }
        Toast.makeText(
            this,
            getString(R.string.import_directory_info, "$fileCount"),
            Toast.LENGTH_LONG
        ).show()
    }

    fun onFileAdded(file: File): Boolean {
        val internalPath = FileUtils.getInternalPath(this, file)
        if (internalPath != null && !FileUtils.existsInternalFile(this, file.getName()) &&
            FileUtils.isWhitelisted(file)
        ) {
            FileUtils.copyToInternal(this, file)
            addSound(file)
            return true
        }
        return false
    }

    private fun onSendFile(intent: Intent) {
        if (intent.getType() != null && intent.getType()!!.startsWith("audio/")) {
            val newFile = File(intent.getData()!!.getPath())
            if (FileUtils.existsInternalFile(this, newFile.getName())) {
                Toast.makeText(this, getString(R.string.error_entry_exists), Toast.LENGTH_LONG)
                    .show()
            } else if (!(onFileAdded(File(intent.getData()!!.getPath())))) {
                Toast.makeText(
                    this,
                    getString(R.string.error_add, intent.getData()!!.getPath()),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun addSound(soundFile: File) {
        // Load the sound file

        val soundId = soundPool!!.load(soundFile.getAbsolutePath(), 1)

        // Create a new sound object and add it to the adapter
        sounds!!.add(Sound(soundId, soundFile.getName()))
        soundAdapter!!.notifyDataSetChanged()
    }

    fun playSound(soundId: Int) {
        soundPool!!.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    private fun removeAll() {
        AlertDialog.Builder(this)
            .setMessage(Html.fromHtml(getString(R.string.confirm_remove_all)))
            .setPositiveButton(R.string.button_ok, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    for (i in sounds!!.indices.reversed()) {
                        removeSound(i)
                    }
                }
            })
            .setNegativeButton(R.string.button_cancel, null)
            .show()
    }

    fun removeSound(position: Int) {
        // Remove from adapter

        val sound = sounds!!.removeAt(position)
        soundAdapter!!.notifyDataSetChanged()

        // Unload from sound pool
        soundPool!!.unload(sound.getId())

        // Delete from filesystem
        val internalPath = FileUtils.getInternalPath(this, File(sound.getName()))
        if (internalPath == null || !File(internalPath).delete()) {
            Toast.makeText(this, getString(R.string.error_remove), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val REQUEST_CODE_ASK_PERMISSIONS = 123
    }
}
