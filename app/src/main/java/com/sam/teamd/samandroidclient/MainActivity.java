package com.sam.teamd.samandroidclient;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");

        // To use the same font in all the APP
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "font/SourceSansPro-Regular.ttf", true);

        Button fontAwesomeUniversityIcon = (Button) findViewById(R.id.font_awesome_university_icon);
        fontAwesomeUniversityIcon.setTypeface(fontAwesomeFont);
    }
}
