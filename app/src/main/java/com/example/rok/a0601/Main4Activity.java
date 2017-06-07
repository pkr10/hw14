package com.example.rok.a0601;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main4Activity extends AppCompatActivity {
    EditText e1,e2;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        e1 = (EditText)findViewById(R.id.editText3);
        e2 = (EditText)findViewById(R.id.editText4);
        msg = (TextView)findViewById(R.id.textView);
    }

    Handler handler = new Handler();
    Thread thread = new Thread(){
        @Override
        public void run() {
            try{
                URL url =new URL("http://jerry1004.dothome.co.kr/info/login.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                String userid = e1.getText().toString();
                String password =e2.getText().toString();
                String postData = "userid="+ URLEncoder.encode(userid) +"&password=" +URLEncoder.encode(password);
                OutputStream outputStream =httpURLConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                InputStream inputStream;
                if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                    
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                    
                }
                final String result = loginResult(inputStream);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(result.equals("FAIL\n")){
                            msg.setText("로그인이 실패했습니다");
                        }
                        else{
                            msg.setText(result+"님로그인 성공");
                        }
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String loginResult(InputStream is) {
            String data = "";
            Scanner s = new Scanner(is);
            while(s.hasNext())data +=s.nextLine() +"\n";
            s.close();
            return data;
        }
    };


    public void onmyclick(View view) {
        switch(view.getId()) {


            case R.id.button5:
                thread.start();
                break;
        }
    }
}
