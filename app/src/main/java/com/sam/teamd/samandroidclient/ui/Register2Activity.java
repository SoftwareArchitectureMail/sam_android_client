package com.sam.teamd.samandroidclient.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.service.Api;
import com.sam.teamd.samandroidclient.service.UserClient;
import com.sam.teamd.samandroidclient.util.Constants;

import java.util.GregorianCalendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register2Activity extends AppCompatActivity {

    private static final String LOG_TAG = Register2Activity.class.getSimpleName();
    private UserClient userClient = Api.getInstance().getUserClient();

    private Button btnSend;
    private User user;
    private EditText inputUsername;
    private boolean validUsername = false;


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

        inputUsername = (EditText) findViewById(R.id.input_register_username);
        inputUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validateUsername();
            }
        });

    }

    private void validateUsername() {
        String username = inputUsername.getText().toString();
        if(username.length() <= 5){
            validUsername = false;
            inputUsername.setError(getString(R.string.username_short_error));
        }else{
            Call<ResponseBody> call = userClient.validateUsername(username);
            call.enqueue(new Callback<ResponseBody>(){

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        validUsername = false;
                        inputUsername.setError(getString(R.string.username_in_use_fielf_error));
                    }else{
                        validUsername = true;
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //TODO Validar error!
                    Log.e(LOG_TAG, "Error en login", t);
                    Toast.makeText(Register2Activity.this, getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void send(){
        boolean errors = false;
        String username, password, email, location;
        validateUsername();

        username = validateTextField(findViewById(R.id.input_register_username));
        errors = !validUsername || errors;

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
        EditText pass = (EditText)  findViewById(R.id.input_register_password);
        EditText passConf = (EditText)  findViewById(R.id.input_register_reppassword);

        if(pass.getText().toString().equals(passConf.getText().toString())){
            password = pass.getText().toString();
        }else{
            pass.setError(getString(R.string.password_doesnt_match_error));
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
