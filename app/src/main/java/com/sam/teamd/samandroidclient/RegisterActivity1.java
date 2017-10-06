package com.sam.teamd.samandroidclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        Spinner register_gender_spn = (Spinner) findViewById(R.id.register_gender_spn);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> register_genders_adapter = ArrayAdapter.createFromResource(this,
                R.array.register_genders_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        register_genders_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        register_gender_spn.setAdapter(register_genders_adapter);
    }

}
