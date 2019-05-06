package com.ynov.apprecipe.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ynov.apprecipe.R;
import com.ynov.apprecipe.managers.DataManager;
import com.ynov.apprecipe.model.IngredientRecipe;
import com.ynov.apprecipe.model.NutritionRecipe;
import com.ynov.apprecipe.model.Recipe;
import com.ynov.apprecipe.model.StepRecipe;
import com.ynov.apprecipe.model.TimeRecipe;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipeItem extends AppCompatActivity {

    public static String RECIPE_ID = "recipe_id";

    private ArrayList<Recipe> mListRecipe;
    private ArrayList<IngredientRecipe> ingredientRecipes;
    private ArrayList<StepRecipe> stepRecipes;
    private ArrayList<NutritionRecipe> nutritionRecipes;
    private ArrayList<TimeRecipe> timeRecipes;


    public ArrayList arrayListStep;
    public ArrayList ArrayListIngr;

    private TextView textViewTitle;
    private TextView textViewPortions;
    private ImageView imageViewPicture;


    private TextView tvUnit;
    private TextView tvName;
    private TextView tvQuantity;


    private TextView tvKcal;
    private TextView tvProtein;
    private TextView tvFat;
    private TextView tvCarbo;
    private TextView tvSugar;
    private TextView tvSatFat;
    private TextView tvFiber;
    private TextView tvSodium;
    private TextView tvTotal;
    private TextView tvPrep;
    private TextView tvBacking;

    private Recipe mCurrentRecipe;

    public static Intent newIntent(Context cxt, String recipeId) {
        Intent i = new Intent(cxt, RecipeItem.class);
        i.putExtra(RECIPE_ID, recipeId);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_item_click);
        textViewTitle = findViewById(R.id.title1);
        textViewPortions = findViewById(R.id.portions1);
        imageViewPicture = findViewById(R.id.picture1);

        tvUnit = findViewById(R.id.ing_unit);
        tvName = findViewById(R.id.ing_name);
        tvQuantity = findViewById(R.id.ing_quantity);

        tvSugar = findViewById(R.id.nut_sugar_value);
        tvSodium = findViewById(R.id.nut_sodium_value);
        tvSatFat = findViewById(R.id.nut_sat_fat_value);
        tvProtein = findViewById(R.id.nut_protein_value);
        tvFat = findViewById(R.id.nut_fat_value);
        tvFiber = findViewById(R.id.nut_fiber_value);
        tvCarbo = findViewById(R.id.nut_carbo_value);
        tvKcal = findViewById(R.id.nut_kcal_value);

        tvBacking = findViewById(R.id.time_backing);
        tvPrep = findViewById(R.id.time_prep);
        tvTotal = findViewById(R.id.time_total);

        getIncomingIntent();

    }


    private void getIncomingIntent() {
        Log.d("tt", "getIncomingIntent: ");
        Intent currentIntent = getIntent();
        String recipeId = currentIntent.getStringExtra(RECIPE_ID);
        if (recipeId != null) {
            mCurrentRecipe = DataManager.getInstance().getRecipeById(recipeId);
            if (mCurrentRecipe != null) {
                //  setup view
                String title = mCurrentRecipe.getTitle();
                String portions = mCurrentRecipe.getPortions();
                String picture = mCurrentRecipe.getPicture_url();
                Picasso.get().load(picture).fit().centerInside().into(imageViewPicture);
                textViewTitle.setText(title);
                textViewPortions.setText(portions + " personnes");

                ingredientRecipes = mCurrentRecipe.getIngredientRecipes();
                stepRecipes = mCurrentRecipe.getSteps();
                nutritionRecipes = mCurrentRecipe.getNutritionRecipes();
                timeRecipes = mCurrentRecipe.getTimeRecipes();

                ArrayList arrayListIngr = new ArrayList();
                for (int ingrFound = 0; ingrFound < ingredientRecipes.size(); ingrFound++) {
                    IngredientRecipe myIngredients = ingredientRecipes.get(ingrFound);

                    String name = myIngredients.getName();
                    String quantity = myIngredients.getQuantity();
                    String unit = myIngredients.getUnit();
                    IngredientRecipe ingredientRecipe = new IngredientRecipe();
                    ingredientRecipe.setName(name);
                    ingredientRecipe.setUnit(unit);
                    ingredientRecipe.setQuantity(quantity);

                    arrayListIngr.add(ingredientRecipe);


                    for (int i = 0; i < arrayListIngr.size(); i++) {
                        IngredientRecipe ingre = (IngredientRecipe) arrayListIngr.get(i);

                        String names = ingre.getName();
                        String quantitys = ingre.getQuantity();
                        String units = ingre.getUnit();

                        Log.i("INGR", "" + names + quantitys + units);
                    }

                    Log.d("steps", String.valueOf(ingrFound));
                    /*System.out.println("Élément à l'index " + ingrFound + " = " + stepRecipes.get(ingrFound));*/
                }


                final ListView myLists = (ListView) findViewById(R.id.ing_list);
                final ArrayList<IngredientRecipe> ingrMy = arrayListIngr;
                final IngredientRecipe OneIngr = new IngredientRecipe();

                final ArrayAdapter mAdapter = new AdapterIngredient(this, ingrMy);
                myLists.setAdapter(mAdapter);


                ArrayList arrayListStep = new ArrayList();
                for (int stepFound = 0; stepFound < stepRecipes.size(); stepFound++) {
                    StepRecipe myStep = stepRecipes.get(stepFound);

                    String order = myStep.getOrder();
                    String step = myStep.getStep();
                    StepRecipe stepRecipe = new StepRecipe();
                    stepRecipe.setOrder(order);
                    stepRecipe.setStep(step);

                    arrayListStep.add(stepRecipe);


                    for (int i = 0; i < arrayListStep.size(); i++) {
                        StepRecipe sstep = (StepRecipe) arrayListStep.get(i);

                        String orders = sstep.getOrder();
                        String steps = sstep.getStep();

                        /*if(sstep.getOrder() == "0"){

                         *//*if (tvOrder1.getText() != null){*//*
                            tvOrder1.setText(orders);
                            tvStep1.setText(steps);

                        }else if (sstep.getOrder() == "1"){
                            tvOrder2.setText(orders);
                            tvStep2.setText(steps);

                        }else if (sstep.getOrder() == "2"){
                            tvOrder3.setText(orders);
                            tvStep3.setText(steps);

                        }else if (sstep.getOrder() == "3"){
                            tvOrder4.setText(orders);
                            tvStep4.setText(steps);

                        }else if (sstep.getOrder() == "4"){
                            tvOrder5.setText(orders);
                            tvStep5.setText(steps);

                        }else {
                            break;
                        }*/

                        Log.i("INGR", "" + orders + steps);
                    }

                    Log.d("steps", String.valueOf(stepFound));
                    System.out.println("Élément à l'index " + stepFound + " = " + stepRecipes.get(stepFound));
                }

                final ListView myList = (ListView) findViewById(R.id.step_my_list);
                final ArrayList<StepRecipe> stepMy = arrayListStep;
                final StepRecipe OneStep = new StepRecipe();

                final ArrayAdapter adapter = new AdapterStep(this, stepMy);
                myList.setAdapter(adapter);


                for (int j = 0; j < nutritionRecipes.size(); j++) {
                    NutritionRecipe nutFound = nutritionRecipes.get(j);

                    String kcal = nutFound.getCalorie();
                    String carbo = nutFound.getCarbohydrate();
                    String fiber = nutFound.getFiber();
                    String fat = nutFound.getFat();
                    String protein = nutFound.getProtein();
                    String sat_fat = nutFound.getSat_fat();
                    String sodium = nutFound.getSodium();
                    String sugar = nutFound.getSugar();

                    tvKcal.setText(nutFound.getCalorie());
                    tvCarbo.setText(nutFound.getCarbohydrate());
                    tvFiber.setText(nutFound.getFiber());
                    tvFat.setText(nutFound.getFat());
                    tvProtein.setText(nutFound.getProtein());
                    tvSatFat.setText(nutFound.getSat_fat());
                    tvSodium.setText(nutFound.getSodium());
                    tvSugar.setText(nutFound.getSugar());

                    Log.d("ingredient", String.valueOf(j));
                    System.out.println("Élément à l'index " + j + " = " + ingredientRecipes.get(j));
                }

                for (int x = 0; x < timeRecipes.size(); x++) {
                    TimeRecipe timeFound = timeRecipes.get(x);

                    String total = timeFound.getTotal();
                    String prep = timeFound.getPrep();
                    String backing = timeFound.getBaking();

                    tvTotal.setText(timeFound.getTotal() + " min");
                    tvPrep.setText(timeFound.getPrep() + " min");
                    tvBacking.setText(timeFound.getBaking() + " min");

                    Log.d("ingredient", String.valueOf(x));
                    System.out.println("Élément à l'index " + x + " = " + ingredientRecipes.get(x));
                }
            }

        } else {
            //   show error message
        }
        mListRecipe = new ArrayList<>();
        mListRecipe = getIntent().getParcelableArrayListExtra("recipe");


    }

}


