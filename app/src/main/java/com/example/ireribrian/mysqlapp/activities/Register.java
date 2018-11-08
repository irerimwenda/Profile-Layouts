package com.example.ireribrian.mysqlapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ireribrian.mysqlapp.R;
import com.example.ireribrian.mysqlapp.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText name, email, passord, confirm_password;
    Button register_button;
    TextView login_link;
    ProgressDialog progressDialog;

    SessionManager sessionManager;

    private static String URL_REGISTER = "http://192.168.242.1/VolleyApp/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        passord = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        register_button = findViewById(R.id.button_register);
        login_link = findViewById(R.id.login_link);

        sessionManager = new SessionManager(this);

        progressDialog = new ProgressDialog(this);

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent loginIntent = new Intent(Register.this, Login.class);
                startActivity(loginIntent);
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Register User
                RegisterUser();
            }
        });
    }

    public void RegisterUser()
    {
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.email.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");

                        if(success.equals("1")){

                            sessionManager.createSession(name, email, password);

                            Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            final Intent homeIntent = new Intent(Register.this, Home.class);
                            homeIntent.putExtra("name", name);
                            homeIntent.putExtra("email", email);
                            startActivity(homeIntent);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Register.this, "Registration Error!!" + e.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, "Registration Error!!" + error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
