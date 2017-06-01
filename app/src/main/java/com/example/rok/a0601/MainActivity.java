package com.example.rok.a0601;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
EditText e1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.etmsg);
    }
    Handler handler = new Handler();
    Thread mythread = new Thread(){
        String SERVER_IP = "172.17.65.177";
        int SERVER_PORT = 200;
        String msg = "";
        @Override
        public void run() {

            try {
                Socket aSocket = new Socket(SERVER_IP, SERVER_PORT);
                System.out.println("Client] 서버 접속");



                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
                outstream.writeObject(msg);
                outstream.flush();
//                System.out.println("Client] 전송데이터 : " + msg);

                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
               final Object obj = instream.readObject();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),(String)obj, Toast.LENGTH_SHORT).show();
                    }
                });
//                System.out.println("Client] 받은데이터 : " + obj);

                aSocket.close();
//                System.out.println("Client] 서버 접속 중단");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        };
}


