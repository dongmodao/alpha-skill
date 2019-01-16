package com.dongmodao.photo_feature.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * author : tangqihao
 * @time : 2019/1/16
 */
// PagerAdapter.instantiateItem 设定 view.setTag(position);
public class CardViewPager extends ViewPager {

    private static final float DISTANCE = 10;
    private float downX = 0;
    private float downY = 0;


    public CardViewPager(@NonNull Context context) {
        this(context, null);
    }

    public CardViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void init() {
        try {
            setOffscreenPageLimit(3);
            setClipChildren(false);
            setPageTransformer(true, new CardViewPager.CardTransformer());
            getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                ViewGroup view = (ViewGroup) getParent();
                if (view != null) {
                    view.setClipChildren(false);
                    view.setOnTouchListener((v, event) ->{
                        dispatchTouchEvent(event);
                        return true;
                    } );
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            downX = ev.getX();
            downY = ev.getY();
        }else if (ev.getAction() == MotionEvent.ACTION_UP) {

            float upX = ev.getX();
            float upY = ev.getY();
            // touch
            if(Math.abs(upX - downX) > DISTANCE || Math.abs(upY - downY) > DISTANCE){
                return super.dispatchTouchEvent(ev);
            }
            // click
            View view = viewOfClickOnScreen(ev);
            if (view != null) {
                int index = (Integer) view.getTag();
                if (getCurrentItem() != index) {
                    setCurrentItem(index);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private View viewOfClickOnScreen(MotionEvent ev) {
        int childCount = getChildCount();
        int[] location = new int[2];

        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.getLocationOnScreen(location);
            int minX = location[0];
            int minY = location[1];
            int maxX = location[0] + v.getWidth();
            int maxY = location[1] + v.getHeight();
            float x = ev.getRawX();
            float y = ev.getRawY();
            if ((x > minX && x < maxX) && (y > minY && y < maxY)) {
                return v;
            }
        }
        return null;
    }

    public static class CardTransformer implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.85f;

        @Override
        public void transformPage(View page, float position) {

            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

            if (position < 0) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if (position >= 0 && position < 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if (position >= 1) {
                page.setScaleX(scaleFactor);
            }
        }
    }
}
