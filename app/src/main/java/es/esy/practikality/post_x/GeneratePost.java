package es.esy.practikality.post_x;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class GeneratePost extends FragmentActivity {
    private static final int PICK_FROM_GALLERY = 2;
    ImageView text_post_image_background;
    TextView main_text;
    TextView bottom_right_text;
    EditText current_font_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_post);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //customise postx heading
        TextView postx_heading = findViewById(R.id.postx_text_post);
        postx_heading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf"));

        //set views to variables
        main_text = findViewById(R.id.text_post_main_text);
        bottom_right_text = findViewById(R.id.text_post_bottom_right_text);
        text_post_image_background = findViewById(R.id.text_post_image_background);
        current_font_size = findViewById(R.id.text_post_font_size);

        //create basic post by getting saved details from shared preferences file
        SharedPreferences sharedPreferences = getSharedPreferences("postx", Context.MODE_PRIVATE);
        String[] details = sharedPreferences.getString("env_details", "").split(" ");
        String[] bgcolor = details[0].split("textcodenew");
        String[] textcolor = details[1].split("textcodenew");
        String textstyle = details[2];
        String textalign = details[3];
        String maintext = sharedPreferences.getString("maintext", "not found");
        if (sharedPreferences.getString("bottomrighttext", "notfound").equals("no_bottom_right_text_needed")) {
            bottom_right_text.setVisibility(View.GONE);
        } else {
            String bottomtext = "~ " + sharedPreferences.getString("bottomrighttext", "notfound");
            bottom_right_text.setText(bottomtext);
        }
        main_text.setText(maintext);
        RelativeLayout linearLayout = findViewById(R.id.main_text_post_body);
        linearLayout.setBackgroundColor(Color.rgb(Integer.parseInt(bgcolor[0]), Integer.parseInt(bgcolor[1]), Integer.parseInt(bgcolor[2])));
        main_text.setTextColor(Color.rgb(Integer.parseInt(textcolor[0]), Integer.parseInt(textcolor[1]), Integer.parseInt(textcolor[2])));
        bottom_right_text.setTextColor(Color.rgb(Integer.parseInt(textcolor[0]), Integer.parseInt(textcolor[1]), Integer.parseInt(textcolor[2])));

        //set custom fonts
        Typeface custom_font;
        switch (textstyle) {
            case "monospace":
                main_text.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
                break;
            case "caviar_dreams":
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
                main_text.setTypeface(custom_font);
                break;
            case "coolvetica":
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/coolvetica.ttf");
                main_text.setTypeface(custom_font);
                break;
            case "helvetica":
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue.ttf");
                main_text.setTypeface(custom_font);
                break;
            case "oswald":
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
                main_text.setTypeface(custom_font);
                break;
            case "raleway":
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.ttf");
                main_text.setTypeface(custom_font);
                break;
            case "honey_script":
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/HoneyScript-SemiBold.ttf");
                main_text.setTypeface(custom_font);
                break;
            case "vaguely_repulsive":
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/vaguelyrepulsive.ttf");
                main_text.setTypeface(custom_font);
                break;
            case "sans_serif":
            default:
                main_text.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        }
        bottom_right_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.ttf"));

        //set text alignment
        switch (textalign) {
            case "right":
                main_text.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                break;
            case "center":
                main_text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                break;
            default:
                main_text.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                break;
        }
    }

    //change font size of text
    public void update_font_size(View view) {
        String fontSize = current_font_size.getText().toString();
        if (fontSize.length() > 0) {
            main_text.setTextSize(Integer.parseInt(fontSize));
        }
    }

    //share as post
    public void share(View view) {
        //convert view to Bitmap for sharing
        RelativeLayout relativeLayout = findViewById(R.id.main_text_post_body);
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(true);
        makeToast();

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

    //start intent for choosing image from gallery
    public void choose_image(View view) {
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
                        text_post_image_background.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

    //create toasts
    private void makeToast() {
        Toast.makeText(getApplicationContext(), "Creating an aesthetic post right away!", Toast.LENGTH_SHORT).show();
    }
}