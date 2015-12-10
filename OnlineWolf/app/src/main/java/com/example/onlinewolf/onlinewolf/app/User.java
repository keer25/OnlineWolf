package com.example.onlinewolf.onlinewolf.app;

/**
 * Created by Thana on 10-Dec-15.
 */
//Constructor called with name and password as arguments
public class User {
    protected long id;
    protected String name;
    Stats statistics;
    protected String password;
    //Should initiate id and statistics
    public User(String arg,String argp){
        name = arg ;
        id = 0;
        password = argp;
    }
    public String getName(){
        return name;
    }
    public String passwordCheck(){
        return password;
    }
    public long getId(){
        return id;
    }

}
