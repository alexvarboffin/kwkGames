package de.meonwax.soundboard.activity

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import de.meonwax.soundboard.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Show action bar to allow user to navigate back
        setSupportActionBar(findViewById<android.view.View?>(R.id.toolbar) as androidx.appcompat.widget.Toolbar?)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        // Set version
        val versionText =
            findViewById<android.view.View?>(R.id.version_text) as android.widget.TextView
        try {
            val version = packageManager.getPackageInfo(packageName, 0).versionName
            versionText.setText(version)
        } catch (e: PackageManager.NameNotFoundException) {
            versionText.visibility = android.view.View.GONE
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): kotlin.Boolean {
        super.onOptionsItemSelected(item)
        when (item.getItemId()) {
            android.R.id.home -> finish()
        }
        return true
    }
}
