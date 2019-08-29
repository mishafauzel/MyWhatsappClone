package com.example.mywhatsappclone.utills;

import android.util.Log;

import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class SendNotification {
    public SendNotification(String message, String heading,String notificationKey) {
        JSONObject notivicationContent=new JSONObject();
        try {
            notivicationContent.put("content",new JSONObject().put("en",message));
            notivicationContent.put("include_players_ids",new JSONArray().put(notificationKey));
            notivicationContent.put("headings",new JSONObject().put("en",heading));
            Log.i("Json",notivicationContent.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Json","error"+e.getMessage());
        }
        OneSignal.postNotification(notivicationContent,null);
    }

}
