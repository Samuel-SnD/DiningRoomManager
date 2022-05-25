package com.example.diningroommanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.diningroommanager.mapping.Mesa;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context context;
    private ArrayList <Mesa> mesas;

    public ListAdapter (Context context, ArrayList <Mesa> arrmesas){
        this.context = context;
        this.mesas = arrmesas;
    }

    @Override
    public int getCount() {
        return mesas.size();
    }
    @Override
    public Object getItem(int i) {
        return mesas.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView id;
        TextView asientos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;
        // Instancio todos los campos
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.id = convertView.findViewById(R.id.tvId);
            viewHolder.asientos = convertView.findViewById(R.id.tvAsientos);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(String.valueOf(mesas.get(position).getId()));
        viewHolder.asientos.setText(String.valueOf(mesas.get(position).getAsientos()));

        return convertView;
    }

}
