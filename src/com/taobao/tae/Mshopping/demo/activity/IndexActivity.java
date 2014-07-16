package com.taobao.tae.Mshopping.demo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.taobao.tae.Mshopping.demo.R;
import com.taobao.tae.Mshopping.demo.adapter.FragmentPagerAdapter;
import com.taobao.tae.Mshopping.demo.constant.Constants;
import com.taobao.tae.Mshopping.demo.fegment.ItemsListFragment;

import java.util.ArrayList;

public class IndexActivity extends FragmentActivity {
    private static final String TAG = "IndexActivity";
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentsList;
    private RadioButton tvTabNew, tvTabSelect, tvTabFashion;
    private int param = 1;
    private int currIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.index_activity);
        initTextView();
        initViewPager();
    }

    private void initTextView() {
        tvTabNew = (RadioButton) findViewById(R.id.tv_tab_new);
        tvTabSelect = (RadioButton) findViewById(R.id.tv_tab_select);
        tvTabFashion = (RadioButton) findViewById(R.id.tv_tab_fashion);


        tvTabNew.setOnClickListener(new MyOnClickListener(0));
        tvTabSelect.setOnClickListener(new MyOnClickListener(1));
        tvTabFashion.setOnClickListener(new MyOnClickListener(2));
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vPager);
        fragmentsList = new ArrayList<Fragment>();
        Fragment newFragment = ItemsListFragment.newInstance(Constants.NEW_CATEGORY);
        Fragment selectFragment = ItemsListFragment.newInstance(Constants.SELECT_CATEGORY);
        Fragment fashionFragment = ItemsListFragment.newInstance(Constants.FASHION_CATEGORY);

        fragmentsList.add(newFragment);
        fragmentsList.add(selectFragment);
        fragmentsList.add(fashionFragment);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }


    public class MyOnClickListener implements OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    }

    ;

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:

                    if (currIndex == 1) {
                        tvTabSelect.setTextColor(getResources().getColor(R.color.black));
                        tvTabSelect.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tvTabSelect.setCompoundDrawablesWithIntrinsicBounds(
                                getResources().getDrawable(R.drawable.special_icon), null, null, null);
                    } else if (currIndex == 2) {
                        tvTabFashion.setTextColor(getResources().getColor(R.color.black));
                        tvTabFashion.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tvTabFashion.setCompoundDrawablesWithIntrinsicBounds(
                                getResources().getDrawable(R.drawable.mei_icon), null, null, null);
                    }
                    tvTabNew.setTextColor(getResources().getColor(R.color.pink));
                    tvTabNew.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    tvTabNew.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(R.drawable.fashion_icon_s), null, null, null);
                    break;
                case 1:

                    if (currIndex == 0) {
                        tvTabNew.setTextColor(getResources().getColor(R.color.black));
                        tvTabNew.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tvTabNew.setCompoundDrawablesWithIntrinsicBounds(
                                getResources().getDrawable(R.drawable.fashion_icon), null, null, null);

                    } else if (currIndex == 2) {
                        tvTabFashion.setTextColor(getResources().getColor(R.color.black));
                        tvTabFashion.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tvTabFashion.setCompoundDrawablesWithIntrinsicBounds(
                                getResources().getDrawable(R.drawable.mei_icon), null, null, null);
                    }
                    tvTabSelect.setTextColor(getResources().getColor(R.color.pink));
                    tvTabSelect.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    tvTabSelect.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(R.drawable.special_icon_s), null, null, null);
                    break;
                case 2:
                    if (currIndex == 0) {
                        tvTabNew.setTextColor(getResources().getColor(R.color.black));
                        tvTabNew.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tvTabNew.setCompoundDrawablesWithIntrinsicBounds(
                                getResources().getDrawable(R.drawable.fashion_icon), null, null, null);

                    } else if (currIndex == 1) {
                        tvTabSelect.setTextColor(getResources().getColor(R.color.black));
                        tvTabSelect.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tvTabSelect.setCompoundDrawablesWithIntrinsicBounds(
                                getResources().getDrawable(R.drawable.special_icon), null, null, null);

                    }
                    tvTabFashion.setTextColor(getResources().getColor(R.color.pink));
                    tvTabFashion.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    tvTabFashion.setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(R.drawable.mei_icon_s), null, null, null);
                    break;
            }


            currIndex = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}
