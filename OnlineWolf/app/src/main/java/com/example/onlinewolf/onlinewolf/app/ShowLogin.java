//User data in file name "user.txt"
package com.example.onlinewolf.onlinewolf.app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ShowLogin extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.onlinewolf.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Replace with code for checking for cookies
        FileInputStream inp = null;
        try{
            inp = openFileInput("user.usr");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (inp == null) {
                setContentView(R.layout.login_signup);
            } else {
                setContentView(R.layout.activity_create_file);
            }
        }
        }

    public void login(View view){
        Log.i("VIEW", "setting login view..");
        setContentView(R.layout.login);

    }
    public void authUser(View view){
        EditText edit = (EditText) findViewById(R.id.emailLogin);
        String email =  edit.getText().toString();
        edit = (EditText) findViewById(R.id.passwordLogin);
        String password = edit.getText().toString();
        //TODO Bring some validations to client Side
        //Send http request with proper params
    }

    public void createUser(View view) {
        Log.i("VIEW", "Signing up...");
        Map map = new HashMap();
        EditText edit = (EditText) findViewById(R.id.nameSignup);
        map.put("name", edit.getText().toString());
        edit = (EditText) findViewById(R.id.emailSignup);
        map.put("email", edit.getText().toString());
        edit = (EditText) findViewById(R.id.passwordSignup);
        map.put("password", edit.getText().toString());
        edit = (EditText) findViewById(R.id.confirmSignup);
        map.put("conf_password", edit.getText().toString());
        Map main = new HashMap();
        main.put("user",map);
        //Perform validation
        Log.i("GEN", "Map created");
        String json = new JSONObject(map).toString();
        Log.i("GEN", "JSON created "+json);
        Util util = Util.getInstance();
        new PostUser().execute(json);
        if (util.isConnected()){
            Log.i("GEN","Connected");
        }
    }
        class PostUser extends AsyncTask<String,Void,Void>{

            @Override
            protected Void doInBackground(String... json) {
                HttpURLConnection urlConnection = null;
                try {
                    Util util = Util.getInstance();
                    URL url = new URL(util.url + "/users");
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try{

                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    //urlConnection.connect();
                    urlConnection.setDoOutput(true);
                    //urlConnection.connect();
                    urlConnection.setChunkedStreamingMode(0);
                    urlConnection.connect();
                    Log.i("GEN",urlConnection.getContentEncoding());
                    //Log.i("GEN",urlConnection.toString());
                    ObjectOutputStream outputStream = new ObjectOutputStream(urlConnection.getOutputStream());
                    outputStream.writeObject(json[0]);
                    //Reading inputstream
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection == null) Log.e("POST","Error in posting content");
                    urlConnection.disconnect();
                }
                return null;
            }
        //Send http request with proper params

    }
    public void signup(View view){
        Log.i("VIEW","setting signup view..");
        setContentView(R.layout.signup);
    }
    public void createUserFile(View view){

        EditText user_name = (EditText) findViewById(R.id.editText);
        String name = user_name.getText().toString();
        EditText pass = (EditText) findViewById(R.id.editText2);
        String password =  pass.getText().toString();
        //User user = new User(name,password);
        //try {
        //    FileOutputStream fos =openFileOutput("user.usr", Context.MODE_PRIVATE);
        //    ObjectOutputStream oos = new ObjectOutputStream(fos);
        //    oos.writeObject(user);
        //}catch (Exception ex){
        //    ex.printStackTrace();
        //}
        Intent intent = new Intent(this, CreateFile.class);
        Button button = (Button) findViewById(R.id.confirmbutton);
        String message = button.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


    protected void onDestroy()
    {
        super.onDestroy();
        android.os.Debug.stopMethodTracing();
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
