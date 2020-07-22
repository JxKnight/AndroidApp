package com.example.chua.gogreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ReceiptStorage extends AppCompatActivity {

    String vCatName = "",name;
    RelativeLayout relay_Utilities,relay_Transportation,relay_Shopping,relay_Travel,relay_Health,relay_Insurances,relay_Education,relay_Others;
    private Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_storage);

        relay_Utilities = findViewById(R.id.relay_utilities);
        relay_Transportation = findViewById(R.id.relay_transport);
        relay_Shopping = findViewById(R.id.relay_Shopping);
        relay_Travel = findViewById(R.id.relay_travel);
        relay_Health = findViewById(R.id.relay_health);
        relay_Insurances = findViewById(R.id.relay_insurance);
        relay_Education = findViewById(R.id.relay_education);
        relay_Others = findViewById(R.id.relay_others);

        Intent data = getIntent();
        name = data.getStringExtra("name");
        b = new Bundle();
        b.putString("name",name);
        relay_Utilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vCatName = "Utilities";
                openStorage();
            }
        });

        relay_Transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vCatName = "Transportation";
                openStorage();
            }
        });
        relay_Shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vCatName = "Shopping";
                openStorage();
            }
        });
        relay_Travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vCatName = "Travel";
                openStorage();
            }
        });
        relay_Health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vCatName = "Health";
                openStorage();
            }
        });
        relay_Insurances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vCatName = "Insurances";
                openStorage();
            }
        });
        relay_Education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vCatName = "Education";
                openStorage();
            }
        });
        relay_Others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vCatName = "Others";
                openStorage();
            }
        });
    }
    public void openStorage(){

        Intent intent = new Intent(ReceiptStorage.this,ReceiptStorageListView.class);
        b.putString("categories",vCatName);
        intent.putExtras(b);
        startActivity(intent);
    }
}
