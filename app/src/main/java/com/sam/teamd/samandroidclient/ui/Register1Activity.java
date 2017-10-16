package com.sam.teamd.samandroidclient.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.service.Api;
import com.sam.teamd.samandroidclient.service.UserClient;
import com.sam.teamd.samandroidclient.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register1Activity extends AppCompatActivity {

    private static final String LOG_TAG = Register1Activity.class.getSimpleName();
    private UserClient userClient;


    private Button btnNext;
    private EditText inputBirthdate;
    private Spinner spinnerGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        userClient = Api.getInstance(getApplicationContext()).getUserClient();

        // Calendar for date picker
        final Calendar register_birthdate_calendar = Calendar.getInstance();
        // Edit text for date
        inputBirthdate = (EditText) findViewById(R.id.input_register_birthdate);
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
                String send_mail_date_format = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(send_mail_date_format, Locale.US);

                inputBirthdate.setText(sdf.format(register_birthdate_calendar.getTime()));
            }
        };

        inputBirthdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Register1Activity.this, date, register_birthdate_calendar
                        .get(Calendar.YEAR), register_birthdate_calendar.get(Calendar.MONTH),
                        register_birthdate_calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        spinnerGender = (Spinner) findViewById(R.id.spinner_register_gender);
        ArrayAdapter<CharSequence> adapter_register_genders = ArrayAdapter.createFromResource(this,
                R.array.register_genders_array, android.R.layout.simple_spinner_item);
        adapter_register_genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter_register_genders);

        btnNext = (Button) findViewById(R.id.btn_register_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextForm();
            }
        });
    }


    private void nextForm(){
        boolean errors = false;
        String name, lastName, phone, gender;
        Date birthDate;

        name = validateTextField(findViewById(R.id.input_register_name));
        errors = (name == null) || errors;

        lastName = validateTextField(findViewById(R.id.input_register_lastname));
        errors = (lastName == null) || errors;

        phone = validateTextField(findViewById(R.id.input_register_phone));
        errors = (phone == null) || errors;

        gender = getSpinnerValue(spinnerGender);
        errors = (gender == null) || errors;

        //TODO esperar a que steven se le de la gana de implementar el maldito date picker
        birthDate = getDate();
        errors = (birthDate == null) || errors;

        if(!errors){
            //TODO Validar gender
            User user = new User(name, lastName, birthDate, Long.valueOf(phone), 1);
            Intent intent = new Intent(this, Register2Activity.class);
            intent.putExtra(Constants.EXTRA_USER, user);
            startActivityForResult(intent, Constants.REQ_CODE_REG2);
        }
    }

    private Date getDate() {
        Date date = null;
        String text = inputBirthdate.getText().toString();
        if(text.length() > 0 ){
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.US);
            try {
                date = format.parse(text);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQ_CODE_REG2) {
            if (resultCode == RESULT_OK) {
                User user = (User) data.getSerializableExtra(Constants.EXTRA_USER);
                Call<User> call = userClient.createUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(Register1Activity.this, LoginActivity.class);
                            intent.putExtra(Constants.EXTRA_USERNAME, response.body().getUsername());
                            startActivity(intent);
                            finish();
                        }else{
                            //TODO validar error
                            Log.d(LOG_TAG, response.toString());
                            Toast.makeText(Register1Activity.this, "Error creando el usuario" + response.toString() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d(LOG_TAG, "Error", t);
                        Toast.makeText(Register1Activity.this, getString(R.string.conection_error), Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        }
    }

    private String getSpinnerValue(Spinner spinner){
        String text = spinner.getSelectedItem().toString();
        if(text.length() == 0) {
            //TODO generar error
            Toast.makeText(this, "Por favor seleccione un genero", Toast.LENGTH_SHORT).show();
            text = null;
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


}
