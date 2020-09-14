package com.example.michele.votazione.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.michele.votazione.fragment.GestisciConcorsoFragment;
import com.example.michele.votazione.fragment.AggiungiConcorsoFragment;

/**
 * Created by Michele on 03/03/2020.
 */

public class PagerAdapter2 extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter2(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AggiungiConcorsoFragment tab1 = new AggiungiConcorsoFragment();
                return tab1;
            case 1:
                GestisciConcorsoFragment tab2 = new GestisciConcorsoFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
