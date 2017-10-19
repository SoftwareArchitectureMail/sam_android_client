package com.sam.teamd.samandroidclient.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sam.teamd.samandroidclient.R;
import com.sam.teamd.samandroidclient.model.Mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yessica on 16/10/17.
 */

public class MailAdapter extends BaseAdapter {

    private static final String TAG = "CustomAdapter";
    private static int convertViewCounter = 0;

    private List<Mail> data;
    private LayoutInflater inflater = null;
    private Context context;
    private Typeface fontAwesomeFont;

    static class ViewHolder
    {
        TextView textView_sender;
        TextView textView_subject;
        TextView textView_content;
        TextView textView_date;
        TextView icon_urgent;
        TextView icon_send;
        TextView icon_attachment;
    }

    public MailAdapter(Context c, List<Mail> d)
    {
        Log.v(TAG, "Constructing CustomAdapter");
        fontAwesomeFont = Typeface.createFromAsset(c.getAssets(), "font/fontawesome-webfont.ttf");
        this.data = d;
        context = c;
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
            convertView = inflater.inflate(R.layout.list_content_home, null);

            convertViewCounter++;
            Log.v(TAG, convertViewCounter + " convertViews have been created");

            holder = new ViewHolder();

            holder.textView_sender = (TextView) convertView
                    .findViewById(R.id.textView_sender);
            holder.textView_subject = (TextView) convertView
                    .findViewById(R.id.textView_subject);
            holder.textView_content = (TextView) convertView
                    .findViewById(R.id.textView_content);
            holder.textView_date = (TextView) convertView
                    .findViewById(R.id.textView_date);
            holder.icon_urgent = (TextView) convertView
                    .findViewById(R.id.icon_urgent);
            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.textView_sender.setText(data.get(position).getSender());
        holder.textView_subject.setText(data.get(position).getSubject());
        holder.textView_content.setText(data.get(position).getMessageBody());

        if(data.get(position).isUrgent()){
            holder.icon_urgent.setText(context.getString(R.string.fa_exclamation_circle));
            holder.icon_urgent.setTypeface(fontAwesomeFont);
        }

        if(data.get(position).getSentDate() != null){
            holder.textView_date.setText(data.get(position).getSentDate().toString());
        }

        if(data.get(position).isRead()){
            holder.icon_send.setText(context.getString(R.string.fa_envelope_open));
            holder.icon_send.setTypeface(fontAwesomeFont);
        }else
            holder.icon_send.setText(context.getString(R.string.fa_envelope));
            holder.icon_send.setTypeface(fontAwesomeFont);

        if(data.get(position).isHasAttachment()){
            holder.icon_attachment.setText(context.getString(R.string.fa_paperclip));
            holder.icon_attachment.setTypeface(fontAwesomeFont);
        }



        return convertView;
    }
}
