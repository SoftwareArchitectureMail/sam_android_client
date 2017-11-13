package com.sam.teamd.samandroidclient.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Html;
import android.text.Spanned;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.Mail;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.util.Constants;

public class ViewMailActivity extends AppCompatActivity {

    Mail mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mail);

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

    }

    private void setText(TextView view, String string){
        view.setText(string);
    }
}
