package com.sam.teamd.samandroidclient.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.Token;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.service.Api;
import com.sam.teamd.samandroidclient.service.UserClient;
import com.sam.teamd.samandroidclient.util.Constants;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private UserClient userClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userClient = Api.getInstance(getApplicationContext()).getUserClient();

        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");

        // To use the same font in all the APP
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "font/SourceSansPro-Regular.ttf", true);

        loadToken();
    }

    private void loadToken(){

        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String token = sharedPref.getString(Constants.SHARED_PREF_TOKEN, null);
        String refresh = sharedPref.getString(Constants.SHARED_PREF_REF, null);
        if(token != null && refresh != null) {
            Token userToken = new Token(token, refresh);
            loadSession(userToken);
        }else{
            //TODO QUITAR!!!!
            Toast.makeText(MainActivity.this, "No se encontraron sesiones guardadas", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSession(final Token token){
        Call<User> call = userClient.getCurrentUser(token.getToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(LOG_TAG, String.valueOf(response.code()));
                if(response.isSuccessful()) {
                    User currentUser = response.body();
                    currentUser.setToken(token);
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra(Constants.EXTRA_USER, currentUser);
                    startActivity(intent);
                    finish();
                }
                else if(response.code() == 401) {
                    loadToken();
                }
                else{
                    Toast.makeText(MainActivity.this, getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(LOG_TAG, "ERROR REFRESCANDO TOKEN", t);
                Toast.makeText(MainActivity.this, getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, ViewMailActivity.class);
        startActivity(intent);
    }
}
