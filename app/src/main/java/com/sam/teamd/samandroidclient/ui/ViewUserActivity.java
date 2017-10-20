package com.sam.teamd.samandroidclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.Mail;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.util.Constants;

public class ViewUserActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(Constants.EXTRA_MAIL);

        fillFields();
    }

    private void fillFields(){
        setText((TextView) findViewById(R.id.textView_user_full_name), user.getFullName());
        setText((TextView) findViewById(R.id.textView_user_email), user.getCurrentEmail());
        setText((TextView) findViewById(R.id.textView_user_mobile_phone), String.valueOf(user.getMobilePhone()));
        setText((TextView) findViewById(R.id.textView_user_birth_date), String.valueOf(user.getDateBirth()));
        setText((TextView) findViewById(R.id.textView_user_gender), String.valueOf(user.getGender()));
    }

    private void setText(TextView view, String string){
        view.setText(string);
    }
}
