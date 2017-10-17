package com.sam.teamd.samandroidclient.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.Mail;

import java.util.ArrayList;

/**
 * Created by yessica on 16/10/17.
 */

public class MailAdapter extends BaseAdapter {

    private static final String TAG = "CustomAdapter";
    private static int convertViewCounter = 0;

    private ArrayList<Mail> data;
    private LayoutInflater inflater = null;

    static class ViewHolder
    {
        TextView sender;
        TextView subject;
        TextView content;
        TextView date;
    }

    public MailAdapter(Context c, ArrayList<Mail> d)
    {
        Log.v(TAG, "Constructing CustomAdapter");
        this.data = d;
        inflater = LayoutInflater.from(c);
    }


    @Override
    public int getCount() {
        Log.v(TAG, "in getCount()");
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        Log.v(TAG, "in getItem() for position " + position);
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.v(TAG, "in getItemId() for position " + position);
        return position;
    }

    @Override
    public int getViewTypeCount()
    {
        Log.v(TAG, "in getViewTypeCount()");
        return 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        Log.v(TAG, "in getItemViewType() for position " + position);
        return 0;
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }

    ////

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;

        Log.v(TAG, "in getView for position " + position + ", convertView is "
                + ((convertView == null) ? "null" : "being recycled"));

        if (convertView == null)
        {
            //convertView = inflater.inflate(R.layout.list_row_posts, null);

            convertViewCounter++;
            Log.v(TAG, convertViewCounter + " convertViews have been created");

            holder = new ViewHolder();

            holder.sender = (TextView) convertView
                    .findViewById(R.id.sender);
            holder.subject = (TextView) convertView
                    .findViewById(R.id.subject);
            holder.content = (TextView) convertView
                    .findViewById(R.id.content);
            holder.date = (TextView) convertView
                    .findViewById(R.id.date);
            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.sender.setText(data.get(position).getSender());
        holder.subject.setText(data.get(position).getSubject());
        holder.content.setText(data.get(position).getMessageBody());
        //holder.date.setText(data.get(position).getDate());


        return convertView;
    }
}
