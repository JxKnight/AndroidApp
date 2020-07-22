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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Edit extends AppCompatActivity {
    private Button buttonConfirm;
    private EditText firstName,lastName,password,email,contact,oldpassword;
    private String name;
    Handler searchhandler = new Handler();
    Handler updatehandler = new Handler();
    Handler hinthandler = new Handler();
    private String hintPassword="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent data = getIntent();
        name = data.getStringExtra("name");

        Edit.hintProfileThread spt = new Edit.hintProfileThread(name,hinthandler);
        spt.start();

        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        contact = (EditText)findViewById(R.id.contact);
        oldpassword = (EditText)findViewById(R.id.oldPassword);
        buttonConfirm = (Button)findViewById(R.id.confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit.searchProfileThread spt = new Edit.searchProfileThread(name,firstName.getText().toString(),lastName.getText().toString(),password.getText().toString(),email.getText().toString(),contact.getText().toString(),oldpassword.getText().toString(),searchhandler);
                spt.start();
            }
        });
    }
    private class searchProfileThread extends Thread {
        String username,firstname,lastname,password,email,contact,oldpassword;
        Handler shandler;

        public searchProfileThread(String name,String firstname, String lastname, String password, String email, String contact,String oldpassword, Handler handler) {
            this.username = name;
            this.firstname = firstname;
            this.lastname = lastname;
            this.password = password;
            this.email = email;
            this.contact = contact;
            this.oldpassword = oldpassword;
            shandler = handler;
        }
        public void run() {
            try {
                String searchURL = "http://192.168.0.100/mobileappassignment/searchprofile.php?username=" + username;
                URL url = new URL(searchURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result = br.readLine();
                JSONObject resultJson = new JSONObject(result);
                final String FirstName = resultJson.getString("firstname");
                final String LastName = resultJson.getString("lastname");
                final String Password = resultJson.getString("password");
                final String Email = resultJson.getString("email");
                final String Contact = resultJson.getString("contact");
                Log.i("oldpassword",oldpassword);
                Log.i("password",Password);
                if (oldpassword.equals(Password)) {
                    shandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (firstname.isEmpty()) { firstname = FirstName;}
                            if (lastname.isEmpty()) { lastname = LastName; }
                            if (password.isEmpty()) { password = Password; }
                            if (email.isEmpty()) { email = Email; }
                            if (contact.isEmpty()) { contact = Contact; }
                            Log.i("firstname", firstname);
                            Log.i("lastname", lastname);
                            Log.i("password", password);
                            Log.i("email", email);
                            Log.i("contact", contact);
                            Edit.updateProfileThread updatet = new Edit.updateProfileThread(username, firstname, lastname, password, email, contact, updatehandler);
                            updatet.start(); }});
                }else {
                    shandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Edit.this, "Your old password is wrong,edit profile fail.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }
    private class updateProfileThread extends Thread {
        String username,firstname,lastname,password,email,contact;
        Handler shandler;

        public updateProfileThread(String name,String firstname, String lastname, String password, String email, String contact, Handler handler){
            this.username = name;
            this.firstname = firstname;
            this.lastname = lastname;
            this.password = password;
            this.email = email;
            this.contact = contact;
            shandler = handler;
        }
        public void run() {
            try {
                String searchURL = URLClass.getURL()+"updateprofile.php?username=" + username+"&firstname="+firstname+"&lastname="+lastname+"&password="+password+"&email="+email+"&contact="+contact;
                URL url = new URL(searchURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());

                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result = br.readLine();
                if (result.equalsIgnoreCase("Valid")) {
                    shandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Edit.this, "Update Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Edit.this, Menu.class);
                            intent.putExtra("name",username);
                            startActivity(intent);
                        }
                    });
                }else if (result.equalsIgnoreCase("error")) {
                    shandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Edit.this, "Update Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }
    private class hintProfileThread extends Thread {
        String username;
        Handler hhandler;

        public hintProfileThread(String name, Handler handler) {
            this.username = name;
            hhandler = handler;
        }
        public void run() {
            try {
                String searchURL = "http://192.168.0.100/mobileappassignment/searchprofile.php?username=" + username;
                URL url = new URL(searchURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result = br.readLine();
                JSONObject resultJson = new JSONObject(result);
                final String FirstName = resultJson.getString("firstname");
                final String LastName = resultJson.getString("lastname");
                final String Password = resultJson.getString("password");
                final String Email = resultJson.getString("email");
                final String Contact = resultJson.getString("contact");
                for(int i=0;i<Password.length();i++) {
                    hintPassword="x"+hintPassword;
                }
                hhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        firstName = (EditText)findViewById(R.id.firstName);
                        lastName = (EditText)findViewById(R.id.lastName);
                        email = (EditText)findViewById(R.id.email);
                        contact = (EditText)findViewById(R.id.contact);
                        firstName.setHint(FirstName);
                        lastName.setHint(LastName);
                        password.setHint(hintPassword);
                        email.setHint(Email);
                        contact.setHint(Contact);
                    }
                });
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }
}
