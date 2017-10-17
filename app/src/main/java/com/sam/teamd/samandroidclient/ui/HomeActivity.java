package com.sam.teamd.samandroidclient.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.SearchView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.Mail;
import com.sam.teamd.samandroidclient.model.User;
import com.sam.teamd.samandroidclient.service.Api;
import com.sam.teamd.samandroidclient.service.MailClient;
import com.sam.teamd.samandroidclient.util.Constants;
import com.sam.teamd.samandroidclient.util.FontManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static int MAIL_INBOX = 1;
    public final static int MAIL_SENT = 2;
    public final static int MAIL_DRAFT = 3;

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    private MailClient mailClient;
    private  User user;
    private int currentType;
    private List<Mail> mails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mailClient = Api.getInstance(getApplicationContext()).getMailClient();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(Constants.EXTRA_USER);

        currentType = MAIL_INBOX;
        loadEmails("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMail();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);
    }

    private void createMail() {
        Intent intent = new Intent(this, SendMailActivity.class);
        intent.putExtra(Constants.EXTRA_USER, user);
        startActivityForResult(intent, Constants.REQ_CODE_SENDMAIL);
    }

    private Map<String, String> queryOptions(String query){
        Map<String, String> options = new HashMap<>();
        switch (query){
            case Constants.QUERY_PARAMS_URGENT:
                options.put(Constants.QUERY_PARAMS_URGENT, String.valueOf(true));
                break;
            case Constants.QUERY_PARAMS_READ:
                options.put(Constants.QUERY_PARAMS_READ, String.valueOf(true));
                break;
        }
        return options;
    }


    private void loadEmails(final String options){
        Map<String, String> queryOptions = queryOptions(options);
        String token = loadToken();
        Call<List<Mail>> call;
        switch (currentType){
            case MAIL_INBOX:
                call = mailClient.getInbox(token, queryOptions);
                break;
            case MAIL_SENT:
                call = mailClient.getSent(token, queryOptions);
                break;
            case MAIL_DRAFT:
                call = mailClient.getDraft(token, queryOptions);
                break;
            default:
                call = mailClient.getInbox(token, queryOptions);
                break;
        }

        call.enqueue(new Callback<List<Mail>>() {
            @Override
            public void onResponse(Call<List<Mail>> call, Response<List<Mail>> response) {
                Log.d(LOG_TAG, response.toString());
                if(response.isSuccessful()){
                    mails = response.body();
                    Log.d(LOG_TAG, String.valueOf(mails.size()));
                    for(Mail m : mails){
                        Log.d(LOG_TAG, "Mail:" + m.getSubject());
                    }
                }else if(response.code() == 401){
                    loadEmails(options);
                }
                else{
                    Toast.makeText(HomeActivity.this, "Error al cargar correos" + response.toString() , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Mail>> call, Throwable t) {
                Log.d(LOG_TAG, "Error enviando correo", t);
                Toast.makeText(HomeActivity.this, getString(R.string.conection_error), Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQ_CODE_SENDMAIL:
                if (resultCode == Constants.RESULT_OK) {
                    Toast.makeText(this, getString(R.string.mail_sended_toast), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_home_settings,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO Poner lo que va a hacer la búsqueda
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            // Handle the camera action
        } else if (id == R.id.nav_sent) {

        } else if (id == R.id.nav_draft) {

        } else if (id == R.id.nav_important) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private String loadToken() {
        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPref.getString(Constants.SHARED_PREF_TOKEN, null);
        return token;
    }
}
