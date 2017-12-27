package com.myexamplehd.hdbackground_git.model.entities.assets;

import java.util.ArrayList;

/**
 * Created by Максим on 17.12.2017.
 */

public class TypesWallpapers {

private String title;
private ArrayList<Tabs> tabs;


    public TypesWallpapers(String title, ArrayList<Tabs> tabs) {
        this.title = title;
        this.tabs = tabs;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Tabs> getTabs() {
        return tabs;
    }

}
