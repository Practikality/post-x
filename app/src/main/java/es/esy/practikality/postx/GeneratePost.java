package es.esy.practikality.postx;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class GeneratePost extends FragmentActivity {
    String bgcolor, textcolor, textstyle, textalign, maintext, bottomtext;
    ShareDialog shareDialog;
    CallbackManager callbackManager;
    String image_path;
    private static final int PICK_FROM_GALLERY = 2;
    ImageView Mybackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_post);
        Mybackground = (ImageView) findViewById(R.id.Mybackground);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPreferences = getSharedPreferences("postx", Context.MODE_PRIVATE);
        String[] details = sharedPreferences.getString("env_details", "").split(" ");
        bgcolor = details[0];
        textcolor = details[1];
        textstyle = details[2];
        textalign = details[3];
        maintext = sharedPreferences.getString("maintext", "not found");
        bottomtext = sharedPreferences.getString("bottomrighttext", "not found");
        TextView tv1 = (TextView) findViewById(R.id.tofillwithmaintext);
        TextView tv2 = (TextView) findViewById(R.id.tofillwithbottomrightext);
        TextView tv3 = (TextView) findViewById(R.id.tofillwithbottomtoptext);
        tv1.setText(maintext);
        tv2.setText("~ " + bottomtext);
        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.mainpostbody);
        linearLayout.setBackgroundColor(Color.parseColor(bgcolor));
        tv1.setTextColor(Color.parseColor(textcolor));
        tv2.setTextColor(Color.parseColor(textcolor));
        tv3.setTextColor(Color.parseColor(textcolor));
        if (textstyle.equals("casual")) {
            tv1.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        } else if (textstyle.equals("condensed")) {
            tv1.setTypeface(Typeface.SERIF, Typeface.NORMAL);
        } else if (textstyle.equals("sansserif")) {
            tv1.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        }
        if (textalign.equals("right")) {
            tv1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else if (textalign.equals("center")) {
            tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }

    public void adjustSize(View view) {
        TextView tv1 = (TextView) findViewById(R.id.tofillwithmaintext);
        EditText et = (EditText) findViewById(R.id.font_size);
        String fontSize = et.getText().toString();
        if (fontSize.length() > 0) {
            float font = Integer.parseInt(fontSize);
            tv1.setTextSize(font);
        }
    }

    public void share(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.mainpostbody);
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache(true);

        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(true);
        Toast.makeText(getApplicationContext(), "Just making those finishing touches", Toast.LENGTH_SHORT).show();

        try {
            File cachePath = new File(getApplicationContext().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(getApplicationContext().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");

        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "es.esy.practikality.postx", newFile);

        if (contentUri != null) {
            Date dt = Calendar.getInstance().getTime();
            MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,dt.toString(),"Post x by Practikality");
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
    public void Gallery(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_FROM_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case PICK_FROM_GALLERY:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                        Mybackground.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

}