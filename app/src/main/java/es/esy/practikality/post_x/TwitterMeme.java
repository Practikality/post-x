package es.esy.practikality.post_x;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class TwitterMeme extends AppCompatActivity {
    private static int xyz = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_meme);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public void generatememe (View view){
        EditText username = (EditText)findViewById(R.id.maintext);
        EditText fullname = (EditText)findViewById(R.id.bottom_right_text);
        EditText caption = (EditText) findViewById(R.id.caption_text);
        EditText hashtags = (EditText) findViewById(R.id.hashtags);
        String user_name = username.getText().toString().trim();
        String full_name = fullname.getText().toString().trim();
        String caption_post = caption.getText().toString().trim();
        String hashtags_post = hashtags.getText().toString().trim();
        SharedPreferences sharedPreferences = getSharedPreferences("postx", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",user_name);
        editor.putString("fullname",full_name);
        editor.putString("caption_post",caption_post);
        if(hashtags_post.length()>0){
            editor.putString("hashtags",hashtags_post);
        }
        editor.apply();
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