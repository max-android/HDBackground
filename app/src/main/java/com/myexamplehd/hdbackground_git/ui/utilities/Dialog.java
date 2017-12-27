package com.myexamplehd.hdbackground_git.ui.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.myexamplehd.hdbackground_git.R;
import com.myexamplehd.hdbackground_git.ui.callbacks.FuncVoid;


/**
 * Created by Максим on 22.12.2017.
 */

public class Dialog {

    public void showDialog(Drawable drawable, Context context, FuncVoid funcVoid){

        AlertDialog.Builder establishBuilder = new AlertDialog.Builder(context);

        establishBuilder.setIcon(drawable)
                .setTitle(context.getString(R.string.my_drawable))
                .setMessage(context.getString(R.string.establish_message))
                .setCancelable(false);

        establishBuilder.setNegativeButton(context.getString(R.string.cancel),(dialog,which)->{dialog.cancel();});

        establishBuilder.setPositiveButton(context.getString(R.string.ok),(dialog,which)->{funcVoid.action();});

        AlertDialog alert = establishBuilder.create();

        alert.show();
    }

}
