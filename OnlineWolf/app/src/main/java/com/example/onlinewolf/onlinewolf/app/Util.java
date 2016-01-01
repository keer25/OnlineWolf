package com.example.onlinewolf.onlinewolf.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;

/**
 * Created by keerthana on 1/1/16.
 */
public class Util extends AppCompatActivity{
    private static Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }

    String url = "http://192.168.1.102:3000";

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        else{
            Log.e("Network Error", "Unable to connect to network");
            // TODO Positioning Toasts
            showToast("Check Network Connection");
            return false;
        }
    }
    public void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT);
        toast.show();
    }
}
