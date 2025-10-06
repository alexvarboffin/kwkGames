package com.walhalla.luckypunch.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.databinding.FragmentTabHolderBinding;

public class ViewPagerFragment extends Fragment {

    private FragmentTabHolderBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
        binding.pager.setAdapter(adapter);
        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selected(position);
            }
        });
        binding.pager.setUserInputEnabled(false);
        binding.tab1.setOnClickListener((v -> {
            binding.pager.setCurrentItem(0);
        }));
        binding.tab2.setOnClickListener((v -> {
            binding.pager.setCurrentItem(1);
        }));
        binding.tab3.setOnClickListener((v -> {
            binding.pager.setCurrentItem(2);
        }));
    }

    private void selected(int position) {
        if (position == 0) {
            binding.tab1.setBackgroundResource(R.drawable.gradient_control_selected);
            binding.tab2.setBackgroundResource(R.drawable.gradient_control_normal);
            binding.tab3.setBackgroundResource(R.drawable.gradient_control_normal);
        } else if (position == 1) {
            binding.tab1.setBackgroundResource(R.drawable.gradient_control_normal);
            binding.tab2.setBackgroundResource(R.drawable.gradient_control_selected);
            binding.tab3.setBackgroundResource(R.drawable.gradient_control_normal);
        } else {
            binding.tab1.setBackgroundResource(R.drawable.gradient_control_normal);
            binding.tab2.setBackgroundResource(R.drawable.gradient_control_normal);
            binding.tab3.setBackgroundResource(R.drawable.gradient_control_selected);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_holder, container, false);
        return binding.getRoot();
    }


    static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new RandFragment();
            } else if (position == 1) {
                return LuckyWheelFragment.newInstance(0);
            } else {
                return new CoinFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
