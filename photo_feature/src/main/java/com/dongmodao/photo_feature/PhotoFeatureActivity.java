package com.dongmodao.photo_feature;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;

import java.util.List;
import java.util.Random;

public class PhotoFeatureActivity extends AppCompatActivity {

    private ViewStub mVsResult;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_feature);

        mVsResult = findViewById(R.id.result_view_stub);
        mVsResult.inflate();
        mVsResult.setVisibility(View.VISIBLE);

        mViewPager = findViewById(R.id.card_view_pager);
        mViewPager.setAdapter(new CardViewpager());
        mViewPager.setOffscreenPageLimit(3);
//        mViewPager.setPageMargin(-20);
        mViewPager.setClipChildren(false);

        ViewGroup view = (ViewGroup) mViewPager.getParent();
        if (view != null) {
            view.setClipChildren(false);
            view.setOnTouchListener((v, event) ->{
//                todo 根据坐标位置更改点击事件
                mViewPager.dispatchTouchEvent(event);
                return true;
            } );
        }

        mViewPager.setPageTransformer(true, new CardTransformer());
        mViewPager.setCurrentItem(0);
    }




    class CardViewpager extends PagerAdapter {

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(PhotoFeatureActivity.this).inflate(R.layout.item_tarot_card, null);
            ImageView imageView = view.findViewById(R.id.iv_content);
            imageView.setBackgroundColor(Color.parseColor(getRandColorCode()));
            view.setOnClickListener(v-> {
                mViewPager.setCurrentItem(position);
                Log.e("---", "instantiateItem: pos = " + position );
            });
            container.addView(view);
            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }

    /**
     * 获取随机颜色，便于区分
     * @return
     */
    public static String getRandColorCode(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;

        return "#" + r+g+b;
    }
}
