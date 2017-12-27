package com.myexamplehd.hdbackground_git.ui.wallpapers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    private ArrayList<Fragment> fragmentList=new ArrayList<>();
    private ArrayList<String> titleList=new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


    public  void addFragment(Fragment fragment, String title){

         fragmentList.add(fragment);
         titleList.add(title);

    }




}
