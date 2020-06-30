package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {
    public static final int MAX_TWEET_LENGTH = 280;
    EditText etCompose;
    Button btnTweet;
    TextView tvChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        tvChar = findViewById(R.id.tvChar);

        tvChar.setText("0");

        //Updates tvChar as the user types their Tweet
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int charSize = etCompose.getText().toString().length();
                tvChar.setText(String.valueOf(charSize));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int charSize = etCompose.getText().toString().length();
                tvChar.setText(String.valueOf(charSize));
            }

            @Override
            public void afterTextChanged(Editable s) {
                int charSize = etCompose.getText().toString().length();
                tvChar.setText(String.valueOf(charSize));
            }
        });

        //Set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this, "The tweet is empty", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(tweetContent.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this, "Your tweet is too long", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_SHORT).show();
            }
        });
        //Make an API call to Twitter to publish the tweet
    }
}