package com.example.diningroommanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.diningroommanager.mapping.Reserva;

import java.util.ArrayList;

public class ListAdapter2 extends BaseAdapter {

    Context context;
    private ArrayList<Reserva> reservas;

    public ListAdapter2 (Context context, ArrayList <Reserva> arrReservas){
        this.context = context;
        this.reservas = arrReservas;
    }

    @Override
    public int getCount() {
        return reservas.size();
    }
    @Override
    public Object getItem(int i) {
        return reservas.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView id;
        TextView day;
        TextView hour;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListAdapter2.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ListAdapter2.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row2, parent, false);
            viewHolder.id = convertView.findViewById(R.id.tvIdMesaRow2);
            viewHolder.day = convertView.findViewById(R.id.tvDiaRow2);
            viewHolder.hour = convertView.findViewById(R.id.tvHora2Row2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ListAdapter2.ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(String.valueOf(reservas.get(position).getId()));
        viewHolder.day.setText(String.valueOf(reservas.get(position).getFecha()));
        viewHolder.hour.setText(String.valueOf(reservas.get(position).getHora()));

        return convertView;
    }

}
