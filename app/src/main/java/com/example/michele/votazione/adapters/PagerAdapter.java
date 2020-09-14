package com.example.michele.votazione.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.michele.votazione.fragment.LoginFragment;
import com.example.michele.votazione.fragment.VisualizzaConcorsiAttiviFragment;
import com.example.michele.votazione.fragment.VisualizzaConcorsiChiusiStatisticheFragment;

/**
 * Created by Michele on 03/03/2020.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                VisualizzaConcorsiAttiviFragment tab1 = new VisualizzaConcorsiAttiviFragment();
                return tab1;
            case 1:
                VisualizzaConcorsiChiusiStatisticheFragment tab2 = new VisualizzaConcorsiChiusiStatisticheFragment();
                return tab2;
            case 2:
                LoginFragment tab3 = new LoginFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}