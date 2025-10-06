package com.walhalla.luckypunch.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.fragment.CoinFragment;

public class CoinActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBind.toolbar);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new CoinFragment());
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void startLuckyWheelWithTargetIndex(int index) {

    }

    @Override
    public void setLuckyRoundItemSelectedListener(int index) {

    }

    @Override
    public void randomNumberRequest() {

    }

    @Override
    public void flipCoin() {

    }
}
