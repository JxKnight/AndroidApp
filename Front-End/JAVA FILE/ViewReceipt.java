package com.example.chua.gogreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewReceipt extends AppCompatActivity {

    private String RID,senderID,receiverID,sendDate,sendText,photo;
    private JSONArray jArray;
    private ArrayList<ReceiptRecordData> ReceiptList;
    private JSONObject obj;
    private TextView Rid,SID,rId,Date,text;
    private ImageView Photo;
    private ArrayAdapter adapter;
    private ListView listView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt);
        Intent data = getIntent();
        RID = data.getStringExtra("RID");
        ReceiptList = new ArrayList<>();

        Rid = (TextView)findViewById(R.id.receiptIdText);
        SID = (TextView)findViewById(R.id.SenderIdText);
        rId = (TextView)findViewById(R.id.RecieverText);
        Date = (TextView)findViewById(R.id.dateText);
        Photo = (ImageView)findViewById(R.id.receiptImage);
        listView = (ListView)findViewById(R.id.listview);
        ViewReceipt.viewThread lt = new ViewReceipt.viewThread(RID,handler);
        lt.start();
        Rid.setText(RID);
        SID.setText(senderID);
        rId.setText(receiverID);
        Date.setText(sendDate);
        String [] separated = sendText.split(",");
        adapter = new ArrayAdapter<String>(this,R.layout.listview,separated);
        listView.setAdapter(adapter);

    }
    private class viewThread extends Thread {
        String rid;
        Handler viewhandler;

        public viewThread(String RID,Handler handler) {
            this.rid = RID;
            viewhandler = handler;
        }
        public void run() {
            try {
                String loginURL = URLClass.getURL()+"inputReceiptCategories.php?receiptID=" + rid;
                URL url = new URL(loginURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();

                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result = br.readLine();
                JSONObject resultJson = new JSONObject(result);
                senderID = resultJson.getString("senderID");
                receiverID = resultJson.getString("receiverID");
                sendDate = resultJson.getString("sendDate");
                sendText = resultJson.getString("sendText");
                photo = resultJson.getString("photo");
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }

}
