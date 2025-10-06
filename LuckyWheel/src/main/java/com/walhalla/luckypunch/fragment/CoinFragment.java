package com.walhalla.luckypunch.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.databinding.CoinLayoutBinding;

import java.util.Random;

public class CoinFragment extends BaseFragment {
    private static final long ANIM_DURATION = 500;
    private boolean isHeadsSideVisible = true;
    private boolean isHeads = true;

    private CoinLayoutBinding mBind;

    private ObjectAnimator rotateAnimator;
    private ObjectAnimator rotateAnimator1;
    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBind = DataBindingUtil.inflate(inflater, R.layout.coin_layout, container, false);
        return mBind.getRoot();
    }

    public static final Random RANDOM = new Random();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBind.btn.setOnClickListener(view1 -> flipCoin());
    }

    private void flipCoin() {
        mCallback.flipCoin();
        //Создаем анимацию вращения
        rotateAnimator1 = ObjectAnimator.ofFloat(mBind.headsImageView, "rotationY", 0f, 360f);
        rotateAnimator1.setDuration(ANIM_DURATION);
        rotateAnimator1.setRepeatCount(4);
        rotateAnimator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                setResult();

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(3000);
                fadeIn.setFillAfter(true);
                mBind.headsImageView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setResult();
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(3000);
                fadeIn.setFillAfter(true);
                mBind.headsImageView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.coin_flip_animation);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mBind.headsImageView.setImageResource(RANDOM.nextFloat() > 0.5f
//                        ? R.drawable.ic_tails : R.drawable.ic_head);
//
////                Animation fadeIn = new AlphaAnimation(0, 1);
////                fadeIn.setInterpolator(new DecelerateInterpolator());
////                fadeIn.setDuration(3000);
////                fadeIn.setFillAfter(true);
////                mBind.headsImageView.startAnimation(fadeIn);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

//        ObjectAnimator rotateAnimator1 = ObjectAnimator.ofFloat(mBind.headsImageView, "rotationY", 0f, 360f);
//        rotateAnimator1.setDuration(1000); // Длительность анимации в миллисекундах
//        rotateAnimator1.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(@NonNull Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(@NonNull Animator animation) {
//                mBind.headsImageView.setImageResource(RANDOM.nextFloat() > 0.5f
//                        ? R.drawable.ic_tails : R.drawable.ic_head);
//
//                Animation fadeIn = new AlphaAnimation(0, 1);
//                fadeIn.setInterpolator(new DecelerateInterpolator());
//                fadeIn.setDuration(3000);
//                fadeIn.setFillAfter(true);
//                mBind.headsImageView.startAnimation(fadeIn);
//            }
//
//            @Override
//            public void onAnimationCancel(@NonNull Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(@NonNull Animator animation) {
//
//            }
//        });

//        ObjectAnimator rotateAnimator1 = ObjectAnimator.ofFloat(mBind.headsImageView, "rotationY", 0f, 360f);
//        rotateAnimator1.setDuration(500);

//        ObjectAnimator rotateAnimator2 = ObjectAnimator.ofFloat(mBind.headsImageView, "rotationY", 0f, 360f);
//        rotateAnimator2.setDuration(700);
//        ObjectAnimator rotateAnimator3 = ObjectAnimator.ofFloat(mBind.headsImageView, "rotationY", 0f, 360f);
//        rotateAnimator3.setDuration(400);


//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(rotateAnimator1, fadeOut);
//        animatorSet.start();
//        rotateAnimator1.start();

        // Переключаем видимость между сторонами монеты
//        if (isHeadsSideVisible) {
//            mBind.headsImageView.setVisibility(View.INVISIBLE);
//            mBind.tailsImageView.setVisibility(View.VISIBLE);
//        } else {
//            mBind.headsImageView.setVisibility(View.VISIBLE);
//            mBind.tailsImageView.setVisibility(View.INVISIBLE);
//        }
//        isHeadsSideVisible = !isHeadsSideVisible;
        rotateAnimator = ObjectAnimator.ofFloat(mBind.coinText, "rotationY", 0f, 360f);
        rotateAnimator.setDuration(ANIM_DURATION); // Длительность анимации в миллисекундах
        rotateAnimator.setRepeatCount(4);
        rotateAnimator.start();
        rotateAnimator1.start();

        // Циклически меняем текст между "HEADS" и "TAILS"
        handler = new Handler();
        handler.postDelayed(() -> {
            isHeads = !isHeads;
            mBind.coinText.setText(isHeads ? R.string.heads : R.string.tails);
        }, 500); // Задержка 500 миллисекунд (равная длительности анимации)

        //mBind.headsImageView.startAnimation(fadeOut);
    }

    private void setResult() {
        float mm = RANDOM.nextFloat();
        int res = mm > 0.5f ? R.string.tails : R.string.heads;
        //int result = mm > 0.5f ? R.drawable.ic_tails : R.drawable.ic_head;
        //mBind.headsImageView.setImageResource(result);
        mBind.coinText.setText(res);
    }

    @Override
    public void onResume() {
        super.onResume();
        setResult();
    }
}