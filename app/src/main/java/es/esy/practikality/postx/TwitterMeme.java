package es.esy.practikality.postx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        String user_name = username.getText().toString();
        String full_name = fullname.getText().toString();
        String caption_post = caption.getText().toString();
        String hashtags_post = hashtags.getText().toString();
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
    }
}