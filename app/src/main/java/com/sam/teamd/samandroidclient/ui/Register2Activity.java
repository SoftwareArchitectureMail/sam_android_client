package com.sam.teamd.samandroidclient.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.util.Constants;

import java.util.GregorianCalendar;

public class Register2Activity extends AppCompatActivity {

    private Button btnSend;
    private  User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(Constants.EXTRA_USER);

        btnSend = (Button) findViewById(R.id.btn_register_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

    }

    public void send(){
        boolean errors = false;
        String username, password, email, location;
        GregorianCalendar birthDate;

        username = validateTextField(findViewById(R.id.input_register_username));
        errors = (username == null) || errors;

        email = validateTextField(findViewById(R.id.input_register_currentemail));
        errors = (email == null) || errors;

        password = validatePasswords();
        errors = (password == null) || errors;

        //TODO Implementar spinner para location
        location = validateTextField(findViewById(R.id.input_register_location));
        errors = (location == null) || errors;


        if(!errors){

            user.setUsername(username);
            user.setPassword(password);
            user.setCurrentEmail(email);

            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_USER, user);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private String validatePasswords() {
        String password = null;
        EditText pass, passConf;
        pass = (EditText)  findViewById(R.id.input_register_password);
        passConf = (EditText)  findViewById(R.id.input_register_reppassword);

        if(pass.getText().toString().equals(passConf.getText().toString())){
            password = pass.getText().toString();
        }

        return password;
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
