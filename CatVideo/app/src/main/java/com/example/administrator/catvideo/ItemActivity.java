package com.example.administrator.catvideo;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

/**
 * Created by Administrator on 2019/4/28.
 */

public class ItemActivity extends AppCompatActivity {
    private TextView textView;
    private VideoView videoView;
    private String url;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        //隐藏状态栏和actionBar
//        View decorView = getWindow().getDecorView();
//        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(option);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.main_Toolbar);
//        setSupportActionBar(toolbar);
//        textView = (TextView) findViewById(R.id.activity_item);
        videoView = (VideoView) findViewById(R.id.video_View);

        initVideoPath();
        setVideoView();
    }
//设置沉浸式状态栏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void initVideoPath(){
        Intent intent = getIntent();
        url = intent.getStringExtra("URL");
        name = intent.getStringExtra("name");
    }

    private void setVideoView(){
        videoView.setVideoPath(url);
        videoView.requestFocus();
        videoView.setMediaController(new MediaController(this));//设置播放进度操作
        videoView.start();
//        videoView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (videoView.isPlaying()){
//                    videoView.pause();
//                }else if(!videoView.isPlaying()){
//                    videoView.start();
//                }
//                return false;
//            }
//        });
    }
}
