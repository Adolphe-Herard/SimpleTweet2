package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetsAdapter  extends RecyclerView.Adapter <TweetsAdapter.ViewHolder> {

    private Context context;
    private List<Tweet> tweets;



    // Pass in context list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //for each row,inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
       View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, viewGroup,false);
        return new ViewHolder(view);
    }
    //bind values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Tweet tweet = tweets.get(i);
        viewHolder.bind(tweet);


    }
    @Override
    public int getItemCount() {
        return tweets.size();
    }
    public void clear () {
        tweets.clear();
        notifyDataSetChanged();

    }

    public void addTweets(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();


    }






    //Define the viewholder

    public class ViewHolder extends RecyclerView.ViewHolder{

            public ImageView ivProfileImage;
            public TextView tvSreenName;
            public TextView tvBody;
            public RelativeLayout relativeLayout;

            public ViewHolder(View itemView) {
            super(itemView);
            ivProfileImage= itemView.findViewById(R.id.ivProfileImage);
            tvSreenName = itemView.findViewById(R.id.tvSreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            relativeLayout =itemView.findViewById(R.id.relative);
        }

        public void bind(final Tweet tweet) {
                tvBody.setText(tweet.body);
            tvSreenName.setText(tweet.user.SreenName);
            Glide.with(context).load(tweet.user.profilImageUrl).into(ivProfileImage);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DetailTweet.class);
                    intent.putExtra("t", Parcels.wrap(tweet));
                    context.startActivity(intent);
                }
            });

        }
    }
}
