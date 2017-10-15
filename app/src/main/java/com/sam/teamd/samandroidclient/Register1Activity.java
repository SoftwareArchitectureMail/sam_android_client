package com.sam.teamd.samandroidclient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        // Calendar for date picker
        final Calendar register_birthdate_calendar = Calendar.getInstance();
        // Edit text for date
        final EditText register_birthdate_input = (EditText) findViewById(R.id.input_register_birthdate);
        // Method for date picker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                register_birthdate_calendar.set(Calendar.YEAR, year);
                register_birthdate_calendar.set(Calendar.MONTH, monthOfYear);
                register_birthdate_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String send_mail_date_format = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(send_mail_date_format, Locale.US);

                register_birthdate_input.setText(sdf.format(register_birthdate_calendar.getTime()));
            }
        };
        register_birthdate_input.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Register1Activity.this, date, register_birthdate_calendar
                        .get(Calendar.YEAR), register_birthdate_calendar.get(Calendar.MONTH),
                        register_birthdate_calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Spinner for gender - there is only 2 genders btw
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
