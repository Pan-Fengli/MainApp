package com.dalab.dalabapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dalab.dalabapp.R;
import com.dalab.dalabapp.constant.Global;

public class bindAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public bindAdapter(Context context)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    static class ViewHolderbind
    {
        public TextView inner_title, pressure;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //
        ViewHolderbind holder = null;
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.inner_list_item, null);
            holder = new ViewHolderbind();
            holder.inner_title = convertView.findViewById(R.id.inner_title);
            holder.pressure = convertView.findViewById(R.id.inner_Pressure);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolderbind) convertView.getTag();
        }
        if(position == 0)
        {
            holder.inner_title.setText("上肢包扎");
            String info="最佳包扎压力"+ Global.global.up_low_value +
                    "~" + Global.global.up_high_value +"N";
            holder.pressure.setText(info);
        }
        else if(position == 1)
        {
            holder.inner_title.setText("下肢包扎");
            String info="最佳包扎压力"+ Global.global.down_low_value +
                    "~" + Global.global.down_high_value +"N";
            holder.pressure.setText(info);
        }
        return convertView;
    }
}
