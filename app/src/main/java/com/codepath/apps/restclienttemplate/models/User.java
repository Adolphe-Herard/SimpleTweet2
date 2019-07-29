package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {


    private static long uid;
    private static String name;
    public  static String SreenName;
    public  static String profilImageUrl;
    private static String ScreenName;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        
        User user = new User();
        User.name = jsonObject.getString("name");
        User.uid= jsonObject.getLong("id");
        User.ScreenName =jsonObject.getString("Screen_Name");
        User.profilImageUrl =jsonObject.getString("profil_image_url");
        return user;
    }
}
