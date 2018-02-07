package es.esy.practikality.postx;

import android.content.Intent;
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
        EditText username = (EditText)findViewById(R.id.maintext);
        EditText fullname = (EditText)findViewById(R.id.bottom_right_text);
        EditText caption = (EditText) findViewById(R.id.caption_text);
        TextView tv = (TextView) findViewById(R.id.username);
        TextView tv1 = (TextView) findViewById(R.id.caption);
        TextView tv2 = (TextView) findViewById(R.id.fullname);
        tv.setText(username.getText().toString());
        tv1.setText(fullname.getText().toString());
        tv2.setText(caption.getText().toString());
    }
    public void generatememe (View view){
        startActivity(new Intent(TwitterMeme.this,TwitterActivity.class));
    }
    public void nokeyboard(){
        InputMethodManager inp = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inp.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
}