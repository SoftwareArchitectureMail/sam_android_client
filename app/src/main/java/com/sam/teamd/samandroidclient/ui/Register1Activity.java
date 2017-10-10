package com.sam.teamd.samandroidclient.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sam.teamd.samandroidclient.R;

public class Register1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        Spinner spinner_register_gender = (Spinner) findViewById(R.id.spinner_register_gender);
        ArrayAdapter<CharSequence> adapter_register_genders = ArrayAdapter.createFromResource(this,
                R.array.register_genders_arr, android.R.layout.simple_spinner_item);
        adapter_register_genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_register_gender.setAdapter(adapter_register_genders);
    }

    public void next(View view) {
        Intent intent = new Intent(this, Register2Activity.class);
        startActivity(intent);
    }

}
