package es.esy.practikality.post_x;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoadTemplates extends AppCompatActivity {

    FloatingActionButton floating_button;
    LinearLayout tweetLay;
    LinearLayout textLay;
    LinearLayout irfanLay;
    Animation rotate;
    Animation revrotate;
    Animation show;
    Animation hide;
    TextView tweet;
    TextView textp;
    TextView irfan_posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_templates);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //assign views to variables
        tweet = findViewById(R.id.twitter_post);
        textp = findViewById(R.id.textpost);
        irfan_posts = findViewById(R.id.irfanpost);
        floating_button = findViewById(R.id.main_floating_action_button);
        tweetLay = findViewById(R.id.linlayout_twitter_post);
        textLay = findViewById(R.id.linlayout_text_post);
        irfanLay = findViewById(R.id.linlayout_dank_irfan);
        rotate = AnimationUtils.loadAnimation(LoadTemplates.this, R.anim.rotate);
        revrotate = AnimationUtils.loadAnimation(LoadTemplates.this, R.anim.revrotate);
        show = AnimationUtils.loadAnimation(LoadTemplates.this, R.anim.showicons);
        hide = AnimationUtils.loadAnimation(LoadTemplates.this, R.anim.hideicons);

        //set custom fonts to template names
        Typeface helvetica = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.ttf");
        tweet.setTypeface(helvetica);
        textp.setTypeface(helvetica);
        irfan_posts.setTypeface(helvetica);

        //set template names invisible by default
        tweetLay.setVisibility(View.GONE);
        textLay.setVisibility(View.GONE);
        irfanLay.setVisibility(View.GONE);
    }

    //override back to make user select a post type
    @Override
    public void onBackPressed() {
        makeToast();
    }

    //show or hide template names
    public void show_template_names(View view) {
        if (tweetLay.getVisibility() == View.VISIBLE && textLay.getVisibility() == View.VISIBLE) {
            tweetLay.setVisibility(View.GONE);
            textLay.setVisibility(View.GONE);
            irfanLay.setVisibility(View.GONE);
            floating_button.startAnimation(revrotate);
            tweetLay.startAnimation(hide);
            textLay.startAnimation(hide);
            irfanLay.startAnimation(hide);
        } else {
            tweetLay.setVisibility(View.VISIBLE);
            textLay.setVisibility(View.VISIBLE);
            irfanLay.setVisibility(View.VISIBLE);
            floating_button.startAnimation(rotate);
            tweetLay.startAnimation(show);
            textLay.startAnimation(show);
            irfanLay.startAnimation(show);
        }
    }

    //go to tweet page
    public void create_twitter_post(View view) {
        startActivity(new Intent(this, TwitterMeme.class));
        finish();
    }

    //go to classic text post page
    public void create_text_post(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    //go to dank irfan meme generating page
    public void create_dank_irfan(View view) {
        startActivity(new Intent(this, DankIrfan.class));
    }

    //make toasts
    private void makeToast() {
        Toast.makeText(getApplicationContext(), "Select a post type to create a post", Toast.LENGTH_SHORT).show();
    }
}

