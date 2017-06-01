package com.example.rok.a0601;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main2Activity extends AppCompatActivity {
    EditText e1,e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);
    }
    Handler handler = new Handler();
    Thread thread = new Thread(){
        @Override
        public void run() {
            try{
                URL url = new URL(e1.getText().toString());
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    final String data = readData(urlConnection.getInputStream());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            e2.setText(data);
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public String readData(InputStream is) {
            String data = "";
            Scanner s = new Scanner(is);
            while(s.hasNext()){
                data += s.nextLine();

            }
            s.close();
            return data;
        }
    };

    public void onmyclick(View view) {
        switch (view.getId()){
            case R.id.button:
                thread.start();
                break;
            case R.id.button2:
                Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(intent);
                break;
        }
    }
}
