//User data in file name "user.txt"
package com.example.onlinewolf.onlinewolf.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.transform.Result;

public class ShowLogin extends AppCompatActivity {
    Util util = Util.getInstance();
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
// <ethod called on pressing login
    public void login(View view){
        Log.i("VIEW", "setting login view..");
        setContentView(R.layout.login);

    }
//Method on submit from login page
    public void authUser(View view){
        Log.i("VIEW","Logging in...");
        EditText edit = (EditText) findViewById(R.id.emailLogin);
        String email =  edit.getText().toString();
        edit = (EditText) findViewById(R.id.passwordLogin);
        String password = edit.getText().toString();
        //TODO Bring some validations to client Side
        //Send http request with proper params
    }
//Method on submit form signup page
    public void createUser(View view) throws JSONException {
        Log.i("VIEW", "Signing up...");
        JSONObject json  = new JSONObject();
        EditText edit = (EditText) findViewById(R.id.nameSignup);
        json.accumulate("name", edit.getText().toString());
        edit = (EditText) findViewById(R.id.emailSignup);
        json.accumulate("email", edit.getText().toString());
        edit = (EditText) findViewById(R.id.passwordSignup);
        json.accumulate("password", edit.getText().toString());
        edit = (EditText) findViewById(R.id.confirmSignup);
        json.accumulate("password_confirmation", edit.getText().toString());
        JSONObject main = new JSONObject();
        main.accumulate("user", json);
        //Perform validation
        String jsonstr = main.toString();
        Log.i("JSON",jsonstr);
        new PostUser().execute(jsonstr);
    }
        class PostUser extends AsyncTask<String, Void, Void> {

            //Util util = Util.getInstance();

            @Override
            protected Void doInBackground(String... json) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                Log.i("Network info ",networkInfo.getState().toString());
                if (networkInfo != null && networkInfo.isConnected()) {
                    HttpURLConnection urlConnection = null;
                    try {
                        //Making connections
                        URL url = new URL(util.url + "/users");
                        urlConnection = (HttpURLConnection) url.openConnection();
                        Log.i("URl",url.toString());
                        Log.i("Connection",urlConnection.toString());
                        // Configuring headers
                        urlConnection.setRequestProperty("Content-Type", "application/json");
                        urlConnection.setDoOutput(true);
                        urlConnection.setRequestMethod("POST");
                        Log.i("Request method",urlConnection.getRequestMethod());
                        //Writing to output stream
                        OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                        wr.write(json[0]);
                        wr.flush();
                        Log.i("Response",urlConnection.getResponseMessage());
                        urlConnection.disconnect();
                        //TODO Error handling in response
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();

                return null;
            }
            @Override
            protected void onPostExecute(Void v){
                Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_SHORT).show();
                          }

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
        intent.putExtra(Util.EXTRA_MESSAGE, message);
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
