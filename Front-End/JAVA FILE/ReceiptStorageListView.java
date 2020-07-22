package com.example.chua.gogreen;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReceiptStorageListView extends AppCompatActivity {

    private static final String TAG = "ReceiptStorageListView";
    private String result,name,rnur="read",colour,categories;
    private JSONArray jArray;
    private ArrayList<ReceiptStorageData> ReceiptList;
    private JSONObject obj;
    private RelativeLayout rl;
    private ImageView iv;
    private Bundle b;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_storage_list_view);

        Intent data = getIntent();
        b = getIntent().getExtras();
        categories = b.getString("categories");
        name = b.getString("name");
        rl=(RelativeLayout)findViewById(R.id.layout);
        colour=CategoriesLayout.getColor(categories);
        iv=(ImageView)findViewById(R.id.imageView);
        String image = CategoriesLayout.getImage(categories);

        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("mipmap/"+image,null,c.getPackageName());
        iv.setImageResource(id);
        int xx = c.getResources().getIdentifier("color/"+colour,null,c.getPackageName());
        iv.setColorFilter(ContextCompat.getColor(this, xx), android.graphics.PorterDuff.Mode.SRC_IN);

        ListView mListView = (ListView) findViewById(R.id.receiptStorageListView);
        ReceiptStorageListView.getDataThread lt = new ReceiptStorageListView.getDataThread(name,categories,rnur,handler);
        lt.start();
        ReceiptList = new ArrayList<>();
        ReceiptStorageListAdapter adapter = new ReceiptStorageListAdapter(this, R.layout.receipt_storage_list_adapter, ReceiptList);
        mListView.setAdapter(adapter);


    }
    private class getDataThread extends Thread {
        String username, categories,rnur;
        Handler getDatahandler;

        public getDataThread(String name, String categories, String rnur,Handler handler) {
            this.rnur = rnur;
            this.username = name;
            this.categories = categories;
            getDatahandler = handler;
        }
        public void run() {
            try {
                String loginURL = URLClass.getURL()+"displayReceiptWithCategories.php?username=" + username +"&categories="+categories+"&rnur="+rnur;
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
                                String y = obj.getString("senderID");
                                String z = obj.getString("Date");
                                ReceiptStorageData xx = new ReceiptStorageData(x,z,y);
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
