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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class ShowLogin extends AppCompatActivity {
    Util util = Util.getInstance();
    String email;
    String password;
    int active_flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Replace with code for checking for cookies
        active_flag = 0;
        FileInputStream inp = null;
        try{
            inp = openFileInput("user.usr");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (inp == null) {
                setContentView(R.layout.login_signup);
            } else {
                String json = null;
                try {
                    ObjectInputStream ois = new ObjectInputStream(inp);
                    json = ois.readUTF();
                    new LoginExisting().execute(json);
                    //if (active_flag == 1) startGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

    //Writes string json to the netowrk output Stream and returns the response or -1 in case of exceptions
    //Throws IO exception
    protected int getResponse(String json) throws IOException {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) return -1;
        if (networkInfo.isConnected()) {
            HttpURLConnection conn = null;
            //Making connections
            URL url = new URL(util.url + "/login");
            conn = (HttpURLConnection) url.openConnection();
            Log.i("URl", url.toString());
            // Configuring headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            Log.i("Request method", conn.getRequestMethod());
            //Writing to output stream
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json);
            wr.flush();
            int code = conn.getResponseCode();
            //Log.i("Response",urlConnection.getResponseCode());
            conn.disconnect();
            return code;
        }
            return -1;
        }

    // Method to take user to the game
    protected void startGame(){
        Log.i("GEN","Starting game");
        Intent intent = new Intent(this, StartGame.class);
        intent.putExtra(Util.EXTRA_MESSAGE, "Start Game Bitch");
        startActivity(intent);
    }

    public void signup(View view){
        Log.i("VIEW", "setting signup view..");
        // TODO Adapters for showing exepected data
        setContentView(R.layout.signup);
    }

    // Method called on pressing login
    public void login(View view){
        Log.i("VIEW", "setting login view..");
        setContentView(R.layout.login);

    }


    private class LoginExisting extends Login{
        @Override
        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            if (result == "Logged in") startGame();
            else setContentView(R.layout.login_signup);
        }
    }





//Method on submit from login page
    public void authUser(View view) throws JSONException{
        Log.i("VIEW", "Logged in");
        //TODO Bring some validations to client Side
        //Uncomment this while deploying

        JSONObject json = new JSONObject();
        json.accumulate("email",email = ((EditText) findViewById(R.id.emailLogin)).getText().toString());
        json.accumulate("password",password = ((EditText) findViewById(R.id.passwordLogin)).getText().toString());
        JSONObject main = new JSONObject();
        main.accumulate("session",json);

        //JSONObject main = loginTest();
        //Client Side Validations
        new Login().execute(main.toString());
        //if (active_flag==1) startGame();
    }

    public class Login extends AsyncTask<String,Void,String> {
        protected String doInBackground(String... json) {
                try{
                int code = getResponse(json[0]);
                //TODO Elaborate Error handling in response at the client sid
                if(code == 201)
                    return "Logged in";
                else if (code == 250)
                    return "Invalid Details";
                else if (code == -1)
                    return "Check Network Connection";
                else return "Error in Sending Data. Try again later.";
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Something Bad happened. Try again later.";
                }
        }


        @Override
        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            if (result == "Logged in"){
                try {
                    writeFile();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

                //active_flag = 1;
                startGame();
            }
            else setContentView(R.layout.login_signup);
        }

    }



//Method on submit form signup page
    public void createUser(View view) throws JSONException {

        Log.i("VIEW", "Signing up...");
        //Uncomment this on deploying

        JSONObject json  = new JSONObject();
        EditText edit = (EditText) findViewById(R.id.nameSignup);
        json.accumulate("name", edit.getText().toString());
        edit = (EditText) findViewById(R.id.emailSignup);
        json.accumulate("email", email = edit.getText().toString());
        edit = (EditText) findViewById(R.id.passwordSignup);
        json.accumulate("password", password = edit.getText().toString());
        edit = (EditText) findViewById(R.id.confirmSignup);
        json.accumulate("password_confirmation", edit.getText().toString());
        JSONObject main = new JSONObject();
        main.accumulate("user", json);

        //JSONObject main = signupTest();
        //Perform validation
        String jsonstr = main.toString();
        Log.i("JSON",jsonstr);
        new PostUser().execute(jsonstr);
        //if (active_flag == 1) startGame();
    }
        class PostUser extends AsyncTask<String, Void, String> {

            //Util util = Util.getInstance();

            @Override
            protected String doInBackground(String... json) {
                    try {
                        int code = getResponse(json[0]);
                        //TODO Elaborate Error handling in response at the client side
                        if(code == 201)
                            return "Account Created";
                        else if (code == 250)
                            return "Invalid Details";
                        else if(code == 251)
                            return "Email already exists";
                        else if(code == -1)
                            return "Check Network Connection";
                        else return "Error in Sending Data. Try again later.";
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "Something Bad happened. Try again later.";
                    }
                }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                if (result == "Account Created") {
                    try {
                        writeFile();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    startGame();
                    //active_flag = 1;
                }
                else setContentView(R.layout.login_signup);
            }
    }
    //TODO Remove redundancies in PostUser and Login
    //TODO Implement more effective JSON serializer
    public void writeFile() throws JSONException, IOException {

        //EditText user_name = (EditText) findViewById(R.id.editText);
        //String name = user_name.getText().toString();
        //EditText pass = (EditText) findViewById(R.id.editText2);
        //String password =  pass.getText().toString();
        //User user = new User(name,password);
        //String json = "{ \"session\" : {\"email\" : \""+email+ "\" , \"password\" : \"" +password + "\" } }";
        JSONObject json = new JSONObject();
        json.accumulate("email",email);
        json.accumulate("password", password);
        FileOutputStream fos =openFileOutput("user.usr", Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeUTF(new JSONObject().accumulate("session", json).toString());
        oos.flush();

        //Intent intent = new Intent(this, CreateFile.class);
        //Button button = (Button) findViewById(R.id.confirmbutton);
        //String message = button.getText().toString();
        //intent.putExtra(Util.EXTRA_MESSAGE, message);
        //startActivity(intent);

    }

    // Temp testing for signup
    public JSONObject signupTest() throws JSONException {
        JSONObject json = new JSONObject();
        json.accumulate("name","Keerthana");
        json.accumulate("email",email = "keerukeerthana8@gmail.com");
        json.accumulate("password",password = "foobars");
        json.accumulate("password_confirmation", "foobars");
        JSONObject main = new JSONObject();
        main.accumulate("user", json);
        return main;
    }
    // Temp testing for login
    public JSONObject loginTest() throws JSONException {
        JSONObject json = new JSONObject();
        json.accumulate("email", email = "keerukeerthana8@gmail.com");
        json.accumulate("password", password = "foobars");
        return new JSONObject().accumulate("session",json);
    }



}
