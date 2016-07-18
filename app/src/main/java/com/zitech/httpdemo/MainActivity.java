package com.zitech.httpdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView=(WebView)findViewById(R.id.webView);
//        new HttpThread("http://www.baidu.com",webView,handler).start();
        ImageView image=(ImageView)findViewById(R.id.image);
        new HttpThread("http://img1.gtimg.com/16/1678/167832/16783276_980x1200_292.jpg",image,handler).start();

    }
}
