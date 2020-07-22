package com.example.chua.gogreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReceiptInbox extends AppCompatActivity {

    private static final String TAG = "ReceiptInbox";
    private String result,name,rnur;
    private JSONArray jArray;
    private ArrayList<ReceiptInboxData> ReceiptList;
    private JSONObject obj;
    private Button backMenu;

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_inbox);

        ListView mListView = (ListView) findViewById(R.id.receiptListView);

        Intent data = getIntent();
        name = data.getStringExtra("name");
        rnur="unread";
        ReceiptList = new ArrayList<>();
        ReceiptInbox.getDataThread lt = new getDataThread(name,rnur,handler);
        lt.start();

        ReceiptInboxListAdapter adapter = new ReceiptInboxListAdapter(this, R.layout.receipt_inbox_list_adapter, ReceiptList);
        mListView.setAdapter(adapter);

        backMenu=(Button)findViewById(R.id.buttonBack);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReceiptInbox.this,Menu.class);
                startActivity(i);
            }
        });
    }

    private class getDataThread extends Thread {
        String username, rnur;
        Handler getDatahandler;

        public getDataThread(String name, String rnur, Handler handler) {
            this.username = name;
            this.rnur = rnur;
            getDatahandler = handler;
        }
        public void run() {
            try {
                String loginURL = URLClass.getURL()+"displayReceiptUnread.php?username=" + username + "&rnur=" + rnur;
                URL url = new URL(loginURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());

                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();

                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                while((result = br.readLine())!=null){
                    sb.append(result+"\n");
                }
                String fs=(sb.toString().trim());
                jArray = new JSONArray(fs);
                getDatahandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<jArray.length();i++){
                            try { obj=jArray.getJSONObject(i);
                            String x = obj.getString("receiptID");
                            String y = obj.getString("senderID");
                            String z = obj.getString("Date");
                            ReceiptInboxData xx = new ReceiptInboxData(x,y,z);
                            ReceiptList.add(xx);
                            Log.i("ReciptList",ReceiptList.get(i).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }


}
