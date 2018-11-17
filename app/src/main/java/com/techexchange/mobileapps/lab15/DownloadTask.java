package com.techexchange.mobileapps.lab15;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {

    private final DownloadCompleteListener downloadCompleteListener;

    public DownloadTask(DownloadCompleteListener downloadCompleteListener) {
        this.downloadCompleteListener = downloadCompleteListener;
    }


    @Override
    protected String doInBackground(String... urlStrings) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlStrings[0]);

            connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            for (int bytesRead; (bytesRead = inputStream.read(buffer)) > 0;) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] responseBytes = outputStream.toByteArray();
            String responseString = new String(responseBytes);
            return responseString;

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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // Pass result to RecentFlickrPhotos
        downloadCompleteListener.onComplete(s);
    }
}
