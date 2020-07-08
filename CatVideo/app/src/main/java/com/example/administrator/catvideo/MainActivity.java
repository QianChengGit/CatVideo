package com.example.administrator.catvideo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText selectEditText;
    private Button selectBtn;
    private List<Data> dataList = new ArrayList<>();
    private List<Data> imageList = new ArrayList<>();
    RecyclerView recyclerView;
    final String url = "http://api.meituan.com/mmdb/movie/v2/list/rt/order/coming.json?ci=1&limit=12&token=&__vhost=api.maoyan.com&utm_campaign=AmovieBmovieCD-1&movieBundleVersion=6801&utm_source=xiaomi&utm_medium=android&utm_term=6.8.0&utm_content=868030022327462&net=255&dModel=MI%205&uuid=0894DE03C76F6045D55977B6D4E32B7F3C6AAB02F9CEA042987B380EC5687C43&lat=40.100673&lng=116.378619&__skck=6a375bce8c66a0dc293860dfa83833ef&__skts=1463704714271&__skua=7e01cf8dd30a179800a7a93979b430b2&__skno=1a0b4a9b-44ec-42fc-b110-ead68bcc2824&__skcy=sXcDKbGi20CGXQPPZvhCU3%2FkzdE%3D&utm_source=xiaomi&utm_medium=android&utm_term=6.8.0&utm_content=868030022327462&net=255&dModel=MI%205&uuid=0894DE03C76F6045D55977B6D4E32B7F3C6AAB02F9CEA042987B380EC5687C43&lat=40.100673&lng=116.378619&__skck=6a375bce8c66a0dc293860dfa83833ef&__skts=1463704714271&__skua=7e01cf8dd30a179800a7a93979b430b2&__skno=1a0b4a9b-44ec-42fc-b110-ead68bcc2824&__skcy=sXcDKbGi20CGXQPPZvhCU3%2FkzdE%3D";
    int n = 0;
    int k = 0;
    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (dataList != null && dataList.size() > 0){
                    if (msg.what == n){
                        if(n<12){
                            Data data = dataList.get(n);
                            String strImage = data.getImg();
                            String oldStr = "/w.h/";
                            String newStr = "/166.200/";
                            String newImageStr = strImage.replace(oldStr,newStr);
                            parseImage(newImageStr,n);
                        }
                        n++;
//                    DownloadPictures downloadPictures = new DownloadPictures();
//                    downloadPictures.setHandler(mhandler);
//                    downloadPictures.execute(newImageStr);
//                    if(msg.what == 1){
//                        Data image = new Data((Bitmap) msg.obj);
//                        imageList.add(image);
//                    }
                }
                if (imageList != null && imageList.size() > 0 && n==13){
                    MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(dataList,imageList);
                    recyclerView.setAdapter(myRecyclerViewAdapter);
                }
            }else {
                Toast.makeText(MainActivity.this,"信息获取失败，服务器繁忙，请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectEditText = (EditText) findViewById(R.id.textView);
        selectBtn = (Button) findViewById(R.id.select_video_btn);
        selectEditText.setText("猫眼预告搜索");
        selectEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k++;
                if (k == 1){
                    selectEditText.setText("");
                }
            }
        });
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = selectEditText.getText().toString();
                if (!str.isEmpty() && !str.equals("没有搜到!") && !str.equals("请重新输入...")){
                    for (int i = 0; i < dataList.size(); i++) {
                        Data data = dataList.get(i);
                        if (data.getNm().toString().equals(str)){
                            selectEditText.setText("");
                            Intent intent = new Intent(MainActivity.this,ItemActivity.class);
                            intent.putExtra("URL",data.getVideourl());
                            intent.putExtra("name",data.getNm());
                            startActivity(intent);
                            break;
                        }else {
                            selectEditText.setText("没有搜到!");
                        }
                    }
                }else {
                    selectEditText.setText("请重新输入...");
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_Toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.video_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        sendRequestWhthOkHttp(url);
    }


    private void sendRequestWhthOkHttp(final String Url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);

                    Message message = mhandler.obtainMessage();
                    message.what = 0;
                    mhandler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String responseData) {
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject jsonObjectItem = jsonObject.getJSONObject("data");
            JSONArray  jsonArray = jsonObjectItem.getJSONArray("coming");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject JSONItem = jsonArray.getJSONObject(i);
                String name;
                String cat;
                String star;
                String dir;
                String pubDesc;
                String videoName;
                String videourl;
                String img;
                if (!JSONItem.isNull("nm")){
                    name = JSONItem.getString("nm");
                }else {
                    name = "无";
                }
                if (!JSONItem.isNull("cat")){
                    cat = JSONItem.getString("cat");
                }else {
                    cat= "无";
                }
                if (!JSONItem.isNull("star")){
                   star = JSONItem.getString("star");
                }else {
                    star = "无";
                }
                if (!JSONItem.isNull("dir")){
                    dir = JSONItem.getString("dir");
                }else {
                    dir = "无";
                }
                if (!JSONItem.isNull("pubDesc")){
                    pubDesc = JSONItem.getString("pubDesc");
                }else {
                    pubDesc = "无";
                }
                if (!JSONItem.isNull("videoName")){
                    videoName = JSONItem.getString("videoName");
                }else {
                    videoName = "无";
                }
                if (!JSONItem.isNull("videourl")){
                    videourl = JSONItem.getString("videourl");
                }else {
                    videourl = "无";
                }
                if (!JSONItem.isNull("img")){
                    img = JSONItem.getString("img");
                }else {
                    img = "无";
                }
                Data data = new Data(cat,star,dir,img,name,pubDesc,videoName,videourl);
                dataList.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseImage(final String imageUrl,final int m) {
        new Thread(new Runnable() {
            @Override
          public void run() {
                Bitmap bitmap = null;
                URLConnection connection;
                InputStream is;
                try {
                    connection = new URL(imageUrl).openConnection();
                    is = connection.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bitmap = BitmapFactory.decodeStream(bis);
                    is.close();
                    bis.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Data data = new Data(bitmap);
                imageList.add(data);
                Message message = mhandler.obtainMessage();
                message.what = m+1;
                mhandler.sendMessage(message);
            }
        }).start();
    }

}
