package com.myexamplehd.hdbackground_git.ui.wallpapers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.myexamplehd.hdbackground_git.R;
import com.myexamplehd.hdbackground_git.model.entities.net.Wallpapers;
import java.util.List;

/**
 * Created by Максим on 19.12.2017.
 */

public class WallpapersAdapter extends RecyclerView.Adapter<WallpapersAdapter.ViewHolder> {

    private List<Wallpapers> results;

    private final RequestManager requestManager;

    private final WallpapersClickListener listener;


    public WallpapersAdapter(List<Wallpapers> results, RequestManager requestManager, WallpapersClickListener listener) {
        this.results = results;
        this.requestManager = requestManager;
        this.listener = listener;
    }

    @Override
    public WallpapersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.image_item,parent,false);

        return  new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(WallpapersAdapter.ViewHolder holder, int position) {

        Wallpapers wallpaper=results.get(position);
        holder.bindTo(wallpaper);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageView;

        private  Wallpapers wallpaper;

        public ViewHolder(View itemView, final WallpapersClickListener listener) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this::launchWallpaper);
        }

        private void launchWallpaper(View view){

            listener.onWallpaperClick(wallpaper);

        }

        public  void bindTo(Wallpapers wallpaper){

            this.wallpaper=wallpaper;

            requestManager.load(wallpaper.getUrl().replace("/post/","/image/")).placeholder(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .into(imageView);
        }
    }

    public interface WallpapersClickListener {

        void onWallpaperClick(Wallpapers wallpaper);
    }

}
