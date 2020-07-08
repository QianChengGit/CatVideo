package com.example.administrator.catvideo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2019/4/30.
 */

public class DownloadPictures extends AsyncTask<String,Integer,Bitmap> {
    private Handler mhandler;

    public void setHandler(Handler mhandler){
        this.mhandler = mhandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap bitmap = null;
        URLConnection connection;
        InputStream is;
        try{
            connection = new URL(url).openConnection();
            is = connection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            bitmap = BitmapFactory.decodeStream(bis);
            is.close();
            bis.close();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Message message = mhandler.obtainMessage();
        message.obj = bitmap;
        message.what = 1;
        mhandler.sendMessage(message);
    }
}
