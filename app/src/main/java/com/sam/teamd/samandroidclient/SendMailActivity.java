package com.sam.teamd.samandroidclient;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SendMailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        // Calendar for date picker
        final Calendar send_mail_calendar = Calendar.getInstance();
        // Edit text for date
        final EditText send_mail_date_input = (EditText) findViewById(R.id.input_send_mail_send_date);
        // Edit text for time
        final EditText send_mail_hour_input = (EditText) findViewById(R.id.input_send_mail_send_hour);

        // Method for date picker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                send_mail_calendar.set(Calendar.YEAR, year);
                send_mail_calendar.set(Calendar.MONTH, monthOfYear);
                send_mail_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String send_mail_date_format = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(send_mail_date_format, Locale.US);

                send_mail_date_input.setText(sdf.format(send_mail_calendar.getTime()));
            }
        };
        send_mail_date_input.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SendMailActivity.this, date, send_mail_calendar
                        .get(Calendar.YEAR), send_mail_calendar.get(Calendar.MONTH),
                        send_mail_calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Method for time picker
        send_mail_hour_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar send_mail_current_hour = Calendar.getInstance();
                int hour = send_mail_current_hour.get(Calendar.HOUR_OF_DAY);
                int minute = send_mail_current_hour.get(Calendar.MINUTE);

                TimePickerDialog send_mail_time_picker;
                send_mail_time_picker = new TimePickerDialog(SendMailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        send_mail_hour_input.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                send_mail_time_picker.setTitle("Select Time");
                send_mail_time_picker.show();

            }
        });
    }
}
