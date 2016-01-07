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

    private Util    () {
    }
    public final static String EXTRA_MESSAGE = "com.example.onlinewolf.MESSAGE";
    //public static String url = "http://192.168.1.100:3000";
    public static String url = "http://agile-cove-4656.herokuapp.com";
    //public static String urlw = "ws://192.168.1.100:3000";
    public static String urlw = "ws://agile-cove-4656.herokuapp.com/";

}
