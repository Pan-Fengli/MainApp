package com.dalab.dalabapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dalab.dalabapp.R;

public class hemostasisAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public hemostasisAdapter(Context context)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    static class ViewHolderHemostasis
    {
        public TextView title, content;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //
        ViewHolderHemostasis holder = null;
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.inner_list_item, null);
            holder = new ViewHolderHemostasis();
            holder.title = convertView.findViewById(R.id.inner_title);
            holder.content = convertView.findViewById(R.id.inner_content);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolderHemostasis) convertView.getTag();
        }
        if(position == 0)
        {
            holder.title.setText("左上肢止血");
            holder.content.setText("？");
        }
        else if(position == 1)
        {
            holder.title.setText("右上肢止血");
            holder.content.setText("？");
        }
        else if(position == 2)
        {
            holder.title.setText("左下肢止血");
            holder.content.setText("？");
        }
        else if(position == 3)
        {
            holder.title.setText("右下肢止血");
            holder.content.setText("？");
        }
        return convertView;
    }
}
