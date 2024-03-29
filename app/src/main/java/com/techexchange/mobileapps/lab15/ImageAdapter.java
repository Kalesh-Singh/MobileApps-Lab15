package com.techexchange.mobileapps.lab15;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>
        implements ImageDownloadListener {

    private final LayoutInflater layoutInflater;
    private final List<ImageViewData> imageViewDataList;
    private final Context context;

    public ImageAdapter(Context context, List<ImageViewData> imageViewDataList) {
        this.imageViewDataList = imageViewDataList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View itemView = layoutInflater.inflate(R.layout.image_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageViewData imageViewData = imageViewDataList.get(position);
        holder.imageView.setImageBitmap(imageViewData.bitmap);

        // Download the image Bitmap
        DownloadImage downloadImageTask
                = new DownloadImage(this, this.imageViewDataList);
        downloadImageTask.execute(position);
    }

    @Override
    public int getItemCount() {
        return imageViewDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
        }
    }

    @Override
    public void onComplete(BitmapAndPosition bitmapAndPosition) {
        this.imageViewDataList.get(bitmapAndPosition.position).bitmap
                = bitmapAndPosition.bitmap;
        this.notifyDataSetChanged();
    }
}
