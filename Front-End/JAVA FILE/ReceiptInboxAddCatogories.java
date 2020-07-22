package com.example.chua.gogreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReceiptInboxAddCatogories extends AppCompatActivity {

    private Button addCatogoriesButton;
    private Spinner spinnerCatogories;
    private String text,RID;
    private String senderID,receiverID,sendDate,sendText,ImgString;
    private TextView Rid,Sid,Date;
    private ListView lv;
    private ArrayList<String> listtext = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ImageView img;
    private Bitmap decodeimg;
    private byte[] sendImg;
    Handler categorieshandler = new Handler();
    Handler gethandler = new Handler();
    Handler readhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_inbox_catogories);
        Intent data = getIntent();
        RID = data.getStringExtra("RID");
        Rid = (TextView)findViewById(R.id.receiptIdText);
        Sid = (TextView)findViewById(R.id.SenderIdText);
        Date =(TextView)findViewById(R.id.dateText);
        lv = (ListView)findViewById(R.id.listview);
        img = (ImageView)findViewById(R.id.receiptImage);

        Toast.makeText(this, RID, Toast.LENGTH_SHORT).show();
        ReceiptInboxAddCatogories.getDataThread st = new getDataThread(RID,gethandler);
        st.start();
        addCatogoriesButton = (Button)findViewById(R.id.buttonAddCatogories);
        spinnerCatogories = (Spinner)findViewById(R.id.spinner1) ;
        addCatogoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = spinnerCatogories.getSelectedItem().toString();
                ReceiptInboxAddCatogories.catogeriesThread lt = new catogeriesThread(RID,text,categorieshandler);
                lt.start();
                ReceiptInboxAddCatogories.readThread rt = new readThread(RID,text,readhandler);
                rt.start();
            }
        });



    }

    private class catogeriesThread extends Thread {
        String text,rid;
        Handler cathandler;

        public catogeriesThread(String RID,String text,Handler handler) {
            this.rid = RID;
            this.text=text;
            cathandler = handler;
        }
        public void run() {
            try {
                String loginURL = URLClass.getURL()+"inputReceiptCategories.php?receiptID=" + rid + "&catogeries=" + text;
                URL url = new URL(loginURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result=br.readLine();
                if (result.equalsIgnoreCase("Valid")) {
                    cathandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReceiptInboxAddCatogories.this, "Selecting Catogeries Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (result.equalsIgnoreCase("NotValid")) {
                    cathandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReceiptInboxAddCatogories.this, "Selecting Catogeries Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }

    private class readThread extends Thread {
        String text,rid;
        Handler cathandler;

        public readThread(String RID,String text,Handler handler) {
            this.rid = RID;
            this.text=text;
            cathandler = handler;
        }
        public void run() {
            try {
                String loginURL = URLClass.getURL()+"inputReceiptCategories.php?receiptID=" + rid + "&rnur=" + text;
                URL url = new URL(loginURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result=br.readLine();
                if (result.equalsIgnoreCase("Valid")) {
                    cathandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent it = new Intent(ReceiptInboxAddCatogories.this,Menu.class);
                            startActivity(it);
                        }
                    });
                }else if (result.equalsIgnoreCase("NotValid")) {
                    cathandler.post(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }
    private class getDataThread extends Thread {
        String rid;
        Handler getDatahandler;

        public getDataThread(String RID,Handler handler) {
            this.rid = RID;
            getDatahandler = handler;
        }
        public void run() {
            try {
                String loginURL = URLClass.getURL()+"displayReceipt.php?rID=" + rid;
                URL url = new URL(loginURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result=br.readLine();

                JSONObject resultJson = new JSONObject(result);
                senderID = resultJson.getString("senderID");
                receiverID = resultJson.getString("receiverID");
                sendDate = resultJson.getString("sendDate");
                sendText = resultJson.getString("sendText");
                ImgString= resultJson.getString("photo");
                getDatahandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendImg=ImgString.getBytes();
                        decodeimg= BitmapFactory.decodeByteArray(sendImg,0,sendImg.length);
                        Bitmap resizeimg = Bitmap.createScaledBitmap(decodeimg,100,100,false);
                        img.setImageBitmap(resizeimg);
                        Rid.setText(rid);
                        Sid.setText(senderID);
                        Date.setText(sendDate);
                        String[] separated = sendText.split("/");
                        for(int i = 0; i < separated.length;i++){
                            listtext.add(separated[i]);
                        }
//                      adapter = new ArrayAdapter<String>(ReceiptInboxAddCatogories.this,R.layout.listview,separated);
                        adapter = new ArrayAdapter<String>(ReceiptInboxAddCatogories.this,android.R.layout.simple_list_item_1,listtext);
                        lv.setAdapter(adapter);
                }
                });

            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }

}
