package com.hfad.databasedemo1;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Created by mine on 12/20/2016.
 */

public class CustomerAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Customer> selecteditems;

    public CustomerAdapter(Context context, int resource,List<Customer>data) {
        super(context, resource);
        this.selecteditems=data;
        this.context=context;

        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (view==null){
            view = inflater.inflate(R.layout.customer_row, parent, false);
        }

        TextView idText=(TextView) view.findViewById(R.id.customer_id);
        TextView nameText=(TextView)view.findViewById(R.id.customer_name);

        Customer c=(Customer) getItem(position);

        idText.setText(String.valueOf(c.getId()));
        nameText.setText(c.getName().toString());
        if (selecteditems.contains(c))
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        else
            view.setBackground(null);
        return view;
    }


}