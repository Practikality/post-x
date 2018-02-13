package es.esy.practikality.post_x;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class DankIrfan extends AppCompatActivity {
    EditText top;
    EditText bottom;
    EditText myboy_top;
    EditText myboy_bottom;
    ImageView irfan_image;
    TextView postx_heading;
    String current_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dank_irfan);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set_views_to_variables
        postx_heading = (TextView) findViewById(R.id.postx_dank_irfan);
        top = (EditText) findViewById(R.id.irfan_text_top);
        myboy_top = (EditText) findViewById(R.id.irfan_myboy_top_text);
        bottom = (EditText) findViewById(R.id.irfan_text_bottom);
        myboy_bottom = (EditText) findViewById(R.id.irfan_myboy_bottom_text);
        current_image = "haveli";
        irfan_image = (ImageView) findViewById(R.id.irfan_image_view);
        irfan_image.setBackgroundResource(R.drawable.irfan_haveli);

        //set_fonts_for_text
        Typeface impact = Typeface.createFromAsset(getAssets(), "fonts/impact.ttf");
        Typeface stilu_Regular = Typeface.createFromAsset(getAssets(), "fonts/stilu-Regular.otf");
        Typeface dreams = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        postx_heading.setTypeface(dreams);
        top.setTypeface(impact);
        bottom.setTypeface(impact);
        myboy_top.setTypeface(stilu_Regular);
        myboy_bottom.setTypeface(stilu_Regular);

    }

    //override back to go to LoadTemplates.class
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoadTemplates.class));
        finish();
    }

    public void change_image(View view){
        switch (current_image){
            case "haveli":
                irfan_image.setBackgroundResource(R.drawable.irfan_kid);
                top.setVisibility(View.VISIBLE);
                bottom.setVisibility(View.VISIBLE);
                myboy_top.setVisibility(View.GONE);
                myboy_bottom.setVisibility(View.GONE);
                current_image = "kid";
                break;
            case "kid":
                irfan_image.setBackgroundResource(R.drawable.irfan_myboy);
                top.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);
                myboy_top.setVisibility(View.VISIBLE);
                myboy_bottom.setVisibility(View.VISIBLE);
                current_image = "myboy";
                break;
            case "myboy":
                irfan_image.setBackgroundResource(R.drawable.irfan_clever);
                top.setVisibility(View.VISIBLE);
                bottom.setVisibility(View.VISIBLE);
                myboy_top.setVisibility(View.GONE);
                myboy_bottom.setVisibility(View.GONE);
                current_image = "clever";
                break;
            case "clever":
                irfan_image.setBackgroundResource(R.drawable.irfan_charlie);
                top.setVisibility(View.VISIBLE);
                bottom.setVisibility(View.VISIBLE);
                myboy_top.setVisibility(View.GONE);
                myboy_bottom.setVisibility(View.GONE);
                current_image = "charlie";
                break;
            case "charlie":
            default:
                irfan_image.setBackgroundResource(R.drawable.irfan_haveli);
                top.setVisibility(View.VISIBLE);
                bottom.setVisibility(View.GONE);
                myboy_top.setVisibility(View.GONE);
                myboy_bottom.setVisibility(View.GONE);
                current_image = "haveli";
                break;
        }
    }

    public void share(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.irfan_meme_body);
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(true);
        makeToast("Just making those finishing touches");

        try {
            File cachePath = new File(getApplicationContext().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found error");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception error");
        }
        File imagePath = new File(getApplicationContext().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");

        Uri contentUri = FileProvider.getUriForFile(this, "es.esy.practikality.post_x", newFile);

        if (contentUri != null) {
            Date dt = Calendar.getInstance().getTime();
            MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,dt.toString(),"Post X by Practikality");
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Shared with #PostXbyPractikality");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared with #PostXbyPractikality");
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }

    private void makeToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
