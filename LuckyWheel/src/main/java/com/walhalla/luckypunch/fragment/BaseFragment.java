package com.walhalla.luckypunch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    public interface IOnFragmentInteractionListener {
        void replaceFragment(Fragment fragment);

        void startLuckyWheelWithTargetIndex(int index);

        void setLuckyRoundItemSelectedListener(int index);

        void randomNumberRequest();

        void flipCoin();
    }

    protected IOnFragmentInteractionListener mCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IOnFragmentInteractionListener) {
            mCallback = (IOnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement IOnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
