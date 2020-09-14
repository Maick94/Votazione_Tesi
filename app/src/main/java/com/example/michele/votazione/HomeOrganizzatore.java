package com.example.michele.votazione;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.michele.votazione.adapters.PagerAdapter2;

public class HomeOrganizzatore extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_organizzatore);

        int n = 0;
        n= (int) getIntent().getSerializableExtra("Numero");

        // TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setupTabIcons();

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter2 adapter = new PagerAdapter2(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout. addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(n!=0){
            viewPager.setCurrentItem(n);
            n=0;
        }
    }

    private void setupTabIcons() {

        TextView tabUno = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabUno.setText("ONE");
        tabUno.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.add, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabUno);

        TextView tabDue = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabDue.setText("TWO");
        tabDue.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.gestione , 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabDue);
    }











}

