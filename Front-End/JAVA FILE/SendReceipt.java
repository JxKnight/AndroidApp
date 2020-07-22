package com.example.chua.gogreen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class SendReceipt extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SendReceipt";
    private EditText bid;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ImageView receiptPhoto;
    private Button buttonCamera,buttonGallery,buttonSend;
    private ImageButton buttonCircle;
    ArrayList<String> addArray = new ArrayList<>();
    EditText txt;
    ListView show;
    private final int REQUEST_IMAGE_CAPTURE = 1, REQUEST_IMAGE_GALLERY=2;
    private String base64String,checkresult,newRID,newtext,checkBIDresult,username,rnur="unread",newdate;
    Handler checkRIDHandler=new Handler();
    Handler checkBIDHandler=new Handler();
    Handler sendHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_receipt);

        Intent data = getIntent();
        username = data.getStringExtra("name");

        receiptPhoto = (ImageView)findViewById(R.id.receiptPhoto);
        buttonCamera = (Button)findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(this);
        buttonGallery = (Button)findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(this);
        buttonSend = (Button)findViewById(R.id.buttonSend);
        bid = (EditText)findViewById(R.id.buyerId);
        txt = (EditText)findViewById(R.id.txt);
        show = (ListView)findViewById(R.id.listview);
        buttonCircle =(ImageButton)findViewById(R.id.btnCircle);
        SendReceipt.checkRIDThread lt = new SendReceipt.checkRIDThread(checkRIDHandler);
        lt.start();

        buttonCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInput = txt.getText().toString();

                if(addArray.contains(getInput)){
                    Toast.makeText(getBaseContext(),"Item Already Added To Array", Toast.LENGTH_LONG).show();
                }
                else if(getInput == null || getInput.trim().equals("")){
                    Toast.makeText(getBaseContext(),"Input Field is Empty", Toast.LENGTH_LONG).show();
                }
                else{
                    addArray.add(getInput);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SendReceipt.this,android.R.layout.simple_list_item_1,addArray);
                    show.setAdapter(adapter);
                    ((EditText)findViewById(R.id.txt)).setText(" ");

                    for ( int i = 0; i < addArray.size(); i++) {
                        Log.d("value = ", addArray.get(i));
                    }
                    show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            AlertDialog.Builder adb=new AlertDialog.Builder(SendReceipt.this);
                            adb.setTitle("Delete?");
                            adb.setMessage("Are you sure you want to delete " + position);
                            final int positionToRemove = position;
                            adb.setNegativeButton("Cancel", null);
                            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    addArray.remove(positionToRemove);
                                    adapter.notifyDataSetChanged();
                                }});
                            adb.show();
                        }
                    });
                }
            }
        });


        mDisplayDate = (TextView) findViewById(R.id.tvDate);

        //String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");//formating according to my need
        String date = formatter.format(today);
        mDisplayDate.setText(date);
        newdate=mDisplayDate.getText().toString();

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SendReceipt.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String newmonth = Integer.toString(month);
                String newday = Integer.toString(day);
                String date = month + "/" + day + "/" + year;
                if(newmonth.length()==1)
                {
                    newmonth = "0"+newmonth;
                }
                if(newday.length()==1)
                {
                    newday = "0"+newday;
                }
                mDisplayDate.setText(date);
                newdate=mDisplayDate.getText().toString();
            }
        };

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<addArray.size();i++) {
                    if(newtext==null){
                    newtext = addArray.get(i);
                    } else {
                        newtext = newtext + "/" + addArray.get(i);
                    }
                }
                if(bid.getText().toString().isEmpty()) {
                    Toast.makeText(SendReceipt.this, "BuyerID cannot be empty", Toast.LENGTH_SHORT).show();
                }else if(bid.getText().toString().equals(username)){
                    Toast.makeText(SendReceipt.this, "Cannot send to ownself", Toast.LENGTH_SHORT).show();
                }else{
                    SendReceipt.checkBIDThread lt = new SendReceipt.checkBIDThread(checkBIDHandler);
                    lt.start();
                    SendReceipt.sendThread st = new SendReceipt.sendThread(username,newRID, bid.getText().toString(),newtext,newdate,rnur,sendHandler);
                    st.start();
                }
            }
        });
    }

    public void openSend(){
        Intent intent = new Intent(this,Menu.class);
        Toast.makeText(SendReceipt.this,"Send Success",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCamera:
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(iCamera.resolveActivity(getPackageManager())!= null){
                    startActivityForResult(iCamera,REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.buttonGallery:
                Intent iGallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                iGallery.setType("image/*");
                startActivityForResult(iGallery,REQUEST_IMAGE_GALLERY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode ==REQUEST_IMAGE_CAPTURE){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                receiptPhoto.setImageBitmap(bitmap);
                base64String = ImageUtil.convert(bitmap);

            }else if(requestCode == REQUEST_IMAGE_GALLERY){
                Uri uri =data.getData();
                Bitmap bitmap = null;
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    receiptPhoto.setImageBitmap(bitmap);
                    base64String = ImageUtil.convert(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    private class checkRIDThread extends Thread {
        Handler checkhandler;

        public checkRIDThread(Handler handler) {
            checkhandler = handler;
        }
        public void run() {
            try {
                String checkURL = URLClass.getURL()+"checkReceiptID.php";
                URL url = new URL(checkURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                checkresult = br.readLine();
                checkhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        newRID=checkresult;
                    }
                });
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }

    private class checkBIDThread extends Thread {
        String bid;
        Handler checkBIDhandler;

        public checkBIDThread(Handler handler) {
            this.bid=bid;
            checkBIDhandler = handler;
        }
        public void run() {
            try {
                String checkURL = URLClass.getURL()+"checkReceiverID.php?bid="+bid;
                URL url = new URL(checkURL.replaceAll(" ", "%20"));
                Log.i("url", url.toString());
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                checkBIDresult = br.readLine();
                if (checkBIDresult.equalsIgnoreCase("Valid")) {
                    checkBIDhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SendReceipt.this, "Valid Buyer ID", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (checkBIDresult.equalsIgnoreCase("NotValid")) {
                    checkBIDhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SendReceipt.this, "InValid Buyer ID", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }

    private class sendThread extends Thread {
        String username, text,date,bid,newrid,rnur,img;
        Handler sendhandler;
        public sendThread(String name,String newRID, String BID,String text,String date,String rnur,Handler handler) {
            this.username = name;
            this.newrid=newRID;
            this.bid = BID;
            this.date = date;;
            this.img = img;
            this.rnur=rnur;
            sendhandler = handler;
        }
        public void run() {
            try {
                String loginURL = URLClass.getURL()+"inputReceipt.php?receiptID="+newrid+"&senderID="+username+"&receiverID="+bid+"&sendDate="+date+"&sendText="+text+"&rnur="+rnur;
                URL url = new URL(loginURL.replaceAll(" ", "%20"));
                HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                hc.setRequestMethod("GET");
                hc.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                final String result = br.readLine();
                if (result.equalsIgnoreCase("Valid")) {
                    sendhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            openSend();
                        }
                    });
                }else if (result.equalsIgnoreCase("NotValid")) {
                    sendhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SendReceipt.this, "Send Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Thread", e.toString());
            }
        }
    }
}
