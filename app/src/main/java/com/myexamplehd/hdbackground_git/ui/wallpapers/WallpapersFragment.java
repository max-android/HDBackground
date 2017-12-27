package com.myexamplehd.hdbackground_git.ui.wallpapers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bumptech.glide.RequestManager;
import com.myexamplehd.hdbackground_git.R;
import com.myexamplehd.hdbackground_git.dependencies.App;
import com.myexamplehd.hdbackground_git.model.entities.net.Wallpapers;
import com.myexamplehd.hdbackground_git.ui.paperhanging.PaperhangingActivity;
import com.myexamplehd.hdbackground_git.ui.utilities.Constants;
import com.myexamplehd.hdbackground_git.ui.utilities.NetInspector;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by Максим on 19.12.2017.
 */

public class WallpapersFragment extends Fragment implements WallpapersAdapter.WallpapersClickListener {

    private RecyclerView wallpapersRecycler;
    private ProgressBar progressBar;
    private ArrayList<Wallpapers> wallpapers;

    @Inject
    RequestManager requestManager;

    public WallpapersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_wallpapers,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);

        setDataIntoList();

    }

    private void initComponents(View view){

        App.getAppComponent().injectWallpapersFragments(this);

        wallpapersRecycler=(RecyclerView)view.findViewById(R.id.wallpapersRecycler);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
         wallpapersRecycler.setLayoutManager(gridLayoutManager);

        wallpapersRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
       progressBar=(ProgressBar) view.findViewById(R.id.imageList);
         wallpapers = new ArrayList<>();
    }

    private void setDataIntoList(){

        for(String key:producerKeys()){

            if(getArguments().containsKey(key)){

             wallpapers = getArguments().getParcelableArrayList(key);

             break;
            }
        }

        wallpapersRecycler.setAdapter(new WallpapersAdapter(wallpapers,requestManager,this));
        progressBar.setVisibility(View.INVISIBLE);
    }

    private ArrayList<String> producerKeys(){

        ArrayList<String> keys = new ArrayList<>();

        keys.add("0");
        keys.add("1");
        keys.add("2");
        keys.add("3");
        keys.add("4");
        keys.add("5");
        keys.add("6");
        keys.add("7");
        keys.add("8");
        keys.add("9");

        return keys;
    }

    @Override
    public void onWallpaperClick(Wallpapers wallpaper) {

        if(NetInspector.isOnline(getContext())){
            launchPaperhanging(wallpaper);
        }else {
            showHelp(getString(R.string.not_internet));
        }
    }

    private void showHelp(String message) {

        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    private void launchPaperhanging(Wallpapers wallpaper){
        Intent paperhangingIntent = new Intent(getContext(),PaperhangingActivity.class);
        paperhangingIntent.putExtra(Constants.PAPER,wallpaper.getUrl());
        startActivity(paperhangingIntent);
    }

}
