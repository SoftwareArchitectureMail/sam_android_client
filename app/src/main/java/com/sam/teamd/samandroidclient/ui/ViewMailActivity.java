package com.sam.teamd.samandroidclient.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.Mail;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.service.Api;
import com.sam.teamd.samandroidclient.service.MailClient;
import com.sam.teamd.samandroidclient.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMailActivity extends AppCompatActivity {

    private static final String LOG_TAG = ViewMailActivity.class.getSimpleName();

    Mail mail;
    private Button btnDelete;
    private MailClient mailClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mail);
        mailClient = Api.getInstance(getApplicationContext()).getMailClient();

        Intent intent = getIntent();
        mail = (Mail) intent.getSerializableExtra(Constants.EXTRA_MAIL);

        fillFields();
    }

    private void fillFields(){
        setText((TextView) findViewById(R.id.txt_view_mail_message_subject), mail.getSubject());
        setText((TextView) findViewById(R.id.txt_view_mail_message_sender), mail.getSender());
        setText((TextView) findViewById(R.id.txt_view_mail_message_recipient), mail.getRecipient());
        String date = String.valueOf(mail.getSentDate() != null ? mail.getSentDate() : "");
        setText((TextView) findViewById(R.id.txt_view_mail_message_sent_date), date);
        //setText((TextView) findViewById(R.id.txt_view_mail_message_body), mail.getMessageBody());

        // get our html content
        //String htmlAsString = getString(R.string.html);      // used by WebView
        String htmlAsString = mail.getMessageBody();

        WebView webView = (WebView) findViewById(R.id.txt_view_mail_message_body);
        webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);

        btnDelete = (Button) findViewById(R.id.btn_login_enter);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMail();
            }
        });
    }

    private void setText(TextView view, String string){
        view.setText(string);
    }

    private void deleteMail() {
        String token = loadToken();
        Call<Mail> call = mailClient.delSingleMail(token, mail.getId());
        call.enqueue(new Callback<Mail>() {
            @Override
            public void onResponse(Call<Mail> call, Response<Mail> response) {
                Log.d(LOG_TAG, response.toString());
                if(response.isSuccessful()){
                    Intent intent = new Intent(ViewMailActivity.this, HomeActivity.class);
                    intent.putExtra(Constants.EXTRA_USER, response.body());
                    startActivity(intent);
                    finish();
                }else if(response.code() == 401){
                    deleteMail();
                }else{
                    Toast.makeText(ViewMailActivity.this, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Mail> call, Throwable t) {
                //TODO Validar error!
                Log.e(LOG_TAG, "Error en login", t);
                Toast.makeText(ViewMailActivity.this, getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String loadToken(){
        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPref.getString(Constants.SHARED_PREF_TOKEN, null);
        return token;
    }
}
