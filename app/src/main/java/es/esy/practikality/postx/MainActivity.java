package es.esy.practikality.postx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String bgcolor,textcolor,textstyle,textalign,maintext,bottomtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bgcolor = "";
        textcolor = "";
        textstyle = "";
        textalign = "";
        maintext = "";
        bottomtext = "";
    }
    public void darkgreen(View view){
        bgcolor = "#1abc9c";
        bgchange(bgcolor);
    }
    public void lightgreen(View view){
        bgcolor = "#2ecc71";
        bgchange(bgcolor);
    }
    public void blue(View view){
        bgcolor = "#3498db";
        bgchange(bgcolor);
    }
    public void purple(View view){
        bgcolor = "#9b59b6";
        bgchange(bgcolor);
    }
    public void almostblack(View view){
        bgcolor = "#34495e";
        bgchange(bgcolor);
    }
    public void orange(View view){
        bgcolor = "#f39c12";
        bgchange(bgcolor);
    }
    public void red(View view){
        bgcolor = "#e74c3c";
        bgchange(bgcolor);
    }
    public void white(View view){
        bgcolor = "#ecf0f1";
        bgchange(bgcolor);
    }
    public void generate(View view){
        textcolor = textColor();
        if(!textcolor.equals("notfilled")){
            textstyle = textStyle();
            if(!textcolor.equals("notfilled")){
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
    private String textColor(){
        RadioButton r1 = (RadioButton) findViewById(R.id.textcolor_white);
        RadioButton r2 = (RadioButton) findViewById(R.id.textcolor_black);
        if(r1.isChecked()){
            return "#ffffff";
        }else if(r2.isChecked()){
            return "#000000";
        }else{
            makeToast("Please make a selection for text color");
            return "notfilled";
        }
    }
    private String textStyle(){
        RadioButton r1 = (RadioButton) findViewById(R.id.family_casual);
        RadioButton r2 = (RadioButton) findViewById(R.id.family_condensed);
        RadioButton r3 = (RadioButton) findViewById(R.id.family_sansserif);
        if(r1.isChecked()){
            return "casual";
        }else if(r2.isChecked()){
            return "condensed";
        }else if(r3.isChecked()) {
            return "sansserif";
        }else {
            makeToast("Please make a selection for text style");
            return "notfilled";
        }
    }
    public void bgchange(String colorcode){
        TextView tv = (TextView) findViewById(R.id.bgchange);
        tv.setBackgroundColor(Color.parseColor(colorcode));
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
    private void makeToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
