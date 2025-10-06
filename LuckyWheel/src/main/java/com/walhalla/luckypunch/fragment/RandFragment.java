package com.walhalla.luckypunch.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.Randomazer;
import com.walhalla.luckypunch.databinding.RandLayoutBinding;
import com.walhalla.ui.DLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandFragment extends BaseFragment implements SeekBar.OnSeekBarChangeListener,
        RadioGroup.OnCheckedChangeListener {


    private static final int MIN_LIMIT0 = 1;
    private static final int MAX_LIMIT0 = 55;
    private static final String LINE_DIVIDER = "\n";

    //Generator Var
    private int count = MIN_LIMIT0;
    private int start = 1;
    private int end = 1;
    private List<Integer> list = new ArrayList<>();

    RandLayoutBinding mBind;
    private int selection;

    private boolean unique = true;
    private Randomazer r;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        r = Randomazer.newInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBind = DataBindingUtil.inflate(inflater, R.layout.rand_layout, container, false);
        return mBind.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mBind.radio.setOnCheckedChangeListener(this);
        mBind.seekBar1.setOnSeekBarChangeListener(this);
        mBind.seekBar1.setProgress(1);
        mBind.seekBarValue.setText(nonoCount(1));

        mBind.editFrom.setInputType(InputType.TYPE_CLASS_NUMBER);
        mBind.editTo.setInputType(InputType.TYPE_CLASS_NUMBER);

        mBind.editFrom.setText("1");
        mBind.editTo.setText("100");

        mBind.editFrom.setOnFocusChangeListener((view1, hasFocus) -> {
            if (hasFocus) {
                mBind.editFrom.setHint("Placeholder");
            } else {
                mBind.editFrom.setHint("");
            }
        });
        mBind.generateButton.setOnClickListener((v -> {
            _updateRandomNumberResult();
        }));


        //Pre init
        mBind.r1.setChecked(true);

        // Init random Number
        _updateRandomNumberResult();

        List<String> aaa = new ArrayList<>();
        aaa.add("1");
        aaa.add("2");
        aaa.add("3");
        aaa.add("4");
        aaa.add("5");
        //setTag(aaa);

        mBind.uniq.setOnClickListener(v -> {
            unique = mBind.uniq.isChecked();
        });
        if (unique) {
            mBind.uniq.setChecked(true);
        }
    }

//    private void setTag(final List<String> tagList) {
//        for (int index = 0; index < tagList.size(); index++) {
//            final String tagName = tagList.get(index);
//            final Chip chip = new Chip(getActivity());
//            int paddingDp = (int) TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_DIP, 10,
//                    getResources().getDisplayMetrics()
//            );
//            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
//            chip.setText(tagName);
//            //chip.setCloseIconResource(R.drawable.ic_action_navigation_close);
//            chip.setCloseIconEnabled(true);
//            //Added click listener on close icon to remove tag from ChipGroup
//            chip.setOnCloseIconClickListener(v -> {
//                tagList.remove(tagName);
//                mBind.container2.removeView(chip);
//            });
//            mBind.container2.addView(chip);
//        }
//    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        count = _getScaledProgress(seekBar, MIN_LIMIT0, MAX_LIMIT0);
        mBind.seekBarValue.setText(nonoCount(count));
        //mBind.randomNumberRangeText.setText(_getRangeText(MIN_LIMIT, m_currentSliderValue));
    }

    private String nonoCount(int count) {
        if (count < 0) {
            return "=======";
        }
        if (count == 1) {
            return count + " " + getString(R.string.random_number_1);
        }
        return count + " " + getString(R.string.random_number_2);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private String _getRangeText(int min, int max) {
        return "Random number range " + min + " - " + max;
    }

    private int _getScaledProgress(SeekBar seekBar, int min, int max) {
        float progressPercent = seekBar.getProgress() / (float) seekBar.getMax();
        return (int) (min + (progressPercent) * (max - min));
    }

    @SuppressLint("NonConstantResourceId")
    private void _updateRandomNumberResult() {
        mCallback.randomNumberRequest();

        String randomStr = "*";
        StringBuilder sb = new StringBuilder("*");

        switch (selection) {
            case R.id.r1: //from=range
                try {
                    end = Integer.parseInt(mBind.editTo.getText().toString());
                    start = Integer.parseInt(mBind.editFrom.getText().toString());

                    if (end >= start) {
                        sb = new StringBuilder("");
                        if (unique) {
                            List<Integer> picked = r.pickUniqRandom(count, start, end);
                            for (int i = 0; i < picked.size(); i++) {
                                randomStr = String.valueOf(picked.get(i));
                                sb.append(randomStr);
                                if (i < count - 1) {
                                    sb.append(" ");
                                }
                            }
                        } else {
                            for (int i = 0; i < count; i++) {
                                randomStr = String.valueOf(r.randInt(unique, start, end));
                                sb.append(randomStr);
                                if (i < count - 1) {
                                    sb.append(" ");
                                }
                            }
                        }

                        DLog.d("##" + count + "\t" + end + "x" + start + " -->" + randomStr + ", " + sb.toString());
                    }
                } catch (Exception e) {
                    DLog.handleException(e);
                }

                break;

            case R.id.r2://from=list

                String tmp = mBind.container2.getText().toString();
                String[] array = tmp.split(LINE_DIVIDER);
//                List<String> array = new ArrayList<>();

//                for (int i = 0; i < mBind.container2.getChildCount(); i++) {
//                    Chip chip = (Chip) mBind.container2.getChildAt(i);
//
//                    if (chip.isChecked()) {
//                        DLog.d("-->" + chip.getText().toString());
//                        array.add(chip.getText().toString());
//                    }
//                }

                list = new ArrayList<>();
                try {
                    for (String s : array) {
                        list.add(Integer.valueOf(s));
                    }


                    sb = new StringBuilder("");
                    for (int i = 0; i < count; i++) {
                        int tmp0 = getRandomElement(list);
                        sb.append(tmp0);
                        if (i < count - 1) {
                            sb.append(" ");
                        }
                    }
                    //DLog.d("#FROM_LIST" + count + "\t  -->" + randomStr + ", " + sb.toString());

                } catch (Exception e) {
                    DLog.d("@@@@@@@@@@@@@@@@@@@@@@@@");
                }
                break;

            default:
                DLog.d("====");
                break;
        }

        mBind.randomNumberResult.setText(sb.toString());
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {

        switch (id) {

            case R.id.r1:
                selection = R.id.r1;
                mBind.container1.setVisibility(View.VISIBLE);
                mBind.container2.setVisibility(View.GONE);

//                mBind.editFrom.setVisibility(View.VISIBLE);
//                mBind.editTo.setVisibility(View.VISIBLE);
                break;

            case R.id.r2:
                selection = R.id.r2;
                mBind.container2.setVisibility(View.VISIBLE);
                mBind.container1.setVisibility(View.GONE);
//                mBind.editFrom.setVisibility(View.GONE);
//                mBind.editTo.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }


    // Function select an element base on index
    // and return an element
    public int getRandomElement(List<Integer> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
