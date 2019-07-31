package com.codepath.apps.restclienttemplate.models;


import org.json.JSONException;
import org.json.JSONObject;

public class User {


    public  long uid;
    public   String name;
    public   String SreenName;
    public   String profilImageUrl;
    public   String ScreenName;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        
        User user = new User();
        user.name = jsonObject.getString("name");
        user.uid= jsonObject.getLong("id");
        user.ScreenName =jsonObject.getString("screen_name");
        user.profilImageUrl =jsonObject.getString("profile_image_url");
        return user;
    }
}
