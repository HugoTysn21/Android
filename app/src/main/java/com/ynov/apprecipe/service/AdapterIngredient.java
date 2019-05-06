package com.ynov.apprecipe.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ynov.apprecipe.R;
import com.ynov.apprecipe.model.IngredientRecipe;
import com.ynov.apprecipe.model.Recipe;
import com.ynov.apprecipe.model.Weight;

import java.util.ArrayList;

public class AdapterIngredient extends ArrayAdapter {


    private Context context;
    private ArrayList<IngredientRecipe> ingredientRecipeArrayList = new ArrayList<>();

    public AdapterIngredient(Context context, ArrayList<IngredientRecipe> list) {
        super(context, 0, list);
        this.context = context;
        this.ingredientRecipeArrayList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.ingredient_item_list, parent, false);
        }

        IngredientRecipe currentIngredient = ingredientRecipeArrayList.get(position);


        TextView name = row.findViewById(R.id.ing_name);
        TextView quantity = row.findViewById(R.id.ing_quantity);
        TextView unit = row.findViewById(R.id.ing_unit);

        if (currentIngredient != null) {
            name.setText(currentIngredient.getName());
            quantity.setText(currentIngredient.getQuantity());

            if (currentIngredient.getUnit().equals("sans unité")) {
                unit.setText("");
            } else {
                unit.setText(currentIngredient.getUnit());
            }


        }

        return row;
    }
}
