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
 * Created by Kayumov Abduaziz on 6/3/2015.
 */
public class AgendaListAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public AgendaListAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class DataHandler
    {
        ImageView pic;
        TextView time;
        TextView title;
        TextView description;
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
            row = inflater.inflate(R.layout.agenda_row_layout,parent,false);
            handler = new DataHandler();
            handler.pic = (ImageView)row.findViewById(R.id.imageView2);
            handler.time = (TextView) row.findViewById(R.id.textView4);
            handler.title = (TextView)row.findViewById(R.id.textView5);
            handler.description = (TextView)row.findViewById(R.id.textView6);
            row.setTag(handler);
        }
        else{
            handler = (DataHandler)row.getTag();
        }

        AgendaListProvider provider = (AgendaListProvider) this.getItem(position);
        handler.pic.setImageResource(provider.getPics());
        handler.time.setText(provider.getTime());
        handler.title.setText(provider.getTitle());
        handler.description.setText(provider.getDescription());

        return row;
    }
}
