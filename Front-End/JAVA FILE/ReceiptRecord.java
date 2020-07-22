package com.example.chua.gogreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReceiptRecord extends AppCompatActivity {

    private static final String TAG = "ReceiptRecord";
    private String result,name;
    private JSONArray jArray;
    private ArrayList<ReceiptRecordData> ReceiptList;
    private JSONObject obj;
    private Button backMenu;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_record);
        ReceiptList = new ArrayList<>();
        Intent data = getIntent();
        name = data.getStringExtra("name");

        ReceiptRecord.getDataThread lt = new ReceiptRecord.getDataThread(name,handler);
        lt.start();

        ListView mListView = (ListView) findViewById(R.id.receiptListView);
        ReceiptRecordListAdapter adapter = new ReceiptRecordListAdapter(this, R.layout.receipt_record_list_adapter, ReceiptList);
        mListView.setAdapter(adapter);

        backMenu=(Button)findViewById(R.id.buttonBack);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReceiptRecord.this,Menu.class);
                startActivity(i);
            }
        });

//        show.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                SparseBooleanArray positionchecker = show.getCheckedItemPositions();
//
//                int count = show.getCount();
//
//                for(int item = count-1; item>=0; item--){
//
//                    if(positionchecker.get(item)){
//                        adapter.remove(addArray.get(item));
//                    }
//
//                }
//
//                positionchecker.clear();
//
//                adapter.notifyDataSetChanged();
//
//
//                return false;
//            }
//        });
    }

    private class getDataThread extends Thread {
        String username;
        Handler getDatahandler;

        public getDataThread(String name,Handler handler) {
            this.username = name;
            getDatahandler = handler;
        }
        public void run() {
            try {
                String loginURL = URLClass.getURL()+"displayReceiptSend.php?username=" + username;
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
                            try {
                                obj=jArray.getJSONObject(i);
                                String x = obj.getString("receiptID");
                                String y = obj.getString("receiverID");
                                String z = obj.getString("Date");
                                ReceiptRecordData xx = new ReceiptRecordData(x,y,z);
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
