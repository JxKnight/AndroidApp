package com.example.chua.gogreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Demo extends AppCompatActivity {

    private Button buttonadd;
    ArrayList<String> addArray = new ArrayList<>();
    EditText txt;
    ListView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        txt = (EditText)findViewById(R.id.editText2);
        show = (ListView)findViewById(R.id.listview);
        buttonadd = (Button)findViewById(R.id.button5);

        buttonadd.setOnClickListener(new View.OnClickListener() {
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Demo.this,android.R.layout.simple_list_item_1,addArray);
                    show.setAdapter(adapter);
                    ((EditText)findViewById(R.id.editText2)).setText(" ");

                    for ( int i = 0; i < addArray.size(); i++)
                        Log.d("value = ", addArray.get(i));
                }
            }
        });


    }
}
