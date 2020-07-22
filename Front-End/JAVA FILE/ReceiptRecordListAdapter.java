package com.example.chua.gogreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ReceiptRecordListAdapter extends ArrayAdapter<ReceiptRecordData> {

    private static final String TAG = "ReceiptInboxListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView ReceiptId;
        TextView ReceiverId;
        TextView Date;
    }

    /**
     * Default constructor for the ReceiptInboxListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ReceiptRecordListAdapter(Context context, int resource, ArrayList<ReceiptRecordData> objects) {
        super(context, resource,objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the ReceiptInboxData information
        String ReceiptId = getItem(position).getReceiptId();
        String ReceiverId = getItem(position).getReceiverId();
        String tvDateID = getItem(position).gettvDate();

        //Create the receipt object with the information
        final ReceiptRecordData receiptData = new ReceiptRecordData(ReceiptId,ReceiverId,tvDateID);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        final ReceiptRecordListAdapter.ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ReceiptRecordListAdapter.ViewHolder();
            holder.ReceiptId = (TextView) convertView.findViewById(R.id.ReceiptIdRecord);
            holder.ReceiverId = (TextView) convertView.findViewById(R.id.ReceiverIdRecord);
            holder.Date = (TextView) convertView.findViewById(R.id.DateRecord);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ReceiptRecordListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.ReceiptId.setText(receiptData.getReceiptId());
        holder.ReceiverId.setText(receiptData.getReceiverId());
        holder.Date.setText(receiptData.gettvDate());
        return convertView;
    }

}
