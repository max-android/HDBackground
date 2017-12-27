package com.myexamplehd.hdbackground_git.model.entities.net;

import java.util.List;

/**
 * Created by Максим on 17.12.2017.
 */

public class HDBackgrounds {

    private String title;
    private List<Wallpapers> wallpapersList;


    public HDBackgrounds(String title, List<Wallpapers> wallpapersList) {
        this.title = title;
        this.wallpapersList = wallpapersList;
    }

    public String getTitle() {
        return title;
    }

    public List<Wallpapers> getWallpapersList() {
        return wallpapersList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWallpapersList(List<Wallpapers> wallpapersList) {
        this.wallpapersList = wallpapersList;
    }
}
