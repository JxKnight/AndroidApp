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


public class ReceiptStorageListAdapter extends ArrayAdapter<ReceiptStorageData>{

    private static final String TAG = "ReceiptInboxListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private String ID;

    private static class ViewHolder {
        TextView ReceiptId;
        TextView SendDate;
        TextView SenderId;
    }

    public ReceiptStorageListAdapter(Context context, int resource, ArrayList<ReceiptStorageData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the ReceiptInboxData information
        String ReceiptId = getItem(position).getReceiptId();
        String SendDate = getItem(position).getSendDate();
        String SenderId = getItem(position).getSenderId();

        //Create the receipt object with the information
        final ReceiptStorageData receipt = new ReceiptStorageData(ReceiptId, SendDate, SenderId);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        final ReceiptStorageListAdapter.ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ReceiptStorageListAdapter.ViewHolder();
            holder.ReceiptId = (TextView) convertView.findViewById(R.id.ReceiptId);
            holder.SendDate = (TextView) convertView.findViewById(R.id.SendDate);
            holder.SenderId = (TextView) convertView.findViewById(R.id.SenderId);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ReceiptStorageListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.ReceiptId.setText(receipt.getReceiptId());
        holder.SendDate.setText(receipt.getSendDate());
        holder.SenderId.setText(receipt.getSenderId());

        return convertView;
    }
}
