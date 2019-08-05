package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailTweet extends AppCompatActivity {
    private TextView tvV1;
    private  TextView tvV2;
    ImageView imageView;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tweet);
        tvV1 = findViewById(R.id.tvV1);
        tvV2 = findViewById(R.id.tvV2);
        imageView =findViewById(R.id.imageView);
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("t"));
        tvV2.setText(tweet.body);
        tvV1.setText(tweet.user.SreenName);
        Glide.with(DetailTweet.this).load(tweet.user.profilImageUrl).into(imageView);



    }
}
