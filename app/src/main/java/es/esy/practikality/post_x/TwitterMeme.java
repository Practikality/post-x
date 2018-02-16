package es.esy.practikality.post_x;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class TwitterMeme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_meme);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //customise postx heading
        TextView postx_heading = findViewById(R.id.postx_tweet_details);
        postx_heading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf"));

    }

    public void generatememe(View view) {
        //assign values to variables
        EditText username_et = findViewById(R.id.tweet_details_maintext);
        EditText fullname_et = findViewById(R.id.tweet_details_name);
        EditText caption_et = findViewById(R.id.tweet_details_caption);
        EditText hashtags_et = findViewById(R.id.tweet_details_hashtags);

        //get data from edit text fields to variables
        String username = username_et.getText().toString().trim();
        String fullname = fullname_et.getText().toString().trim();
        String caption_in_tweet = caption_et.getText().toString().trim();
        String hashtags_in_tweet = hashtags_et.getText().toString().trim();
        SharedPreferences sharedPreferences = getSharedPreferences("postx", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("fullname", fullname);
        editor.putString("caption_post", caption_in_tweet);
        if (hashtags_in_tweet.length() > 0) {
            editor.putString("hashtags", hashtags_in_tweet);
        }
        editor.apply();

        //switch screen to go to twitter activity
        Intent intent = new Intent(TwitterMeme.this, TwitterActivity.class);
        startActivity(intent);
        finish();
    }

    //override back to go to LoadTemplates.class
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoadTemplates.class));
    }

}