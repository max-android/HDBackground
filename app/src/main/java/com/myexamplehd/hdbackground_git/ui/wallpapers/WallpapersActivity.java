package com.myexamplehd.hdbackground_git.ui.wallpapers;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.myexamplehd.hdbackground_git.R;
import com.myexamplehd.hdbackground_git.dependencies.App;
import com.myexamplehd.hdbackground_git.model.entities.net.HDBackgrounds;
import com.myexamplehd.hdbackground_git.presenter.WallpapersPresenter;
import com.myexamplehd.hdbackground_git.ui.utilities.Constants;
import com.myexamplehd.hdbackground_git.ui.utilities.DataHolder;
import com.myexamplehd.hdbackground_git.ui.utilities.Info;
import com.myexamplehd.hdbackground_git.ui.utilities.NetInspector;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class WallpapersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String theme;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<WallpapersFragment> fragments = new ArrayList<>();
    private Info info;
    private List<HDBackgrounds> save;

    @Inject
    WallpapersPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);

        App.getAppComponent().injectWallpapersActivity(this);

        initComponents();

        presenter.setTransmitter(this::sendDataIntoFrag);
        presenter.setError(this::showError);

        if(DataHolder.getInstance().getDataList().isEmpty()){
            if (NetInspector.isOnline(this)) {
                firstLaunchHDBackground();
            } else {info.showMessage(getString(R.string.not_internet));}
        }else {
            if (NetInspector.isOnline(this)) {
                sendDataIntoFrag(DataHolder.getInstance().getDataList());
                DataHolder.getInstance().getDataList().clear();
            } else {
                info.showMessage(getString(R.string.not_internet));}}
    }

   private void initComponents(){

       Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolBar);
       setSupportActionBar(toolbar);
       getSupportActionBar().setTitle(" ");
               drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
               this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawer.addDrawerListener(toggle);
       toggle.syncState();

       NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);

      tabLayout=(TabLayout)findViewById(R.id.tab);

       viewPager=(ViewPager)findViewById(R.id.viewPager);
              info=new Info(findViewById(R.id.coordinat),this);
       save=new ArrayList<>();
   }

    @Override
    public void onBackPressed() {
        navigationBackPressed();
    }

    private void navigationBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.nav_nature:
                theme = Constants.NATURE;
                presenter.attach(()->theme);
                break;

            case R.id.nav_tech:
                theme = Constants.TECH;
                presenter.attach(()->theme);
                break;

            case R.id.nav_animals:
                theme = Constants.ANIMALS;
                presenter.attach(()->theme);
                break;

            case R.id.nav_arch:

                theme = Constants.ARCH;
                presenter.attach(()->theme);
                break;

            case R.id.nav_art:
                theme = Constants.ART;
                presenter.attach(()->theme);
                break;

            case R.id.nav_background:
                theme = Constants.BACKGROUND;
                presenter.attach(()->theme);
                break;

            case R.id.nav_eat:
                theme = Constants.EAT;
                presenter.attach(()->theme);
                break;

            case R.id.nav_seasons:
                theme = Constants.SEASONS;
                presenter.attach(()->theme);
                break;

            case R.id.nav_holidays:
                theme = Constants.HOLIDAYS;
                presenter.attach(()->theme);
                break;

            case R.id.nav_religion:
                theme = Constants.RELIGION;
                presenter.attach(()->theme);
                break;

            case R.id.nav_anime:
                theme = Constants.ANIME;
                presenter.attach(()->theme);
                break;

            case R.id.nav_celebrities:
                theme = Constants.CELEBRITIES;
                presenter.attach(()->theme);
                break;

            case R.id.nav_brands:
                theme = Constants.BRANDS;
                presenter.attach(()->theme);
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   private void firstLaunchHDBackground(){

       theme = Constants.NATURE;

       presenter.attach(()->theme);
   }

    private void sendDataIntoFrag(List<HDBackgrounds> result){

        saveData(result);

        if(!fragments.isEmpty()){
            fragments.clear();
        }

        fragments.addAll(FactoryFragments.produceFragments(result.size()));

        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

        for (int i = 0; i < result.size() ; i++) {

            Bundle bundle=new Bundle();

            bundle.putParcelableArrayList(String.valueOf(i),new ArrayList<>(result.get(i).getWallpapersList()));

            fragments.get(i).setArguments(bundle);

            viewPagerAdapter.addFragment(fragments.get(i),result.get(i).getTitle());
        }

        viewPager.setAdapter(viewPagerAdapter);

       tabLayout.setupWithViewPager(viewPager);
    }

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void showError(String error){
        if(!(error.equals(Constants.DEFAULT))){
            showMessage(error);
        }}

    private void saveData(List<HDBackgrounds> result){

        if(save.isEmpty()){
            save.addAll(result);}else {
            save.clear();
            save.addAll(result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataHolder.getInstance().setDataList(save);
    }

}
