package com.ynov.apprecipe.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ynov.apprecipe.R;
import com.ynov.apprecipe.model.Measure;
import com.ynov.apprecipe.model.Weight;

import java.util.ArrayList;

public class AdapterMeasure extends ArrayAdapter<Measure> {

    private Context context;
    private ArrayList<Measure> measureArrayList = new ArrayList<>();

    public AdapterMeasure(Context context, ArrayList<Measure> measureArrayList) {
        super(context, 0, measureArrayList);
        this.context = context;
        this.measureArrayList = measureArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.measure_item_list, parent, false);
        }

        Measure currentMeasure = measureArrayList.get(position);

        TextView date = row.findViewById(R.id.measure_date_v);
        TextView value = row.findViewById(R.id.measure_value_v);
        TextView type = row.findViewById(R.id.measure_type_v);
        ImageButton imageButtonRemove = row.findViewById(R.id.button_Remove_Measure);
        /*final ListView myList = (ListView) row.findViewById(R.id.measure_list);*/

        if (currentMeasure != null) {
            date.setText(currentMeasure.getDate());
            value.setText(Float.toString(currentMeasure.getValue()));
            type.setText(currentMeasure.getType());
        }

        /*imageButtonRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                myList.removeViewAt(position); // remove the item from the data list
                notifyDataSetChanged();
            }
        });*/

        return row;
    }
}

