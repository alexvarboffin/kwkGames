package com.walhalla.luckypunch.activity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.fragment.ViewPagerFragment;
import com.walhalla.ui.DLog;

public class MainActivity extends BaseActivity {
    protected SoundPool soundPool1;
    private int disconnect;

    private int bell_ring;
    private int bell_click;
    private SoundPool wheallPool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setVolumeControlStream(3);

        soundPool1 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        wheallPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        disconnect = wheallPool.load(this, R.raw.disconnect, 1);
        bell_ring = soundPool1.load(this, R.raw.bell_ring, 1);
        bell_click = soundPool1.load(this, R.raw.click, 1);

        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBind.toolbar);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ViewPagerFragment());
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }

//        View view = findViewById(R.id.action_open_web1);
//        view.setOnClickListener(v -> {
//
//            StringBuilder sb = new StringBuilder();
//            List<ResolveInfo> aa = WebUtils.getCustomTabsPackages(this);
//            for (ResolveInfo info : aa) {
//                sb.append(info.toString()).append("\n");
//            }
//            //showAlertDialog(sb.toString());
//
//            boolean supported = aa.size() > 0;
//            if (supported) {
//                presenter.customTabsIntentLaunchUrl("https://google.com");
//            }
//        });
//        View view1 = findViewById(R.id.action_open_web2);
//        view1.setOnClickListener(v -> {
//            makeScreen(new Dataset(ScreenType.WEB_VIEW, "https://google.com"));
//        });
//        View view2 = findViewById(R.id.action_open_web3);
//        view2.setOnClickListener(v -> {
//            presenter.launchExternal(this, "https://google.com");
//        });
    }

//    private void showAlertDialog(String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Список пакетов с поддержкой ChromeTab")
//                .setMessage(message)
//                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
//                    dialog.dismiss();
//                    respenter.customTabsIntentLaunchUrl("https://google.com");
//                })
//                .setNegativeButton("Отмена", (dialog, id) -> {
//                    dialog.dismiss();
//                    presenter.customTabsIntentLaunchUrl("https://google.com");
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

//    @Override
//    public void replaceFragment(Fragment fragment) {
//        String backStateName = fragment.getClass().getName();
//        try {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            //ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
//            //ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//            ft.replace(R.id.container, fragment);
//            ft.addToBackStack(backStateName);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            ft.commit();
//        } catch (IllegalStateException e) {
//            DLog.d(e.getMessage());
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            soundPool1.release();
            soundPool1 = null;

            wheallPool.release();
            wheallPool = null;

        } catch (Exception e) {
            DLog.handleException(e);
        }
    }

    public void startLuckyWheelWithTargetIndex(int index) {
        try {
            //---wheallPool.play(disconnect, 1.0f, 1.0f, 0, -1, 1.0f);
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }



    @Override
    public void setLuckyRoundItemSelectedListener(int index) {
        try {
            //@@ sound_pool.play(bell_ring, 1.0f, 1.0f, 0, 0, 1.0f);
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }

    @Override
    public void randomNumberRequest() {
        try {
            soundPool1.play(bell_click, 1.0f, 1.0f, 0, 0, 1.0f);
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }

    @Override
    public void flipCoin() {
        try {
            soundPool1.play(bell_click, 1.0f, 1.0f, 0, 0, 1.0f);
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }
}
