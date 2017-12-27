package com.myexamplehd.hdbackground_git.presenter;

import android.util.Log;
import com.myexamplehd.hdbackground_git.model.entities.assets.Tabs;
import com.myexamplehd.hdbackground_git.model.entities.assets.TypesWallpapers;
import com.myexamplehd.hdbackground_git.model.entities.net.HDBackgrounds;
import com.myexamplehd.hdbackground_git.model.service.AssetsParser;
import com.myexamplehd.hdbackground_git.model.service.WallpapersService;
import com.myexamplehd.hdbackground_git.ui.callbacks.GetFunc;
import com.myexamplehd.hdbackground_git.ui.callbacks.SetFunc;
import com.myexamplehd.hdbackground_git.ui.utilities.Constants;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;


/**
 * Created by Максим on 17.12.2017.
 */

public class WallpapersPresenter implements WallpapersScreenPresenter {

    private TransmitterDataFromPresenter transmitter;
    private TransmitterErrorFromPresenter mistake;

    @Inject
    AssetsParser parser;

    @Inject
    WallpapersService service;

    public WallpapersPresenter(AssetsParser parser, WallpapersService service) {
        this.parser = parser;
        this.service = service;

    }

    public void setTransmitter(TransmitterDataFromPresenter transmitter) {
        this.transmitter = transmitter;
    }


    public void setError(TransmitterErrorFromPresenter mistake) {
        this.mistake = mistake;
    }

    @Override
    public void attach(GetFunc<String> data) {

        String theme = data.transferData();

        createThemeWallpapers(theme,this::createHDBackgroundsFromService);
    }

    private URL convertTabs(Tabs tabs){

        URL url= null;

        String uri= Constants.BASE_URL.concat(tabs.getArgument()).concat(Constants.THE_END_URL);

        try {
            url=new URL(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        return url;
    }

          //метод изымает из json файла объект TypesWallpapers (объект с данными для запросов в сеть по подтемам даннной большой темы)
          // на основе выбранной в меню темы
    private void createThemeWallpapers(String theme, SetFunc<TypesWallpapers> type){

        parser.openFile(Constants.FILE_NAME,  result ->  {

                    for(TypesWallpapers typesWallpapers:result){

                        if(typesWallpapers.getTitle().equals(theme)){

                            type.transferResult(typesWallpapers);
                        }}},error->{mistake.showError(error);});

    }

    //метод обращается в сеть с помощью сервиса на основе данных с подтемами,которые получает
    //от объекта TypesWallpapers,т.о. мы получаем коллекцию объектов
    // HDBackgrounds(подтема - список объектов Wallpapers(id и url для картинок))
    //все это отправляется с помощью TransmitterDataFromPresenter transmitter в активити для дальнейшей работы
    private void createHDBackgroundsFromService(TypesWallpapers result){

        ArrayList<Tabs>  tabsList =  result.getTabs();

        List<HDBackgrounds> hdBackgroundsList = new ArrayList<>();

        for(Tabs tab:tabsList){

            service.execute(convertTabs(tab),list ->{

                hdBackgroundsList.add(new HDBackgrounds(tab.getTitle(),list));

                if(hdBackgroundsList.size()==tabsList.size()) {
                    transmitter.showHDBackgrounds(hdBackgroundsList);
                }
            },error->{

                mistake.showError(error);

            });
        }
    }

}
