package com.example.ireribrian.mysqlapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ireribrian.mysqlapp.R;
import com.example.ireribrian.mysqlapp.session.SessionManager;

import java.util.HashMap;

public class Home extends AppCompatActivity {

    TextView name, email;
    Button start_button;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Layouts");

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        start_button = findViewById(R.id.button_start);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String user_name = user.get(sessionManager.NAME);
        String user_email = user.get(sessionManager.EMAIL);

        name.setText(user_name);
        email.setText(user_email);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent layoutIntent = new Intent(Home.this, Layouts.class);
                startActivity(layoutIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(item.getItemId() == R.id.logout){
            sessionManager.logout();
        }else
            if( id == R.id.update_profile){
                final Intent updateIntent = new Intent(Home.this, UpdateProfile.class);
                startActivity(updateIntent);
            }
        return super.onOptionsItemSelected(item);
    }
}
