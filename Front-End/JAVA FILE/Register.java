package com.example.chua.gogreen;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register extends AppCompatActivity {
    private Button register;
    private EditText userName,firstName,lastName,password,email,contact;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText) findViewById(R.id.userName);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        contact = (EditText) findViewById(R.id.contact);

        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().toString().isEmpty() || firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || password.getText().toString().isEmpty() || email.getText().toString().isEmpty() || contact.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Any one of it cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Register.registerThread rt = new registerThread(userName.getText().toString(), firstName.getText().toString(),lastName.getText().toString(),password.getText().toString(),email.getText().toString(),contact.getText().toString(),handler);
                    rt.start();
                }
            }
        });
    }
    private class registerThread extends Thread {
        String username, firstname,lastname,password,email,contact;
        Handler loginhandler;

        public registerThread(String username, String firstName, String lastName, String pass,String email, String contact,Handler handler) {
            this.username = username;
            this.firstname = firstName;
            this.lastname = lastName;
            this.password = pass;
            this.email = email;
            this.contact = contact;
            loginhandler = handler;
        }
        public void run() {
            try {
                String registerURL = "http://192.168.0.100/mobileappassignment/register.php?username=" + username + "&firstname="+firstname+ "&lastname="+lastname+ "&password="+password+ "&email="+email+ "&contact="+contact;
                URL url = new URL(registerURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());

                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result = br.readLine();

                if(result.equalsIgnoreCase("Valid")) {
                    loginhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Register.this, "Register Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }else if(result.equalsIgnoreCase("Invalid")) {
                    loginhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Register.this, "Register Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }

        }
    }
}
