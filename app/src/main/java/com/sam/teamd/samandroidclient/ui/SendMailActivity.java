package com.sam.teamd.samandroidclient.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.Mail;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.service.Api;
import com.sam.teamd.samandroidclient.service.MailClient;
import com.sam.teamd.samandroidclient.service.UserClient;
import com.sam.teamd.samandroidclient.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMailActivity extends AppCompatActivity {
    private static final String LOG_TAG = SendMailActivity.class.getSimpleName();

    private MailClient mailClient;
    private EditText inputDate, inputHour;
    private CheckBox chkboxPostpone;
    private Button btnSend;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        mailClient = Api.getInstance(getApplicationContext()).getMailClient();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(Constants.EXTRA_USER);
        
        // Calendar for date picker
        final Calendar send_mail_calendar = Calendar.getInstance();
        // Edit text for date
        inputDate = (EditText) findViewById(R.id.input_send_mail_send_date);
        inputDate.setEnabled(false);
        // Edit text for time
        inputHour = (EditText) findViewById(R.id.input_send_mail_send_hour);
        inputHour.setEnabled(false);

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
                String send_mail_date_format = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(send_mail_date_format, Locale.US);

                inputDate.setText(sdf.format(send_mail_calendar.getTime()));
            }
        };
        inputDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SendMailActivity.this, date, send_mail_calendar
                        .get(Calendar.YEAR), send_mail_calendar.get(Calendar.MONTH),
                        send_mail_calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Method for time picker
        inputHour.setOnClickListener(new View.OnClickListener() {
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
                        inputHour.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                send_mail_time_picker.setTitle("Select Time");
                send_mail_time_picker.show();

            }
        });

        chkboxPostpone = (CheckBox) findViewById(R.id.checkbox_send_mail_postpone_sending);
        chkboxPostpone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    inputDate.setEnabled(true);
                    inputHour.setEnabled(true);
                }else{
                    inputDate.setEnabled(false);
                    inputHour.setEnabled(false);
                }

            }
        });
        
        btnSend = (Button) findViewById(R.id.btn_send_mail_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

    }

    private void validateFields() {
        boolean errors = false;
        String recipient, subject, text, cc;
        Date date = null;
        Boolean urgent, confirmation;

        recipient  = validateTextField(findViewById(R.id.input_send_mail_recipient));
        errors = (recipient == null) || errors;

        subject = validateTextField(findViewById(R.id.input_send_mail_subject));
        errors = (subject == null) || errors;

        text = validateTextField(findViewById(R.id.input_send_mail_message));
        errors = (text == null) || errors;

        cc = getTextField(findViewById(R.id.input_send_mail_cc));

        if(chkboxPostpone.isChecked()){
            date = getDate();
            errors = (date == null) || errors;
        }

        urgent = getCheckBox((CheckBox) findViewById(R.id.checkbox_send_mail_urgent));
        confirmation = getCheckBox((CheckBox) findViewById(R.id.checkbox_send_mail_view_confirmation));



        if(!errors){
            Mail mail;
            if(chkboxPostpone.isChecked()){
                //TODO Validar los booleanos
                mail = new Mail(recipient, user.getUsername(), cc, subject, text, date, true, urgent, confirmation);
            }else{
                mail = new Mail(recipient, user.getUsername(), cc, subject, text,  false, urgent, confirmation);
            }

            sendMail(mail);


            Log.d(LOG_TAG, recipient);
            Log.d(LOG_TAG, subject);
            Log.d(LOG_TAG, text);
            Log.d(LOG_TAG, cc);
            Log.d(LOG_TAG, String.valueOf(date));

        }
    }

    private void sendMail(final Mail mail) {
        String token = loadToken();
        Call<ResponseBody> call = mailClient.sendMail(token, mail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(LOG_TAG, response.toString());
                if(response.isSuccessful()){
                    Intent intent = new Intent();
                    setResult(Constants.RESULT_OK, intent);
                    finish();
                }else if(response.code() == 401){
                    sendMail(mail);
                }
                else{
                    Toast.makeText(SendMailActivity.this, "Error Al enviar correo" + response.toString() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(LOG_TAG, "Error enviando correo", t);
                Toast.makeText(SendMailActivity.this, getString(R.string.conection_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean getCheckBox(CheckBox chk){
        return chk.isChecked();
    }

    private String loadToken(){
        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPref.getString(Constants.SHARED_PREF_TOKEN, null);
        return token;
    }

    private String getTextField(View v){
        EditText editText = (EditText) v;
        String text = editText.getText().toString();
        if (text.length() == 0) {
            text = "";
        }
        return text;
    }

    private String validateTextField(View v) {
        EditText editText = (EditText) v;
        String text = editText.getText().toString();
        if (text.length() == 0) {
            editText.setError(getString(R.string.empty_fielf_error));
            text = null;
        }
        return text;
    }

    private Date getDate() {
        Date date = null;
        String dateText = inputDate.getText().toString();
        String hourText = inputHour.getText().toString();
        if(dateText.length() > 0 && hourText.length() > 0){
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.US);
            try {
                date = format.parse(dateText + " " + hourText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}
