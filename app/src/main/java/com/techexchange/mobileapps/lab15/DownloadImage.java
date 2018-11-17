package com.techexchange.mobileapps.lab15;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class DownloadImage extends AsyncTask<Integer, Void, BitmapAndPosition> {

    private final ImageDownloadListener imageDownloadListener;
    private final List<ImageViewData> imageViewDataList;

    public DownloadImage(ImageDownloadListener imageDownloadListener,
                         List<ImageViewData> imageViewDataList) {
        this.imageDownloadListener = imageDownloadListener;
        this.imageViewDataList = imageViewDataList;
    }

    @Override
    protected BitmapAndPosition doInBackground(Integer... positions) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)
                    this.imageViewDataList.get(positions[0]).url.openConnection();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return new BitmapAndPosition(positions[0], bitmap);
        } catch (IOException e) {
            Log.d(MainActivity.TAG, e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(BitmapAndPosition bitmapAndPosition) {
        super.onPostExecute(bitmapAndPosition);

        imageDownloadListener.onComplete(bitmapAndPosition);
    }
}
