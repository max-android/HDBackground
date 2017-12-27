package com.myexamplehd.hdbackground_git.ui.utilities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.myexamplehd.hdbackground_git.R;
import com.myexamplehd.hdbackground_git.ui.callbacks.FuncVoid;


/**
 * Created by Максим on 01.12.2017.
 */

public class WritePermission {

    public  void requestPermission(Context context, FuncVoid funcVoid){

        //проверка - есть ли разрешение для записи,если нет,то объяснить почему необходимо
        // разрешение на повторный запрос
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

             //случай отсутствия разрешения
            //объяснение и запрос на предоставление
            if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity)context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //случай,если первый раз отказали,то дается объяснение при повторном запросе

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage(R.string.permission_explanation);

                builder.setPositiveButton(android.R.string.ok,(dialog,which)->{request(context);});

                builder.setCancelable(false);
                builder.create().show();

            }
            else {
                //запрос на предоставление
                request(context);
            }
        }

        else {
            //случай наличия разрешения
            funcVoid.action();
        }

    }

              //Запрос разрешения
    private void request(Context context){
        ActivityCompat.requestPermissions((AppCompatActivity)context,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constants.WRITE_FILE_PERMISSION_REQUEST_CODE);
    }

}
