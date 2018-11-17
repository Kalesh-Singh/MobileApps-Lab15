package com.techexchange.mobileapps.lab15;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements TaskCompleteListener {

    static final String TAG = "httptag";
    private List<ImageViewData> imageViewDataList;
    private RecyclerView imageRecyclerView;
    private ImageAdapter  imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the image URLs when the app is started
        RecentFlickrPhotos recentFlickrPhotos = new RecentFlickrPhotos(this);
        recentFlickrPhotos.getPhotosURLs();

        imageRecyclerView = findViewById(R.id.image_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        imageRecyclerView.setLayoutManager(gridLayoutManager);

        // TODO: Set Recycler View Adapter
    }

    @Override
    public void onComplete(List<URL> photosURLs) {
        List<ImageViewData> imageViewDataList = new ArrayList<>();
        for (URL url: photosURLs) {
            imageViewDataList.add(new ImageViewData(url));
        }
        this.imageViewDataList = imageViewDataList;
        this.imageAdapter = new ImageAdapter(this, imageViewDataList);
        imageRecyclerView.setAdapter(imageAdapter);
    }
}
