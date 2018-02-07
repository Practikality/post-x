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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterActivity extends AppCompatActivity {
    private static int xyz = 1;
    private static int yyy = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_template);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView tx = (TextView) findViewById(R.id.tx);
        Typeface kul = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue.ttf");
        tx.setTypeface(kul);
        TextView caption = (TextView) findViewById(R.id.Textview);
        caption.setTypeface(kul);
    }
    public void addImage(View view)
    {
        Intent loadimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadimage,xyz);
    }

    @Override
    protected void onActivityResult(int request,int result,Intent data) {
        super.onActivityResult(request,result,data);
        if (request==xyz && request==RESULT_OK && data!=null) {
            Uri userImage = data.getData();
            String[] path = {MediaStore.Images.Media.DATA};
            Cursor cursor= getContentResolver().query(userImage,path,null,null,null);
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(path[0]);
            String picturepath = cursor.getString(index);
            cursor.close();
            ImageView meme = (ImageView)findViewById(R.id.memeimg);
            meme.setImageBitmap(BitmapFactory.decodeFile(picturepath));
        }
        }
    public void addprofile(View view)
    {
        Intent loadimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadimage,yyy);
    }



    }
