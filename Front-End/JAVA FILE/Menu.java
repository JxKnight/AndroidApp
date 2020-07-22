package com.example.chua.gogreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button buttonEdit,buttonSend,receiptInbox,buttonReceiptRecord,buttonReceiptStorage;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent data = getIntent();
        name = data.getStringExtra("name");

        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Edit.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        buttonSend = (Button)findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, SendReceipt.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        receiptInbox = (Button)findViewById(R.id.receiptInbox);
        receiptInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ReceiptInbox.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        buttonReceiptRecord = (Button)findViewById(R.id.ReceiptRecord);
        buttonReceiptRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ReceiptRecord.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        buttonReceiptStorage = (Button)findViewById(R.id.ReceiptStorage);
        buttonReceiptStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ReceiptStorage.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }
}
