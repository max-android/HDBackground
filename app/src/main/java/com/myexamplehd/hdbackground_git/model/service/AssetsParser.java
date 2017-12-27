package com.myexamplehd.hdbackground_git.model.service;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import com.myexamplehd.hdbackground_git.R;
import com.myexamplehd.hdbackground_git.model.entities.assets.Tabs;
import com.myexamplehd.hdbackground_git.model.entities.assets.TypesWallpapers;
import com.myexamplehd.hdbackground_git.ui.callbacks.SetFunc;
import com.myexamplehd.hdbackground_git.ui.utilities.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Максим on 17.12.2017.
 */

public class AssetsParser {

    private Context context;
    private Handler handler;
    private String fault = Constants.DEFAULT;

    public AssetsParser(Context context) {
        this.context = context;
        handler=new Handler(Looper.getMainLooper());
    }

    public void openFile(String file_name, SetFunc<List<TypesWallpapers>> transfer, SetFunc<String> error){

        new Thread(()->{

        AssetManager assetManager=context.getAssets();

        try(BufferedReader reader=new BufferedReader(new InputStreamReader(assetManager.open(file_name)))) {

            String data;

            StringBuilder stringBuilder=new StringBuilder();

            while((data=reader.readLine())!=null){
                stringBuilder.append(data);
            }

            List<TypesWallpapers> types = parseJSON(stringBuilder.toString());

            handler.post(()->{
                transfer.transferResult(types);
                error.transferResult(fault);
            });

            fault = Constants.DEFAULT;
        }catch (IOException e){
            fault=context.getString(R.string.data_error);
            e.printStackTrace();
        }}

        ).start();
    }

    private List<TypesWallpapers> parseJSON(String json) {

        List<TypesWallpapers> types = new ArrayList<>();

                if(json!=null){

                    try {
                        JSONObject object = new JSONObject(json);

                        JSONArray mass = object.getJSONArray("list");

                        for (int i = 0; i < mass.length() ; i++) {

                            JSONObject obj = mass.getJSONObject(i);

                            String title_obj = obj.getString("title");

                            JSONArray tabs  = obj.getJSONArray("tabs");

                            ArrayList<Tabs> tabsArrayList = new ArrayList<>();

                            for (int j = 0; j < tabs.length(); j++) {
                                JSONObject tab = tabs.getJSONObject(j);
                                String title_tab = tab.getString("title");
                                String argument = tab.getString("argument");
                                tabsArrayList.add(new Tabs(title_tab,argument));
                            }

                            types.add(new TypesWallpapers(title_obj,tabsArrayList));

                        }
                        fault = Constants.DEFAULT;
                    } catch (JSONException e) {
                        fault=context.getString(R.string.data_error);
                        e.printStackTrace();
                    }
                }

              return types;
    }

}
