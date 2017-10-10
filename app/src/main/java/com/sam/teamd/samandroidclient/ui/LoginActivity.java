package com.sam.teamd.samandroidclient.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.Login;
import com.sam.teamd.samandroidclient.model.Token;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.service.Api;
import com.sam.teamd.samandroidclient.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private Button btnLogin;
    private EditText inputUsername, inputPassword;
    private UserClient userClient = Api.getInstance().getUserClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = (EditText) findViewById(R.id.input_login_username);
        inputPassword= (EditText) findViewById(R.id.input_login_password);

        btnLogin = (Button) findViewById(R.id.btn_login_enter);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }



    private void login (){
        if (checkFields()){
            Login login = new Login(inputUsername.getText().toString(), inputPassword.getText().toString());
            Call<User> call = userClient.login(login);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d(LOG_TAG, response.toString());
                    if(response.isSuccessful()){
                        saveUserToken(response.body().getToken());
                        loadToken();
                        Toast.makeText(LoginActivity.this, "Login Exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    //TODO Validar error!
                    Log.e(LOG_TAG, "Error en login", t);
                    Toast.makeText(LoginActivity.this, getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean checkFields(){
        boolean errors = true;

        if(inputUsername.getText().length() == 0) {
            inputUsername.setError(getString(R.string.empty_fielf_error));
            errors = false;
        }

        if(inputPassword.getText().length() == 0) {
            inputPassword.setError(getString(R.string.empty_fielf_error));
            errors = false;
        }

        return errors;
    }

    private void saveUserToken(Token token){
        Log.d(LOG_TAG, token.getToken() + "   " + token.getRefresh());
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_preferences_name),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.shared_token), token.getToken());
        editor.putString(getString(R.string.shared_refresh_token), token.getRefresh());
        editor.apply();
    }

    private void loadToken() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.shared_token), null);
        String refresh = sharedPref.getString(getString(R.string.shared_refresh_token), null);
        Log.d(LOG_TAG, token + "   " + refresh);
    }
}
