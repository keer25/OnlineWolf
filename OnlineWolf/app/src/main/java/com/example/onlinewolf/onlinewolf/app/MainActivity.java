package com.example.onlinewolf.onlinewolf.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public final static String EXTRA_MESSAGE = "com.example.onlinewolf.MESSAGE";
      public void startPlay(View view) {
          Intent login = new Intent(this, ShowLogin.class);
          Button button = (Button) findViewById(R.id.menu_play);
          String message = button.getText().toString();
          login.putExtra(EXTRA_MESSAGE, message);
          startActivity(login);
      }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        else{
            Log.e("Network Error","Unable to connect to network" );
            // TODO Positioning Toasts
            Toast toast = Toast.makeText(getApplicationContext(),"Check Network Connection",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
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
