package com.walhalla.luckypunch.activity;

import android.annotation.SuppressLint;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import com.google.android.gms.ads.AdView;
import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.databinding.ActivityMainBinding;
import com.walhalla.luckypunch.fragment.LuckyWheelFragment;
import com.walhalla.luckypunch.fragment.ViewPagerFragment;
import com.walhalla.ui.DLog;
import com.walhalla.ui.Module_U;

import org.apache.cordova.CompatActivity;
import org.apache.cordova.GConfig;
import org.apache.cordova.domen.BodyClass;
import org.apache.cordova.domen.Dataset;
import org.apache.cordova.enumer.UrlSaver;
import org.apache.cordova.repository.AbstractDatasetRepository;
import org.apache.cordova.repository.impl.FirebaseRepository;

import java.util.List;

public abstract class BaseActivity extends BaseAbstractActivity{


    private boolean doubleBackToExitPressedOnce;
    protected ActivityMainBinding mBind;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            case R.id.action_settings:
//                Intent i = new Intent(getApplicationContext(), ActivitySetting.class);
//                startActivity(i);
//                return true;

//            case R.string.start_test_again:
//                return false;

            case R.id.action_about:
                Module_U.aboutDialog(this);
                return true;

            case R.id.action_privacy_policy:
                Module_U.openBrowser(this, getString(R.string.action_privacy_policy));
                return true;

            case R.id.action_rate_app:
                Module_U.rateUs(this);
                return true;

            case R.id.action_share_app:
                Module_U.shareThisApp(this);
                return true;

            case R.id.action_discover_more_app:
                Module_U.moreApp(this);
                return true;

//            case R.id.action_exit:
//                this.finish();
//                return true;

            case R.id.action_feedback:
                Module_U.feedback(this);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


        //action_how_to_use_app
        //action_support_developer

        //return super.onOptionsItemSelected(item);
    }

    /**
     * Called when leaving the activity
     */
//    @Override
//    public void onPause() {
//        if (mBind.adView != null) {
//            mBind.adView.pause();
//        }
//        super.onPause();
//    }



    /**
     * Called when returning to the activity
     */
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mBind.adView != null) {
//            mBind.adView.resume();
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        if (mBind.adView != null) {
//            mBind.adView.destroy();
//        }
//        super.onDestroy();
//    }

    @Override
    public void onBackPressed() {

        //Pressed back => return to home screen
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(count > 0);
        }
        if (count > 0) {
            fm.popBackStack(fm.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

            //Update GUI
            final List<Fragment> aa = fm.getFragments();
            for (Fragment fragment : aa) {
                if (fragment instanceof LuckyWheelFragment) {
                    try {
                        LuckyWheelFragment aaa = (LuckyWheelFragment) fragment;
                        aaa.updateGUI();
                    } catch (Exception e) {
                        DLog.handleException(e);
                    }
                }
            }
        } else {//count == 0


//                Dialog
//                new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Leaving this App?")
//                        .setMessage("Are you sure you want to close this application?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
            //super.onBackPressed();


            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2100);
        }
    }


    @Override
    protected boolean enableHomeWebView() {
        return true;
    }

    @Override
    public boolean checkRoot() {
        return false;
    }

    @Override
    public boolean checkSignature() {
        return false;
    }
}
