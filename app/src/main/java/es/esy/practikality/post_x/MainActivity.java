package es.esy.practikality.post_x;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String bgcolor, textcolor, textstyle, textalign, maintext, bottomtext;
    private int r = 0, g = 0, b = 0, r2 = 255, g2 = 255, b2 = 255;
    TextView postx_heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //customise postx heading
        postx_heading = findViewById(R.id.postx_text_post_details);
        postx_heading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf"));

        //initialise prerequirements for spinner
        bgcolor = r2 + "textcodenew" + g2 + "textcodenew" + b2;
        textcolor = r + "textcodenew" + g + "textcodenew" + b;
        textstyle = "notfilled";
        textalign = "";
        maintext = "";
        bottomtext = "";
        textColor(); //respond to text color changes in real time
        bgchange(); //respond to bg color changes in real time

        //populate spinner with font choice
        Spinner font_choices_spinner = findViewById(R.id.spinner_text_font);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.font_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        font_choices_spinner.setAdapter(adapter);
        font_choices_spinner.setOnItemSelectedListener(this);

    }

    //override back to go to LoadTemplates.class
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoadTemplates.class));
    }

    public void generate(View view) {
        if (!textcolor.equals("notfilled")) {
            if (!textstyle.equals("notfilled")) {
                textalign = textAlign();
                if (!bgcolor.equals("")) {
                    EditText et1 = findViewById(R.id.maintext);
                    EditText et2 = findViewById(R.id.bottom_right_text);
                    maintext = et1.getText().toString();
                    if (et2.getText().toString().equals("")) {
                        bottomtext = "no_bottom_right_text_needed";
                    } else {
                        bottomtext = et2.getText().toString();
                    }
                    if (maintext.length() > 0 && bottomtext.length() > 0) {
                        SharedPreferences sharedPreferences = getSharedPreferences("postx", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("env_details", bgcolor + " " + textcolor + " " + textstyle + " " + textalign);
                        editor.putString("maintext", maintext);
                        editor.putString("bottomrighttext", bottomtext);
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, GeneratePost.class);
                        startActivity(intent);
                    } else {
                        makeToast("Enter valid texts to add");
                    }
                } else {
                    makeToast("Please add the background color first");
                }
            }
        }
    }

    private void textColor() {
        SeekBar red = findViewById(R.id.text_color_red);
        SeekBar green = findViewById(R.id.text_color_green);
        SeekBar blue = findViewById(R.id.text_color_blue);
        red.setMax(255);
        red.setProgress(r);
        green.setMax(255);
        green.setProgress(g);
        blue.setMax(255);
        blue.setProgress(b);

        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                r = i;
                postx_heading.setTextColor(Color.rgb(r, g, b));
                textcolor = r + "textcodenew" + g + "textcodenew" + b;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Starts");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Stops");
            }
        });
        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                g = i;
                postx_heading.setTextColor(Color.rgb(r, g, b));
                textcolor = r + "textcodenew" + g + "textcodenew" + b;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Starts");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Stops");
            }
        });
        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                b = i;
                postx_heading.setTextColor(Color.rgb(r, g, b));
                textcolor = r + "textcodenew" + g + "textcodenew" + b;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Starts");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Stops");
            }
        });
    }

    private void textStyle(String spinnerselection) {
        Typeface custom_font;
        switch (spinnerselection) {
            case "Sans Serif":
                textstyle = "sans_serif";
                postx_heading.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                break;
            case "Monospace":
                textstyle = "monospace";
                postx_heading.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
                break;
            case "Caviar Dreams":
                textstyle = "caviar_dreams";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
                postx_heading.setTypeface(custom_font);
                break;
            case "Coolvetica":
                textstyle = "coolvetica";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/coolvetica.ttf");
                postx_heading.setTypeface(custom_font);
                break;
            case "Helvetica":
                textstyle = "helvetica";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue.ttf");
                postx_heading.setTypeface(custom_font);
                break;
            case "Oswald":
                textstyle = "oswald";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
                postx_heading.setTypeface(custom_font);
                break;
            case "Raleway":
                textstyle = "raleway";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.ttf");
                postx_heading.setTypeface(custom_font);
                break;
            case "Honey Script":
                textstyle = "honey_script";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/HoneyScript-SemiBold.ttf");
                postx_heading.setTypeface(custom_font);
                break;
            case "Vaguely Repulsive":
                textstyle = "vaguely_repulsive";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/vaguelyrepulsive.ttf");
                postx_heading.setTypeface(custom_font);
                break;
            default:
                textstyle = "notfilled";
        }
    }

    public void bgchange() {
        SeekBar red = findViewById(R.id.bg_color_red);
        SeekBar green = findViewById(R.id.bg_color_green);
        SeekBar blue = findViewById(R.id.bg_color_blue);
        red.setMax(255);
        red.setProgress(r2);
        green.setMax(255);
        green.setProgress(g2);
        blue.setMax(255);
        blue.setProgress(b2);

        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                r2 = i;
                postx_heading.setBackgroundColor(Color.rgb(r2, g2, b2));
                bgcolor = r2 + "textcodenew" + g2 + "textcodenew" + b2;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Starts");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Stops");
            }
        });
        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                g2 = i;
                postx_heading.setBackgroundColor(Color.rgb(r2, g2, b2));
                bgcolor = r2 + "textcodenew" + g2 + "textcodenew" + b2;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Starts");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Stops");
            }
        });
        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                b2 = i;
                postx_heading.setBackgroundColor(Color.rgb(r2, g2, b2));
                bgcolor = r2 + "textcodenew" + g2 + "textcodenew" + b2;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Starts");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("Tracking Stops");
            }
        });
    }

    private String textAlign() {
        RadioButton r1 = findViewById(R.id.align_left);
        RadioButton r2 = findViewById(R.id.align_center);
        RadioButton r3 = findViewById(R.id.align_right);
        if (r1.isChecked()) {
            return "left";
        } else if (r2.isChecked()) {
            return "center";
        } else if (r3.isChecked()) {
            return "right";
        } else {
            makeToast("Please make a selection for text alignment");
            return "notfilled";
        }
    }

    //Respond to font selections
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        textStyle(parent.getItemAtPosition(pos).toString());
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
