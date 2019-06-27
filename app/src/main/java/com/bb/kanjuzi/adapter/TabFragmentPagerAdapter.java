package com.bb.kanjuzi.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Boby on 2019/6/17.
 */

public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {
    String TAG;
    private Context mContext;
    private ArrayList<Fragment> mFragments;
    private String[] mTitles;

    public void setData(ArrayList<Fragment> fragments, String[] titles) {
        mFragments = fragments;
        mTitles = titles;
        notifyDataSetChanged();
    }

    public TabFragmentPagerAdapter(Context context, FragmentManager fm,String tag) {
        super(fm);
        this.mContext = context;
        this.TAG = tag;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments.get(position);
        if (TAG != null) {
            Log.e(TAG, "getItem: " + position + "---fragment:" + fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
