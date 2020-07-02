package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys =  @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @PrimaryKey
    @ColumnInfo
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAd;

    @ColumnInfo
    public long userId;

    @Ignore
    public User user;

    @ColumnInfo
    public String tweetPics;

    //empty constructor needed for the Parceler library
    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAd = jsonObject.getString("created_at");
        tweet.id=jsonObject.getLong("id");
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;

        //getting image url
        JSONObject entities = jsonObject.getJSONObject("entities");
        //Log.i("Tweets", "Entities: "+entities.toString());

        //If there is media in entities, adds images to tweet object
        if(entities.has("media")){
            tweet.tweetPics=entities.getJSONArray("media").getJSONObject(0).getString("media_url_https");
            Log.i("Tweet", tweet.tweetPics);
        }
        else {
            tweet.tweetPics=null;
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i<jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
