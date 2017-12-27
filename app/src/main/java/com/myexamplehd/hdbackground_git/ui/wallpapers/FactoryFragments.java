package com.myexamplehd.hdbackground_git.ui.wallpapers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Максим on 19.12.2017.
 */

public class FactoryFragments {

    public static List<WallpapersFragment> produceFragments(int quantity){

        List<WallpapersFragment> fragments=new ArrayList<>();

        for (int i = 0; i < quantity; i++) {

            fragments.add(new WallpapersFragment());
        }

        return fragments;
    }

}
