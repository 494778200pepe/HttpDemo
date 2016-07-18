package com.zitech.httpdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by pepe on 2016/7/13.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class HttpThread extends Thread {
    private String url;
    private WebView webView;
    private ImageView iamge;
    private Handler handler;

    public HttpThread(String url, WebView webView, Handler handler) {
        this.url = url;
        this.webView = webView;
        this.handler = handler;
    }
    public HttpThread(String url, ImageView iamge, Handler handler) {
        this.url = url;
        this.iamge = iamge;
        this.handler = handler;
    }

    @Override
    public void run() {
        //GET(需要转码)
//        url=url+"?name="+ URLEncoder.encode(name,"utf-8")+"&age="+age;
        try {
            //打印系统默认的环境相关信息
            Properties property=System.getProperties();
            property.list(System.out);
            //file.encoding="UTF-8"

            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            //POST(不需要转码)
//            OutputStream out=conn.getOutputStream();
//            String content="name="+name+"@age="+age;
//            out.write(content.getBytes());

//            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuffer sb=new StringBuffer();
//            String str;
//            while ((str=reader.readLine())!=null){
//                sb.append(str);
//            }
//            System.out.println(sb.toString());

            conn.setDoInput(true);
            InputStream in=conn.getInputStream();
            FileOutputStream out = null;
            File downFile = null;
            String fileNanme=String.valueOf(System.currentTimeMillis());
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File parent=Environment.getExternalStorageDirectory();
                 downFile=new File(parent,fileNanme);
                out =new FileOutputStream(downFile);
            }
            byte[] b = new byte[2*1024];
            int len;
            if(out!=null){
                while ((len=in.read())!=-1){
                    out.write(b,0,len);
                }
            }
            final Bitmap bitmap= BitmapFactory.decodeFile(downFile.getAbsolutePath());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iamge.setImageBitmap(bitmap);
                }
            });



            /*final StringBuffer sb=new StringBuffer();
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            while((str=reader.readLine())!=null){
                sb.append(str);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadData(sb.toString(),"text/html;charset=utf-8","");
                }
            });*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
