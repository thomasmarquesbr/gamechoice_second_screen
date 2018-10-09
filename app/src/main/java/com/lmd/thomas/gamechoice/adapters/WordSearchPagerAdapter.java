package com.lmd.thomas.gamechoice.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.lmd.thomas.gamechoice.ui.wordsearch.gameplay.WordSearchFragment;


public class WordSearchPagerAdapter extends FragmentPagerAdapter {

    private SparseArray<String> mFragmentTags;
    private FragmentManager mFragmentManager;
    private Context mContext;

    public WordSearchPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragmentTags = new SparseArray<String>();
        mFragmentManager = fm;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(mContext, WordSearchFragment.class.getName(), null);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Integer.toString(position);
    }
}