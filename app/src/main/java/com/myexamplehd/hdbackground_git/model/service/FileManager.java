package com.myexamplehd.hdbackground_git.model.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.widget.Toast;
import com.myexamplehd.hdbackground_git.R;
import com.myexamplehd.hdbackground_git.ui.utilities.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Максим on 23.12.2017.
 */

public class FileManager {

    private Context context;
    private Handler handler;

    public FileManager(Context context) {
        this.context = context;
        handler=new Handler(Looper.getMainLooper());
    }

    public void save(Bitmap bitmap){

    new Thread(()->{

        String absolutePath =  Environment.getExternalStorageDirectory().getAbsolutePath();

        File dir = new File(absolutePath+ Constants.NAME_DIRECTORY);
        dir.mkdir();

        String rootPath =  dir.getPath();

        File file = new File(rootPath, context.getString(R.string.head_name_file)+System.currentTimeMillis()+Constants.FORMAT);

        try(OutputStream outputStream = new FileOutputStream(file)){

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            String location =  MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,file.getName(),file.getName());
            if (location!=null){
                handler.post(()->{showMessage(context.getString(R.string.save_successful)+" "+file.getAbsolutePath()+" "+context.getString(R.string.save_in_pictures));
                     });
            }else{
                handler.post(()->{ showMessage(context.getString(R.string.save_error)); });
            }

        } catch (IOException e) {
            handler.post(()->{ showMessage(context.getString(R.string.save_error)); });
        }

    }).start();

}

private void showMessage(String message){
    Toast.makeText(context,message,Toast.LENGTH_LONG).show();

}


}
