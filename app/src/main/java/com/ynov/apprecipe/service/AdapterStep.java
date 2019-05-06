package com.ynov.apprecipe.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ynov.apprecipe.R;
import com.ynov.apprecipe.model.IngredientRecipe;
import com.ynov.apprecipe.model.StepRecipe;

import java.util.ArrayList;

public class AdapterStep extends ArrayAdapter<StepRecipe> {

    private Context context;
    private ArrayList<StepRecipe> stepRecipeArrayList;

    public AdapterStep(Context context, ArrayList<StepRecipe> list) {
        super(context, 0, list);
        this.context = context;
        this.stepRecipeArrayList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.step_item_list, parent, false);
        }

        StepRecipe currentStep = stepRecipeArrayList.get(position);

        TextView name = row.findViewById(R.id.step_order);
        TextView quantity = row.findViewById(R.id.step_value);

        if (currentStep != null) {
            name.setText(currentStep.getStep());
            quantity.setText(currentStep.getOrder());
        }

        return row;
    }

}
