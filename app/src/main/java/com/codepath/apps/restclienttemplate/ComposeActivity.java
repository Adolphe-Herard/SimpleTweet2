package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    public static final int Max_Tweet_LENGTH =140;

    private EditText etCompose;
    private Button btnTweet;
    private TwitterClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client= TwitterApp.getRestClient(this);
        etCompose=findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "my tweet is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tweetContent.length() > Max_Tweet_LENGTH) {
                    Toast.makeText(ComposeActivity.this, "my tweet is to long", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_SHORT).show();

                client.composeTweet(tweetContent,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("TweeterClient","Succes apres les postes"+ response.toString());
                        try {
                            Tweet tweet= Tweet.fromJson(response);
                            Intent data = new Intent();
                            data.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, data);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.e("TweeterClient","Echec apres les postes"+ responseString);

                    }
                });
            }


        });
    }
}
