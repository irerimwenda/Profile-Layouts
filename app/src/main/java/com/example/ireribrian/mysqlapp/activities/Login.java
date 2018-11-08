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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    
    EditText email, password;
    Button login_button;
    TextView register_link;
    ProgressDialog progressDialog;

    SessionManager sessionManager;

    private static String URL_LOGIN = "http://192.168.242.1/VolleyApp/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login_button = findViewById(R.id.button_login);
        register_link = findViewById(R.id.register_link);

        progressDialog = new ProgressDialog(this);

        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent loginIntent = new Intent(Login.this, Register.class);
                startActivity(loginIntent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String loginemail = email.getText().toString().trim();
                final String loginpassword = password.getText().toString().trim();

                if(!loginemail.isEmpty() || !loginpassword.isEmpty()) {

                    LoginUser(loginemail, loginpassword);
                }
                else{
                    email.setError("Please enter email");
                    password.setError("Please enter password");
                }
            }
        });
    }

    private void LoginUser(final String email, final String password) {

            progressDialog.setMessage("Signing In...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("login");
                                
                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++){
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        
                                        String name = object.getString("name").trim();
                                        String email = object.getString("email").trim();
                                        String id = object.getString("id").trim();

                                        sessionManager.createSession(name, email,id);

                                        Toast.makeText(Login.this, "Success Login" + name, Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        final Intent homeIntent = new Intent(Login.this, Home.class);
                                        homeIntent.putExtra("name", name);
                                        homeIntent.putExtra("email", email);
                                        startActivity(homeIntent);
                                    }
                                }
                                        
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Login.this, "Login Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this, "Login Error "+ error.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }
}
