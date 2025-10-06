package com.walhalla.luckypunch.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.databinding.LuckywheelLayoutBinding;
import com.walhalla.luckypunch.db.DatabaseManager;
import com.walhalla.boilerplate.domain.executor.impl.BackgroundExecutor;
import com.walhalla.boilerplate.threading.MainThreadImpl;
import com.walhalla.ui.DLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rubikstudio.library.model.LuckyItem;

public class LuckyWheelFragment extends BaseFragment {

    private LuckywheelLayoutBinding mBind;
    private final List<LuckyItem> data = new ArrayList<>();
    private DatabaseManager databaseManager;


    public static Fragment newInstance(int i) {
        return new LuckyWheelFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = new DatabaseManager(BackgroundExecutor.getInstance(), MainThreadImpl.getInstance());
        DLog.d("");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBind = DataBindingUtil.inflate(inflater, R.layout.luckywheel_layout, container, false);
        return mBind.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DLog.d("");

//        LuckyItem luckyItem4 = new LuckyItem();
//        luckyItem4.topText = "400";
//        luckyItem4.icon = R.raw.test4;
//        luckyItem4.color = 0xffFFF3E0;
//        data.add(luckyItem4);
//
//        LuckyItem luckyItem5 = new LuckyItem();
//        luckyItem5.topText = "500";
//        luckyItem5.icon = R.raw.test5;
//        luckyItem5.color = 0xffFFE0B2;
//        data.add(luckyItem5);
//
//        LuckyItem luckyItem6 = new LuckyItem();
//        luckyItem6.topText = "600";
//        luckyItem6.icon = R.raw.test6;
//        luckyItem6.color = 0xffFFCC80;
//        data.add(luckyItem6);
//        //////////////////
//
//        //////////////////////
//        LuckyItem luckyItem7 = new LuckyItem();
//        luckyItem7.topText = "700";
//        luckyItem7.icon = R.raw.test7;
//        luckyItem7.color = 0xffFFF3E0;
//        data.add(luckyItem7);
//
//        LuckyItem luckyItem8 = new LuckyItem();
//        luckyItem8.topText = "800";
//        luckyItem8.icon = R.raw.test8;
//        luckyItem8.color = 0xffFFE0B2;
//        data.add(luckyItem8);
//
//
//        LuckyItem luckyItem9 = new LuckyItem();
//        luckyItem9.topText = "900";
//        luckyItem9.icon = R.raw.test9;
//        luckyItem9.color = 0xffFFCC80;
//        data.add(luckyItem9);
//        ////////////////////////
//
//        LuckyItem luckyItem10 = new LuckyItem();
//        luckyItem10.topText = "1000";
//        luckyItem10.icon = R.raw.test10;
//        luckyItem10.color = 0xffFFE0B2;
//        data.add(luckyItem10);
//
//        LuckyItem luckyItem11 = new LuckyItem();
//        luckyItem11.topText = "2000";
//        luckyItem11.icon = R.raw.test10;
//        luckyItem11.color = 0xffFFE0B2;
//        data.add(luckyItem11);
//
//        LuckyItem luckyItem12 = new LuckyItem();
//        luckyItem12.topText = "3000";
//        luckyItem12.icon = R.raw.test10;
//        luckyItem12.color = 0xffFFE0B2;
//        data.add(luckyItem12);

        /////////////////////

//        mBind.luckyWheel.setData(data);
//        mBind.luckyWheel.setRound(5);

        /*luckyWheelView.setLuckyWheelBackgrouldColor(0xff0000ff);
        luckyWheelView.setLuckyWheelTextColor(0xffcc0000);
        luckyWheelView.setLuckyWheelCenterImage(getResources().getDrawable(R.drawable.icon));
        luckyWheelView.setLuckyWheelCursorImage(R.drawable.ic_cursor);*/

        mBind.play.setOnClickListener(view0 -> {
            int index = getRandomIndex(data);
            if (index == -1) {
                DLog.d("onViewCreated: disabled");
                return;
            }
            mCallback.startLuckyWheelWithTargetIndex(index);
            mBind.luckyWheel.startLuckyWheelWithTargetIndex(index);
        });

        mBind.luckyWheel.setLuckyRoundItemSelectedListener(index -> {
            mCallback.setLuckyRoundItemSelectedListener(index);
            Toast.makeText(getContext(), "" + data.get(index).topText, Toast.LENGTH_SHORT).show();
        });

        mBind.settings.setOnClickListener(v -> {
            mCallback.replaceFragment(new ItemListFragment());
        });
    }

    public static final Random RANDOM = new Random();

    private int getRandomIndex(List<LuckyItem> data) {
        if (data.size() == 1) {
            return 0;
        }
        int index = data.size() - 1;
        if (index <= 0) {
            return -1;
        }
        return RANDOM.nextInt(index);
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateGUI();
    }

    public void updateGUI() {
        databaseManager.loadNotesList0(items -> {
            if (!items.isEmpty()) {
                data.clear();
                data.addAll(items);
                mBind.luckyWheel.setData(data);
                mBind.luckyWheel.setRound(5);
                mBind.luckyWheel.isTouchEnabled();

                //mBind.luckyWheel.startLuckyWheelWithRandomTarget();
                mBind.luckyWheel.setBorderColor(Color.parseColor("#ff0000"));
                //mBind.luckyWheel.setLuckyWheelCenterImage(getResources().getDrawable(R.drawable.butterfly));
            } else {
                DLog.d("EMPTY->" + items.isEmpty());
            }
        });
    }
}
