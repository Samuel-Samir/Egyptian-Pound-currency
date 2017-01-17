package com.example.android.dollar.Fragments;


import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;
    final int[] ICONS = new int[]{
            R.drawable.bank,
            R.drawable.bestprice
            };
    public BankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_bank, container, false);
            tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
            viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

            viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    tabLayout.setupWithViewPager(viewPager);
                }
            });
        /*
        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);*/

        return rootView ;
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new BankPricesFragment();
                case 1 : return new BestPricesFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {


            switch (position){
                case 0 :
                    return getContext().getString(R.string.bank);
                case 1 :
                    return getContext().getString(R.string.bestPrices);
//context.getString(R.string.COOL_STRING)
            }
            return null;
        }
    }
}
