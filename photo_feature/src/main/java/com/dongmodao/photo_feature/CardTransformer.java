package com.dongmodao.photo_feature;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * author : tangqihao
 *
 * @time : 2019/1/7
 * @project : AlphaSkill
 */
public class CardTransformer implements ViewPager.PageTransformer {

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