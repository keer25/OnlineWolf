package com.example.onlinewolf.onlinewolf.app;

import android.os.AsyncTask;
import android.os.Bundle;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ws.DefaultWebSocketListener;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketListener;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;

import java.util.concurrent.ExecutionException;


public class StartGame extends AppCompatActivity {
    public static WebSocket ws = null;
    Util util = Util.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

            }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (ws != null)
            ws.close();
        android.os.Debug.stopMethodTracing();
    }

    public void startSocket(View view){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        AsyncHttpClient c = new AsyncHttpClient();
        WebSocket w = null;
        try {
            w = c.prepareGet(Util.urlw)
                    .execute(new WebSocketUpgradeHandler.Builder().build()).get();
            ws = w.addWebSocketListener(new DefaultWebSocketListener() {
                //@Override
                public void onMessage(String message) {
                    Log.i("WebSocket", "I received " + message);
                    //((EditText) findViewById(R.id.SampleText)).setText(message);
                }

                @Override
                public void onOpen(WebSocket websocket) {
                    Log.i("WebSocket", "Opened WebScoket");
                }

                @Override
                public void onClose(WebSocket websocket) {
                    Log.i("WebSocket", "Closed WebSocket");
                }

                @Override
                public void onError(Throwable t) {
                    Log.e("WebSocket", "Error");
                }
            });

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_start_game);
        }

    }


        }



