package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;

    private TwitterClient client;
     private RecyclerView rvTweets;
     private TweetsAdapter Adapter;
     private List<Tweet> tweet;
     private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Toast.makeText(this, "Timeline", Toast.LENGTH_SHORT).show();
        client = TwitterApp.getRestClient(this);
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        //Find the recyclerview
        rvTweets = findViewById(R.id.rvTweets);
        //initialiaze list of tweet
        tweet =new ArrayList<>();
        Adapter= new TweetsAdapter(this,tweet);
        //Recycler view setup
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(Adapter);
        populateHomeTimeLine();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Toast.makeText(TimelineActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                populateHomeTimeLine();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.compose){
            Intent i = new Intent(this,ComposeActivity.class);
            startActivityForResult(i,REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweet.add(0,tweet);
            Adapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
        }
    }

    private void populateHomeTimeLine() {
        client.getHomeTimeLine(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient",response.toString());
                // iterate
                List<Tweet> tweetsToAdd = new ArrayList<>();
                for (int i = 0; i< response.length(); i++){
                    try {
                        JSONObject jsonTweetObject = response.getJSONObject(i);
                     Tweet tweets = Tweet.fromJson(jsonTweetObject);
                     tweetsToAdd.add(tweets);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                Adapter.clear();
                Adapter.addTweets(tweetsToAdd);
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(TimelineActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(TimelineActivity.this, "Failure again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
