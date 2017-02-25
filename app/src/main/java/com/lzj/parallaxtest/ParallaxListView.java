package com.lzj.parallaxtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by LZJ on 2017/2/25.
 */

public class ParallaxListView extends ListView {
    private volatile int orginalheight;
    private ImageView image;
    private int intrinsicHeight;

    public ParallaxListView(Context context) {
        super(context);
        init(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化方法
     *
     * @param context
     */
    private void init(Context context) {
        View header = View.inflate(context, R.layout.header, null);
        addHeaderView(header);

        image = (ImageView) header.findViewById(R.id.header);
        intrinsicHeight = image.getDrawable().getIntrinsicHeight();

        this.post(new Runnable() {
            @Override
            public void run() {
                orginalheight = image.getHeight();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                ValueAnimator animator = ValueAnimator.ofInt(image.getHeight(), orginalheight);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int newheight = (int) animation.getAnimatedValue();
                        updateHeight(newheight);
                    }
                });
                animator.setInterpolator(new OvershootInterpolator());
                animator.setDuration(500);
                animator.start();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 这里防止滑出品目最大高度
     * @param newheight
     */
    private void updateHeight(int newheight) {
        if (newheight < intrinsicHeight) {
            image.getLayoutParams().height = newheight;
            image.requestLayout();
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (deltaY < 0 && isTouchEvent) {
            int newheight = (int) (image.getHeight() + Math.abs(deltaY) / 3.0f);
            updateHeight(newheight);
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
}
