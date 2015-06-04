package com.example.kayumovabduaziz.eminemlyrics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayumov Abduaziz on 5/30/2015.
 */
public class MainListAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public MainListAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class DataHandler
    {
         ImageView pic;
         TextView title;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        DataHandler handler;
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_layout,parent,false);
            handler = new DataHandler();
            handler.pic = (ImageView)row.findViewById(R.id.mainListImage);
            handler.title = (TextView)row.findViewById(R.id.mainListItemName);
            row.setTag(handler);
        }
        else{
            handler = (DataHandler)row.getTag();
        }

        MainListProvider provider = (MainListProvider)this.getItem(position);
        handler.pic.setImageResource(provider.getPics());
        handler.title.setText(provider.getMain_list_title());

        return row;
    }
}
