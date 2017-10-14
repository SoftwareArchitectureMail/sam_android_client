package com.sam.teamd.samandroidclient.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.util.Constants;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Register1Activity extends AppCompatActivity {

    private static final String LOG_TAG = Register1Activity.class.getSimpleName();

    private Button btnNext;
    private Spinner spinnerGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

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
        GregorianCalendar birthDate;

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQ_CODE_REG2) {
            if (resultCode == RESULT_OK) {
                User user = (User) data.getSerializableExtra(Constants.EXTRA_USER);
                Log.d(LOG_TAG, user.getFirstName());
                Log.d(LOG_TAG, user.getLastName());
                Log.d(LOG_TAG, user.getUsername());
                Log.d(LOG_TAG, String.valueOf(user.getMobilePhone()));
            }
        }
    }
    //TODO Eliminar cuando a steven se le de la gana de implementar el maldito date picker
    private GregorianCalendar getDate(){
        GregorianCalendar date = null;
        String year = validateTextField(findViewById(R.id.input_register_year));
        String month = validateTextField(findViewById(R.id.input_register_month));
        String day = validateTextField(findViewById(R.id.input_register_day));

        if(day != null && year != null && month != null){
            date = new GregorianCalendar(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
        }
        return date;
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

    private String validateTextField(View v){
        EditText editText = (EditText) v;
        String text = editText.getText().toString();
        if(text.length() == 0) {
            editText.setError(getString(R.string.empty_fielf_error));
            text = null;
        }
        return text;
    }



}
