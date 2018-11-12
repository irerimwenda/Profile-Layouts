package com.example.ireribrian.mysqlapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ireribrian.mysqlapp.R;
import com.example.ireribrian.mysqlapp.session.SessionManager;
import com.example.ireribrian.mysqlapp.ui.CvProfile;
import com.example.ireribrian.mysqlapp.ui.CvProfileTwo;
import com.example.ireribrian.mysqlapp.ui.SocialMediaOne;
import com.example.ireribrian.mysqlapp.ui.SocialMediaTwo;

import de.hdodenhof.circleimageview.CircleImageView;

public class Layouts extends AppCompatActivity {

    SessionManager sessionManager;

    String [] Layouts = {"CV Profile Layout", "Social Media Sample 1", "Social Media Sample 2", "Professional CV Layout"};
    int [] Images = {R.drawable.login, R.drawable.register, R.drawable.splash, R.drawable.home};
    String [] Rating = {"9.7", "9.4", "9.8", "9.9"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Layouts");

        sessionManager = new SessionManager(this);

        ListView profile_list_view = (ListView) findViewById(R.id.layout_list);

        CustomAdapter customAdapter = new CustomAdapter();
        profile_list_view.setAdapter(customAdapter);

        profile_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    final Intent cv_profile_intent = new Intent(Layouts.this, CvProfile.class);
                    startActivity(cv_profile_intent);
                }else
                    if(i == 1){
                    final Intent social_mediaone_profile_intent = new Intent(Layouts.this, SocialMediaOne.class);
                    startActivity(social_mediaone_profile_intent);
                    }else
                    if(i == 2){
                        final Intent social_mediatwo_profile_intent = new Intent(Layouts.this, SocialMediaTwo.class);
                        startActivity(social_mediatwo_profile_intent);
                    }else
                    if(i == 3){
                        final Intent cv_profile_two_intent = new Intent(Layouts.this, CvProfileTwo.class);
                        startActivity(cv_profile_two_intent);
                    }

            }
        });
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Images.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayouts, null);

            CircleImageView icon = (CircleImageView) findViewById(R.id.profile_layout_icon);
            TextView name = (TextView) findViewById(R.id.profile_layout_name);
            TextView rating = (TextView) findViewById(R.id.profile_layout_rating);

            icon.setImageResource(Images[i]);
            name.setText(Layouts[i]);
            rating.setText(Rating[i]);

            return view;
        }
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
