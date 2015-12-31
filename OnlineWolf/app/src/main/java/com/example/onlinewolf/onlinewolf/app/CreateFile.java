package com.example.onlinewolf.onlinewolf.app;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;

import java.util.concurrent.ExecutionException;

public class CreateFile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String url = "ws://192.168.1.102:8080/";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_file);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        AsyncHttpClient c = new AsyncHttpClient();
        WebSocket w = null;
        try {
            w = c.prepareGet(url)
                           .execute(new WebSocketUpgradeHandler.Builder().build()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        w.addWebSocketListener(new WebSocketTextListener() {
            @Override
            public void onMessage(String message) {
                Log.i("WebSocket","I received "+ message);
            }

            @Override
            public void onOpen(WebSocket websocket) {
                Log.i("WebSocket","Opened WebScoket");
            }

            @Override
            public void onClose(WebSocket websocket) {
                Log.i("WebSocket","Closed WebSocket");
            }

            @Override
            public void onError(Throwable t) {
                Log.e("WebSocket","Error");
            }
        }).sendMessage("Hello world");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
