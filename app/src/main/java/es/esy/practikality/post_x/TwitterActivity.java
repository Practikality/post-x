package es.esy.practikality.post_x;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class TwitterActivity extends AppCompatActivity {
    private static final int PICK_FROM_GALLERY = 2;
    ImageView profilePicture;
    ImageView tweetImage;
    boolean profile_picture_clicked;
    TextView username;
    TextView fullname;
    TextView caption;
    TextView hashtags;
    EditText font_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_template);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        makeToast("Click on profile icon to set an image.");

        //customise postx heading
        TextView postx_heading = findViewById(R.id.postx_tweet);
        postx_heading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf"));

        //assign values to variables
        profilePicture = findViewById(R.id.profile_picture_of_tweet);
        tweetImage = findViewById(R.id.tweet_image);
        profile_picture_clicked = false;
        username = findViewById(R.id.tweet_username);
        fullname = findViewById(R.id.tweet_fullname);
        caption = findViewById(R.id.tweet_caption);
        hashtags = findViewById(R.id.hashtags_in_tweet);
        font_size = findViewById(R.id.tweet_font_size);

        //set custom fonts
        Typeface stilu_Regular = Typeface.createFromAsset(getAssets(), "fonts/stilu-Regular.otf");
        Typeface stilu_Light = Typeface.createFromAsset(getAssets(), "fonts/stilu-Light.otf");
        username.setTypeface(stilu_Light);
        caption.setTypeface(stilu_Light);
        fullname.setTypeface(stilu_Regular);
        hashtags.setTypeface(stilu_Light);

        //set tweet details stored in Shared Preferences file
        SharedPreferences sharedPreferences = getSharedPreferences("postx", Context.MODE_PRIVATE);
        String username_value = "@" + sharedPreferences.getString("username", "UserName");
        username.setText(username_value);
        fullname.setText(sharedPreferences.getString("fullname", "Full Name"));
        caption.setText(sharedPreferences.getString("caption_post", "No Caption Found!"));

        //place a # symbol before all hashtags (if any)
        String stored_hashtags = sharedPreferences.getString("hashtags", "none_found");
        if (!stored_hashtags.equals("none_found")) {
            String[] hashtags_array = stored_hashtags.split(" ");
            StringBuilder stringBuilder = new StringBuilder();
            for (String tag : hashtags_array) {
                String current_tag = "#" + tag + " ";
                stringBuilder.append(current_tag);
            }
            hashtags.setText(stringBuilder);
        } else {
            hashtags.setVisibility(View.GONE);
        }
    }

    //update font size if user wants
    public void adjustSize(View view) {
        String entered_font_size = font_size.getText().toString();
        if (entered_font_size.length() > 0) {
            caption.setTextSize(Integer.parseInt(entered_font_size));
        }
    }

    //replace the profile picture
    public void add_profile_image(View view) {
        profile_picture_clicked = true;
        Gallery();
    }

    //add an image to tweet
    public void add_tweet_image(View view) {
        profile_picture_clicked = false;
        Gallery();
    }

    //choose image for profile picture or to add to the tweet
    public void Gallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case PICK_FROM_GALLERY:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                        //check if profile picture was clicked or the add image button
                        if (profile_picture_clicked) {
                            profilePicture.setImageBitmap(bitmap);
                        } else {
                            tweetImage.setImageBitmap(bitmap);
                            tweetImage.setVisibility(View.VISIBLE);
                        }
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

    //share as post
    public void share(View view) {
        //convert view to Bitmap for sharing
        LinearLayout linearLayout = findViewById(R.id.tweet_body);
        linearLayout.setDrawingCacheEnabled(true);
        linearLayout.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(linearLayout.getDrawingCache());
        linearLayout.setDrawingCacheEnabled(true);
        makeToast("Just making those finishing touches");

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
            finish();
        }
    }

    //create toasts
    private void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
