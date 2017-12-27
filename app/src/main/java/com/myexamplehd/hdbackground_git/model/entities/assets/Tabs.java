package com.myexamplehd.hdbackground_git.model.entities.assets;

/**
 * Created by Максим on 17.12.2017.
 */

public class Tabs {

    private String title;
    private String argument;


    public Tabs(String title, String argument) {
        this.title = title;
        this.argument = argument;
    }

    public String getTitle() {
        return title;
    }

    public String getArgument() {
        return argument;
    }


}
