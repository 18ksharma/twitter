package com.codepath.apps.restclienttemplate;

import android.app.ActionBar;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets=tweets;
    }

    //FOr each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    //Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the data at position
        Tweet tweet = tweets.get(position);
        //Bind the tweet with view holder
        holder.bind(tweet);

    }

    //Pass in the context and list of tweets
    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //Clean all elements of the recycler
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    //Add a list of items
    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }


    //Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimestamp;
        ImageView ivContentImage;
        ImageView ivLike;
        ImageView ivLikePressed;
        ImageView ivRetweet;
        ImageView ivRetweetPressed;
        TwitterClient twitterClient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            ivContentImage = itemView.findViewById(R.id.ivContentImage);
            ivLike=itemView.findViewById(R.id.ivLike);
            ivLikePressed=itemView.findViewById(R.id.ivLikePressed);
            ivRetweet=itemView.findViewById(R.id.ivRetweet);
            ivRetweetPressed=itemView.findViewById(R.id.ivRetweetPressed);
        }

        //Helper method
        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            String relativeTime;
            relativeTime=getRelativeTimeAgo(tweet.createdAd);
            tvTimestamp.setText(relativeTime);
            Glide.with(context).load(tweet.user.publicImageUrl).transform(new RoundedCornersTransformation(50, 5)).into(ivProfileImage);


            if (tweet.tweetPics==null){
                ivContentImage.setVisibility(View.GONE);
            }
            else{
                ivContentImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.tweetPics).override(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT).into(ivContentImage);
            }

            ivLike.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    ivLike.setVisibility(View.INVISIBLE);
                    ivLikePressed.setVisibility(View.VISIBLE);
                    //twitterClient.likeTweet();
                }
            });

            ivRetweet.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    ivRetweet.setVisibility(View.INVISIBLE);
                    ivRetweetPressed.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    //Gets Relative time
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
