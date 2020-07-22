package com.example.chua.gogreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;

public class ReceiptStorageCategories extends AppCompatActivity {
    private String name,colour;
    private RelativeLayout rl;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_receipt_storage);

        Intent data = getIntent();
        Bundle b = getIntent().getExtras();
        String categories = b.getString("categories");
        String name = b.getString("name");
        rl=(RelativeLayout)findViewById(R.id.layout);
        colour=CategoriesLayout.getColor(categories);
        iv=(ImageView)findViewById(R.id.imageView);
        String image = CategoriesLayout.getImage(categories);
        Toast.makeText(this, image, Toast.LENGTH_SHORT).show();
        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("mipmap/"+image,null,c.getPackageName());
        iv.setImageResource(id);
        int xx = c.getResources().getIdentifier("color/"+colour,null,c.getPackageName());
        iv.setColorFilter(ContextCompat.getColor(this, xx), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}
