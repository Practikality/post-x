package es.esy.practikality.postx;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoadTemplates extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_templates);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView tx = (TextView)findViewById(R.id.tx);
        Typeface kul = Typeface.createFromAsset(getAssets(),  "fonts/coolvetica.ttf");
        tx.setTypeface(kul);
        TextView textp = (TextView) findViewById(R.id.textpost);
        textp.setTypeface(kul);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        final FloatingActionButton tweet = (FloatingActionButton) findViewById(R.id.twitter);
        FloatingActionButton text = (FloatingActionButton) findViewById(R.id.text);
        final LinearLayout tweetLay = (LinearLayout) findViewById(R.id.tweet) ;
        final LinearLayout textLay = (LinearLayout) findViewById(R.id.stext) ;
        final Animation rotate = AnimationUtils.loadAnimation(LoadTemplates.this,R.anim.rotate);
        final Animation revrotate = AnimationUtils.loadAnimation(LoadTemplates.this,R.anim.revrotate);
        final Animation show = AnimationUtils.loadAnimation(LoadTemplates.this,R.anim.showicons);
        final Animation hide = AnimationUtils.loadAnimation(LoadTemplates.this,R.anim.hideicons);
         tweetLay.setVisibility(View.GONE);
         textLay.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tweetLay.getVisibility() == View.VISIBLE && textLay.getVisibility() == View.VISIBLE) {
                    tweetLay.setVisibility(View.GONE);
                    textLay.setVisibility(View.GONE);
                    fab.startAnimation(revrotate);
                    tweetLay.startAnimation(hide);
                    textLay.startAnimation(hide);


                }
                else
                {
                    tweetLay.setVisibility(View.VISIBLE);
                    textLay.setVisibility(View.VISIBLE);
                    fab.startAnimation(rotate);
                    tweetLay.startAnimation(show);
                    textLay.startAnimation(show);


                }

            }
        });
    }
}
