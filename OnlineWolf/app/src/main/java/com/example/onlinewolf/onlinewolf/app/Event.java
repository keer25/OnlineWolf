package com.example.onlinewolf.onlinewolf.app;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by keerthana on 18/1/16.
 */
public class Event {

    String event;
    String data;
    Event(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        event = object.getString("event");
        data = object.getString("data");
    }

    protected String comp(){
        //Bone of mapping all events to respective functions
        String str = null;
        switch (event){
            case "wait":
                str = waitEvent();
                break;
        }
        return str;
    }

    protected String waitEvent(){
        StartGame.connID = data;
        return data + " players are ready to start the game.\n Require "+StartGame.np + " players to start the game";
    }
}
