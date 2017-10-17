package com.sam.teamd.samandroidclient.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        setText((TextView) findViewById(R.id.txt_view_mail_message_cc), mail.getCc());
        setText((TextView) findViewById(R.id.txt_view_mail_message_sent_date), String.valueOf(mail.getSentDate()));
        setText((TextView) findViewById(R.id.txt_view_mail_message_body), mail.getMessageBody());

    }

    private void setText(TextView view, String string){
        view.setText(string);
    }
}
