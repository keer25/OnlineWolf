//User data in file name "user.txt"
package com.example.onlinewolf.onlinewolf.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

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
        setContentView(R.layout.login);

    }
    public void createUser(View view){
        EditText edit = (EditText) findViewById(R.id.emailLogin);
        String email =  edit.getText().toString();
        edit = (EditText) findViewById(R.id.passwordLogin);
        String password = edit.getText().toString();
        //TODO Bring some validations to client Side
        //Send http request with proper params
    }

    public void authUser(View view){
        EditText edit = (EditText) findViewById(R.id.nameSignup);
        String name = edit.getText().toString();
        edit = (EditText) findViewById(R.id.emailSignup);
        String email =  edit.getText().toString();
        edit = (EditText) findViewById(R.id.passwordSignup);
        String password = edit.getText().toString();
        edit = (EditText) findViewById(R.id.confirmSignup);
        String conf_password = edit.getText().toString();
        //Perform validation
        //Send http request with proper params
    }
    public void signup(View view){
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
