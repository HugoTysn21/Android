package com.ynov.apprecipe.service;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import com.ynov.apprecipe.R;
        import com.ynov.apprecipe.model.Weight;

        import java.util.ArrayList;

public class AdapterWeight extends ArrayAdapter<Weight> {

    private Context context;
    private ArrayList<Weight> weightList = new ArrayList<>();

    public AdapterWeight(Context context, ArrayList<Weight> list) {
        super(context, 0, list);
        this.context = context;
        this.weightList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.weight_item_list, parent, false);
        }

        Weight currentWeight = weightList.get(position);

        TextView date = row.findViewById(R.id.weight_date_v);
        TextView value = row.findViewById(R.id.weight_value_v);

        if (currentWeight != null) {
            date.setText(currentWeight.getDate());
            value.setText(Float.toString(currentWeight.getValue()));
        }

        return row;
    }
}

