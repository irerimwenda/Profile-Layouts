package com.example.ireribrian.mysqlapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ireribrian.mysqlapp.R;
import com.example.ireribrian.mysqlapp.session.SessionManager;
import com.example.ireribrian.mysqlapp.ui.CvProfile;
import com.example.ireribrian.mysqlapp.ui.CvProfileTwo;
import com.example.ireribrian.mysqlapp.ui.SocialMediaOne;
import com.example.ireribrian.mysqlapp.ui.SocialMediaTwo;

public class Layouts extends AppCompatActivity {
    SessionManager sessionManager;

    String [] Layouts = {"CV Profile Layout", "Social Media Sample 1", "Social Media Sample 2", "Professional CV Layout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Layouts");

        sessionManager = new SessionManager(this);

        ListView profile_list_view = (ListView) findViewById(R.id.layout_list);
        profile_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    final Intent cv_profile_intent = new Intent(Layouts.this, CvProfile.class);
                    startActivity(cv_profile_intent);
                }else
                    if(position == 1){
                    final Intent social_mediaone_profile_intent = new Intent(Layouts.this, SocialMediaOne.class);
                    startActivity(social_mediaone_profile_intent);
                    }else
                    if(position == 2){
                        final Intent social_mediatwo_profile_intent = new Intent(Layouts.this, SocialMediaTwo.class);
                        startActivity(social_mediatwo_profile_intent);
                    }else
                    if(position == 3){
                        final Intent cv_profile_two_intent = new Intent(Layouts.this, CvProfileTwo.class);
                        startActivity(cv_profile_two_intent);
                    }

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
            final Intent updateIntent = new Intent(Layouts.this, UpdateProfile.class);
            startActivity(updateIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
