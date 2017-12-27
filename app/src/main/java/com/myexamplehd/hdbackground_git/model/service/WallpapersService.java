package com.myexamplehd.hdbackground_git.model.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.myexamplehd.hdbackground_git.R;
import com.myexamplehd.hdbackground_git.model.entities.net.Wallpapers;
import com.myexamplehd.hdbackground_git.ui.callbacks.SetFunc;
import com.myexamplehd.hdbackground_git.ui.utilities.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Максим on 18.12.2017.
 */

public class WallpapersService {

    private Context context;
    private Handler handler;

    private String fault;

    public WallpapersService (Context context) {
        this.context = context;
        handler=new Handler(Looper.getMainLooper());
    }

    public void execute(URL url, SetFunc<List<Wallpapers>> transfer, SetFunc<String> error) {

        new Thread(()->{

            String data = getDataFromService(url);

            List<Wallpapers> list = convertJsonStringToList(data);

            handler.post(()->{
                transfer.transferResult(list);
                error.transferResult(fault);
            });

        }).start();
    }


    private List<Wallpapers> convertJsonStringToList(String json){

        List<Wallpapers> wallpapersList=new ArrayList<>();


        if(json!=null){

            try {
                JSONObject object = new JSONObject(json);

                JSONArray mass = object.getJSONArray("posts");

                for (int i = 0; i < mass.length() ; i++) {

                    JSONObject obj = mass.getJSONObject(i);

                    String id = obj.getString("id");
                    String url = obj.getString("photo-url-1280");

                    wallpapersList.add(new Wallpapers(id,url));

                }
                fault = Constants.DEFAULT;
            } catch (JSONException e) {
                fault = context.getString(R.string.data_error);
                e.printStackTrace();
            }

        }

        return wallpapersList;
    }

    private String getDataFromService(URL urls) {

        HttpURLConnection connection = null;
        StringBuilder stringBuilder = null;
        try {
            connection = (HttpURLConnection) urls.openConnection();

            int responce = connection.getResponseCode();

            if(responce==HttpURLConnection.HTTP_OK) {

                stringBuilder=new StringBuilder();

                try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

                    String line;

                    while((line=reader.readLine())!=null) {

                        stringBuilder.append(line);
                    }
                    fault = Constants.DEFAULT;
                }catch(IOException e){
                    fault = context.getString(R.string.data_error);
                    e.printStackTrace();
                }

                fault = Constants.DEFAULT;
                return stringBuilder.toString().replace("var tumblr_api_read ="," ");

            }else{
                fault = context.getString(R.string.data_error);
            }
        } catch (Exception e) {
            fault = context.getString(R.string.data_error);
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;

    }

}
