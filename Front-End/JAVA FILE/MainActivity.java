package com.example.chua.gogreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private Button buttonRegister, buttonLogin;
    private EditText username,password;
    String userName ="";
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.button);
        buttonRegister = (Button) findViewById(R.id.button2);
        userName = username.getText().toString();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
                MainActivity.loginThread lt = new loginThread(username.getText().toString(), password.getText().toString(),handler);
                lt.start();
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
    private class loginThread extends Thread {
        String username, password;
        Handler loginhandler;

        public loginThread(String name, String pass, Handler handler) {
            this.username = name;
            this.password = pass;
            loginhandler = handler;
        }

        public void run() {
            try {

                String data = URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8") + "&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                Log.i("POST_URL=",data);
                String loginURL = URLClass.getURL()+"login.php?username=" + username + "&password=" + password;
                URL url = new URL(loginURL.replaceAll(" ", "%20"));
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result = br.readLine();
                if (result.equalsIgnoreCase("Valid")) {
                    loginhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Menu.class);
                            intent.putExtra("name",username);
                            startActivity(intent);
                        }
                    });
                }else if (result.equalsIgnoreCase("NotValid")) {
                    loginhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }
}