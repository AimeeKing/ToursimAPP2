package com.example.aimee.bottombar.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aimee.bottombar.R;

import java.util.List;

/**
 * Created by Aimee on 2016/3/26.
 */
public class ListDropDownAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }


    public ListDropDownAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_drop_down_list,null);
        }
        TextView text= (TextView) convertView.findViewById(R.id.text);
        text.setText(list.get(position));
        if(checkItemPosition!=-1) {
            if (checkItemPosition == position) {
                text.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                text.setBackgroundResource(R.color.check_bg);
            }
            else
            {
                text.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                text.setBackgroundResource(R.color.white);
            }
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




}
