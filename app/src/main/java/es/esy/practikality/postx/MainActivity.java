package es.esy.practikality.postx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private String bgcolor,textcolor,textstyle,textalign,maintext,bottomtext;
    private int r = 0, g = 0, b = 0, r2 = 255, g2 = 255, b2 = 255;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bgcolor = r2 + "textcodenew" + g2 + "textcodenew" + b2;
        textcolor = r + "textcodenew" + g + "textcodenew" + b;
        textstyle = "notfilled";
        textalign = "";
        maintext = "";
        bottomtext = "";
        textColor(); //respond to text color changes in real time
        bgchange(); //respond to bg color changes in real time

        //populate spinner with font choice
        Spinner font_choices_spinner = (Spinner) findViewById(R.id.spinner_text_font);
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

    public void generate(View view){
        if(!textcolor.equals("notfilled")){
            if(!textstyle.equals("notfilled")){
                textalign = textAlign();
                if(!bgcolor.equals("")) {
                    EditText et1 = (EditText) findViewById(R.id.maintext);
                    EditText et2 = (EditText) findViewById(R.id.bottom_right_text);
                    maintext = et1.getText().toString();
                    bottomtext = et2.getText().toString();
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
                }
                else{makeToast("Please add the background color first");}
            }
        }
    }
    private void textColor(){
        SeekBar red = (SeekBar) findViewById(R.id.text_color_red);
        SeekBar green = (SeekBar) findViewById(R.id.text_color_green);
        SeekBar blue = (SeekBar) findViewById(R.id.text_color_blue);
        red.setMax(255);
        red.setProgress(r);
        green.setMax(255);
        green.setProgress(g);
        blue.setMax(255);
        blue.setProgress(b);
        final TextView heading = (TextView) findViewById(R.id.bgchange);

        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                r = i;
                heading.setTextColor(Color.rgb(r,g,b));
                textcolor = r + "textcodenew" + g + "textcodenew" + b;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Starts");}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Stops");}
        });
        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                g = i;
                heading.setTextColor(Color.rgb(r,g,b));
                textcolor = r + "textcodenew" + g + "textcodenew" + b;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Starts");}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Stops");}
        });
        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                b = i;
                heading.setTextColor(Color.rgb(r,g,b));
                textcolor = r + "textcodenew" + g + "textcodenew" + b;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Starts");}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Stops");}
        });
    }
    private void textStyle(String spinnerselection){
        TextView tv1 = (TextView) findViewById(R.id.bgchange);
        Typeface custom_font;
        switch (spinnerselection){
            case "Sans Serif":
                textstyle = "sans_serif";
                tv1.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                break;
            case "Monospace":
                textstyle = "monospace";
                tv1.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
                break;
            case "Caviar Dreams":
                textstyle = "caviar_dreams";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
                tv1.setTypeface(custom_font);
                break;
            case "Coolvetica":
                textstyle = "coolvetica";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/coolvetica.ttf");
                tv1.setTypeface(custom_font);
                break;
            case "Helvetica":
                textstyle = "helvetica";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue.ttf");
                tv1.setTypeface(custom_font);
                break;
            case "Oswald":
                textstyle = "oswald";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
                tv1.setTypeface(custom_font);
                break;
            case "Raleway":
                textstyle = "raleway";
                custom_font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.ttf");
                tv1.setTypeface(custom_font);
                break;
            default:
                textstyle = "notfilled";
        }
    }
    public void bgchange(){
        SeekBar red = (SeekBar) findViewById(R.id.bg_color_red);
        SeekBar green = (SeekBar) findViewById(R.id.bg_color_green);
        SeekBar blue = (SeekBar) findViewById(R.id.bg_color_blue);
        red.setMax(255);
        red.setProgress(r2);
        green.setMax(255);
        green.setProgress(g2);
        blue.setMax(255);
        blue.setProgress(b2);
        final TextView heading = (TextView) findViewById(R.id.bgchange);

        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                r2 = i;
                heading.setBackgroundColor(Color.rgb(r2,g2,b2));
                bgcolor = r2 + "textcodenew" + g2 + "textcodenew" + b2;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Starts");}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Stops");}
        });
        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                g2 = i;
                heading.setBackgroundColor(Color.rgb(r2,g2,b2));
                bgcolor = r2 + "textcodenew" + g2 + "textcodenew" + b2;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Starts");}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Stops");}
        });
        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean boo) {
                b2 = i;
                heading.setBackgroundColor(Color.rgb(r2,g2,b2));
                bgcolor = r2 + "textcodenew" + g2 + "textcodenew" + b2;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Starts");}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { System.out.println("Tracking Stops");}
        });
    }
    private String textAlign(){
        RadioButton r1 = (RadioButton) findViewById(R.id.align_left);
        RadioButton r2 = (RadioButton) findViewById(R.id.align_center);
        RadioButton r3 = (RadioButton) findViewById(R.id.align_right);
        if(r1.isChecked()){
            return "left";
        }else if(r2.isChecked()){
            return "center";
        }else if(r3.isChecked()) {
            return "right";
        }else {
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

    private void makeToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
