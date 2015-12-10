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
        FileInputStream inp = null;
        try{
            inp = openFileInput("user.usr");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (inp == null) {
                setContentView(R.layout.activity_show_login);
            }
            else
                setContentView(R.layout.activity_create_file);
            }
        }
    public void createUserFile(View view){
        EditText user_name = (EditText) findViewById(R.id.editText);
        String name = user_name.getText().toString();
        EditText pass = (EditText) findViewById(R.id.editText2);
        String password =  pass.getText().toString();
        User user = new User(name,password);
        try {
            FileOutputStream fos =openFileOutput("user.usr", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        }catch (Exception ex){
            ex.printStackTrace();
        }
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
