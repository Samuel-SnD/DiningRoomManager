package com.example.diningroommanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.diningroommanager.mapping.Menu;

import java.util.ArrayList;

// Clase de adaptador para las filas de los menús
public class ListAdapter3 extends BaseAdapter {

    Context context;
    private ArrayList<Menu> menus;

    public ListAdapter3 (Context context, ArrayList <Menu> arrMenus){
        this.context = context;
        this.menus = arrMenus;
    }

    @Override
    public int getCount() {
        return menus.size();
    }
    @Override
    public Object getItem(int i) {
        return menus.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView platos;
        TextView bebidas;
    }

    // Creo un nuevo viewHolder y un inflater, inflo la fila y establezco los datos necesarios
    // en el viewHolder para establecerlo más tarde como tag
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListAdapter3.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ListAdapter3.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row3, parent, false);
            viewHolder.platos = convertView.findViewById(R.id.tvMenuRow2);
            viewHolder.bebidas = convertView.findViewById(R.id.tvmenuRow4);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ListAdapter3.ViewHolder) convertView.getTag();
        }

        viewHolder.platos.setText(String.valueOf(menus.get(position).getPlatos()));
        viewHolder.bebidas.setText(String.valueOf(menus.get(position).getBebidas()));

        return convertView;
    }
}
