package com.myexamplehd.hdbackground_git.ui.paperhanging;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.myexamplehd.hdbackground_git.R;
import com.myexamplehd.hdbackground_git.dependencies.App;
import com.myexamplehd.hdbackground_git.model.service.FileManager;
import com.myexamplehd.hdbackground_git.ui.utilities.BlurTransformation;
import com.myexamplehd.hdbackground_git.ui.utilities.Constants;
import com.myexamplehd.hdbackground_git.ui.utilities.Dialog;
import com.myexamplehd.hdbackground_git.ui.utilities.Info;
import com.myexamplehd.hdbackground_git.ui.utilities.WritePermission;
import java.io.IOException;
import javax.inject.Inject;

public class PaperhangingActivity extends AppCompatActivity  {

    @Inject
    RequestManager paper;

    @Inject
    FileManager fileManager;

    private ImageView imagePaper;
    private ImageView imageBackground;
    private ImageButton btnShare;
    private ImageButton btnSet;
    private ImageButton btnSave;
    private ProgressBar imageBar;
    private String url;
    private Info message;
    private Bitmap bitmap;
    private boolean statusLoadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paperhanging);

        initComponents();
        receivePaper();
        stopProgress();
    }

   private void initComponents(){
       App.getAppComponent().injectPaperhangingActivity(this);
       imageBar=(ProgressBar)findViewById(R.id.imageBar);
       message=new Info(findViewById(R.id.frame),this);
       imagePaper = (ImageView) findViewById(R.id.imagePaper);
       imageBackground = (ImageView) findViewById(R.id.imageBackground);
       btnShare =(ImageButton) findViewById(R.id.btnShare);
       btnSet = (ImageButton) findViewById(R.id.btnSet);
       btnSave = (ImageButton) findViewById(R.id.btnSave);
       imagePaper.setOnClickListener(this::showActions);
       btnShare.setOnClickListener(this::shareImage);
       btnSet.setOnClickListener(this::establishImage);
       btnSave.setOnClickListener(this::saveImage);
    }

    private void showActions(View view) {

        if(statusLoadImage){
            message.showMessageWithAction(getString(R.string.error_load),this::onBackPressed);
        }else{
            btnShare.setVisibility(View.VISIBLE);
            btnSet.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);
        }
    }

    private void receivePaper(){

          url = getIntent().getStringExtra(Constants.PAPER);

    paper.load(url)
            .transform(new BlurTransformation( this))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageBackground);

    paper.load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error_loading)
            .listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    statusLoadImage = target.getRequest().isFailed();
                    return false;
                }
                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            })
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .crossFade(Constants.TIME_ANIME)
            .into(imagePaper);
}

private void stopProgress(){
        imageBar.setVisibility(View.INVISIBLE);
    }

    private void shareImage(View view){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_message)+" "+url);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent,Constants.SHARE_SEARCH));
    }

    private void establishImage(View view) {

        Dialog dialog = new Dialog();
        dialog.showDialog(imagePaper.getDrawable(), this, this::setImageIntoScreenPhone);
    }

    private void setImageIntoScreenPhone() {

         bitmap = convertImageIntoBitmap();

         new Thread(()->{

             try {
                 WallpaperManager myWallpaperManager = WallpaperManager.getInstance(PaperhangingActivity.this);
                 myWallpaperManager.setBitmap(bitmap);
                runOnUiThread(()->{message.showMessage(getString(R.string.establish_successful));});

             } catch (IOException e) {

                 runOnUiThread(()->{message.showMessage(getString(R.string.establish_error));});
             }

         }).start();

        correctionImageLocation();
    }

    private void clearBitmap(Bitmap bitmap){
        if(bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    private  Bitmap convertImageIntoBitmap(){
        imagePaper.setDrawingCacheEnabled(true);
        imagePaper.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        imagePaper.layout(0, 0,
                imagePaper.getMeasuredWidth(),imagePaper.getMeasuredHeight());
        imagePaper.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(imagePaper.getDrawingCache());
        imagePaper.setDrawingCacheEnabled(false);

       return bitmap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constants.WRITE_FILE_PERMISSION_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    message.showMessage(getString(R.string.permission_granted));

                    save();

                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        message.showMessage(getResources().getString(R.string.permission_denied));
                    } else {

                        message.showMessageWithAction(getString(R.string.message_setting), this::openApplicationSettings);
                    }
                    break;}}}

    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+getPackageName()));
        startActivityForResult(appSettingsIntent, Constants.WRITE_FILE_PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode ==  Constants.WRITE_FILE_PERMISSION_REQUEST_CODE) {

            save();

              return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveImage(View view){

        WritePermission writePermission=new WritePermission();

        writePermission.requestPermission(this,this::save);

    }

    private void  correctionImageLocation(){

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
             imagePaper.setLayoutParams(params);
    }

    private void save() {

        bitmap=convertImageIntoBitmap();
        fileManager.save(bitmap);
        correctionImageLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearBitmap(bitmap);
    }
}
