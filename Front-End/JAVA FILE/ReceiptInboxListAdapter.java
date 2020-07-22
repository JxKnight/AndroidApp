package com.example.chua.gogreen;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReceiptInboxListAdapter extends ArrayAdapter<ReceiptInboxData>   {

    private static final String TAG = "ReceiptInboxListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private String ID;



    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView ReceiptId;
        TextView ReceiptDate;
        TextView SenderId;
    }

    /**
     * Default constructor for the
     * @param context
     * @param resource
     * @param objects
     */
    public ReceiptInboxListAdapter(Context context, int resource, ArrayList<ReceiptInboxData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the ReceiptInboxData information
        String ReceiptId = getItem(position).getreceiptID();
        String ReceiptDate = getItem(position).getsendDate();
        String SenderId = getItem(position).getsenderID();

        //Create the receipt object with the information
        final ReceiptInboxData receipt = new ReceiptInboxData(ReceiptId, ReceiptDate, SenderId);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        final ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.ReceiptId = (TextView) convertView.findViewById(R.id.ReceiptId);
            holder.ReceiptDate = (TextView) convertView.findViewById(R.id.SenderDate);
            holder.SenderId = (TextView) convertView.findViewById(R.id.SenderID);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.ReceiptId.setText(receipt.getreceiptID());
        holder.ReceiptDate.setText(receipt.getsendDate());
        holder.SenderId.setText(receipt.getsenderID());
        Button buttonAdd = (Button)convertView.findViewById(R.id.addCatogories);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = holder.ReceiptId.getText().toString();
                openAddCatogories();
                //Log.i("Text =", ID);

            }
        });

        return convertView;
    }

    public void openAddCatogories(){

        Intent i = new Intent(mContext, ReceiptInboxAddCatogories.class);
        i.putExtra("RID",ID);
        mContext.startActivity(i);
    }

}
