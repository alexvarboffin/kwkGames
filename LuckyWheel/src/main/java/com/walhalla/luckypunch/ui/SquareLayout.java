package com.walhalla.luckypunch.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareLayout extends RelativeLayout {

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, (int) (widthMeasureSpec * 1.5));
//    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        int size;
//        if (widthMode == MeasureSpec.EXACTLY && widthSize > 0) {
//            size = widthSize;
//        } else if (heightMode == MeasureSpec.EXACTLY && heightSize > 0) {
//            size = heightSize;
//        } else {
//            size = Math.min(widthSize, heightSize);
//        }
//        int finalMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
//        super.onMeasure(finalMeasureSpec, finalMeasureSpec);
//    }
}
