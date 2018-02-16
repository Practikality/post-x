package es.esy.practikality.post_x;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
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
    EditText text_top;
    EditText text_bottom;
    EditText drake_top;
    EditText drake_bottom;
    ImageView irfan_base_image;
    String current_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dank_irfan);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //customise postx heading
        TextView postx_heading = findViewById(R.id.postx_dank_irfan);
        postx_heading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf"));

        //set_views_to_variables
        text_top = findViewById(R.id.dank_irfan_top_text);
        text_bottom = findViewById(R.id.dank_irfan_text_bottom);
        drake_top = findViewById(R.id.irfan_myboy_top_text);
        drake_bottom = findViewById(R.id.irfan_myboy_bottom_text);
        current_image = "haveli";
        irfan_base_image = findViewById(R.id.irfan_base_image);

        //set custom fonts for text views
        Typeface impact = Typeface.createFromAsset(getAssets(), "fonts/impact.ttf");
        Typeface stilu_Regular = Typeface.createFromAsset(getAssets(), "fonts/stilu-Regular.otf");
        text_top.setTypeface(impact);
        text_bottom.setTypeface(impact);
        drake_top.setTypeface(stilu_Regular);
        drake_bottom.setTypeface(stilu_Regular);

        //set default irfan meme base image
        irfan_base_image.setBackgroundResource(R.drawable.irfan_haveli);
    }

    //override back to go to LoadTemplates.class
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoadTemplates.class));
        finish();
    }

    //to switch between base images for memes
    public void change_image(View view) {
        switch (current_image) {
            case "haveli":
                response_to_base_image_switching(R.drawable.irfan_kid, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
                current_image = "kid";
                break;
            case "kid":
                response_to_base_image_switching(R.drawable.irfan_myboy, View.GONE, View.GONE, View.VISIBLE, View.VISIBLE);
                current_image = "myboy";
                break;
            case "myboy":
                response_to_base_image_switching(R.drawable.irfan_clever, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
                current_image = "clever";
                break;
            case "clever":
                response_to_base_image_switching(R.drawable.irfan_charlie, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
                current_image = "charlie";
                break;
            case "charlie":
            default:
                response_to_base_image_switching(R.drawable.irfan_haveli, View.VISIBLE, View.GONE, View.GONE, View.GONE);
                current_image = "haveli";
                break;
        }
    }

    //handle ui responses to base image switching
    private void response_to_base_image_switching(int image_name, int text_top_visibility, int text_bottom_visibility, int drake_top_visibility, int drake_bottom_visibility) {
        irfan_base_image.setBackgroundResource(image_name);
        text_top.setVisibility(text_top_visibility);
        text_bottom.setVisibility(text_bottom_visibility);
        drake_top.setVisibility(drake_top_visibility);
        drake_bottom.setVisibility(drake_bottom_visibility);
    }

    //share as post
    public void share(View view) {
        //convert view to Bitmap for sharing
        makeToast();
        RelativeLayout relativeLayout = findViewById(R.id.irfan_meme_body);
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(true);

        //save bitmap temporarily in cache
        try {
            File cachePath = new File(getApplicationContext().getCacheDir(), "images");
            boolean if_successful = cachePath.mkdirs();
            if (!if_successful) {
                System.out.println("Couldn't make the directory");
            }
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

        //create intent for sharing generated bitmap
        if (contentUri != null) {
            Date dt = Calendar.getInstance().getTime();
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, dt.toString(), "Post X by Practikality");
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

    //create toasts
    private void makeToast() {
        Toast.makeText(getApplicationContext(), "Just making those finishing touches", Toast.LENGTH_SHORT).show();
    }
}
