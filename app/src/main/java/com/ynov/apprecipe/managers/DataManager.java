package com.ynov.apprecipe.managers;

import android.os.AsyncTask;
import android.util.Log;

import com.ynov.apprecipe.interfaces.IAsyncTaskCallback;
import com.ynov.apprecipe.model.IngredientRecipe;
import com.ynov.apprecipe.model.NutritionRecipe;
import com.ynov.apprecipe.model.Recipe;
import com.ynov.apprecipe.model.StepRecipe;
import com.ynov.apprecipe.model.TimeRecipe;
import com.ynov.apprecipe.service.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class DataManager implements IAsyncTaskCallback<Recipe> {

    private static String url = "http://dev.audreybron.fr/flux/flux_recettes.json";

    private IAsyncTaskCallback mTaskDelegate;

    //SINGLETON (AntiPattern)
    //on definit que notre constructor est private car on ne peut avoir un et un seul dataManager dans l'app

    private DataManager() { }

    private static DataManager INSTANCE = new DataManager();

    private ArrayList<Recipe> mRecipes = new ArrayList<>();

    public static DataManager getInstance()
    {
        //  warning ! not thread safe !
        return INSTANCE;
    }

    public void LoadDataFromApi(IAsyncTaskCallback delegate) {
        mTaskDelegate = delegate;
        GetRecipes getRecipes = new GetRecipes();
        getRecipes.setCallback(this);
        getRecipes.execute();
    }

    public ArrayList<Recipe> getRecipes() {
        return mRecipes;
    }

    public Recipe getRecipeById(final String id) {
        Recipe recipeFound = null;
        for (Recipe r : mRecipes) {
            if (r.getId().equals(id)) {
                recipeFound = r;
                break;
            }
        }
        return recipeFound;
    }

    @Override
    public void onPreExecute() {
        //  nothing to do here
        mTaskDelegate.onPreExecute();
    }

    @Override
    public void onErrorOccured(Exception ex) {
        if (mTaskDelegate != null) {
            mTaskDelegate.onErrorOccured(ex);
            mTaskDelegate = null;
        }
    }

    @Override
    public void onPostExecute(ArrayList<Recipe> objects) {
        mRecipes = objects;
        mTaskDelegate.onPostExecute(objects);
        mTaskDelegate = null;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetRecipes extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        IAsyncTaskCallback callback;

        public void setCallback(IAsyncTaskCallback cb) {
            this.callback = cb;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onPreExecute();
        }

        @Override
        protected ArrayList<Recipe> doInBackground(Void... arg0) {

            ArrayList<Recipe> listRecipe = new ArrayList<>();
            ArrayList<StepRecipe> listStepRecipe = new ArrayList<>();
            ArrayList<IngredientRecipe> listIngredient = new ArrayList<>();
            ArrayList<NutritionRecipe> listNutritions = new ArrayList<>();
            ArrayList<TimeRecipe> listTimeRecipe = new ArrayList<>();

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            //Log.e("YNOV", "Response from url: " + jsonStr);

            //parse debuts
            if (jsonStr != null) {
                try {

                    // -> jsonObj est un tableau de données json String
                    // on va alors aller parcourir le tableau a l'aide de la boucle et inscrire les data au variable
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsarray = jsonObj.getJSONArray("result");

                    // looping through All Prospects
                    for (int i = 0; i < jsarray.length(); i++) {
                        JSONObject c = jsarray.getJSONObject(i);
                        listStepRecipe = new ArrayList<>();
                        listIngredient = new ArrayList<>();
                        listNutritions = new ArrayList<>();
                        listTimeRecipe = new ArrayList<>();
                        String title = c.getString("title");
                        String picture_url = c.getString("picture_url");
                        String portions = c.getString("portions");

                        JSONArray jarIngredients = c.getJSONArray("ingredients");
                        for(int a = 0; a<jarIngredients.length();a++){

                            JSONObject ingredient = jarIngredients.getJSONObject(a);
                            Log.e("YNOV RECETTE QUANTITY", ingredient.getString("quantity"));
                            Log.e("YNOV RECETTE UNIT", ingredient.getString("unit"));
                            Log.e("YNOV RECETTE NAME", ingredient.getString("name"));

                            String quantity = ingredient.getString("quantity");
                            String unit = ingredient.getString("unit");
                            String name = ingredient.getString("name");
                            IngredientRecipe ingredientRecipe = new IngredientRecipe();
                            ingredientRecipe.setName(name);
                            ingredientRecipe.setQuantity(quantity);
                            ingredientRecipe.setUnit(unit);
                            listIngredient.add(ingredientRecipe);

                        }

                        JSONArray jarSteps = c.getJSONArray("steps");
                        for(int j = 0; j<jarSteps.length();j++){

                            JSONObject steps = jarSteps.getJSONObject(j);
                            Log.e("YNOV RECETTE ORDER", steps.getString("order"));
                            Log.e("YNOV RECETTE STEP", steps.getString("step"));

                            String order = steps.getString("order");
                            String step = steps.getString("step");
                            StepRecipe etape = new StepRecipe();
                            etape.setOrder(order);
                            etape.setStep(step);
                            listStepRecipe.add(etape);
                            //StepRecipe stepRecipe = new StepRecipe(order,step, (ArrayList<StepRecipe>) listStepRecipe);

                        }



                        JSONObject jNutrition = c.getJSONObject("nutrition");
                        String calorie = jNutrition.getString("kcal");
                        String protein = jNutrition.getString("protein");
                        String fat = jNutrition.getString("fat");
                        String carbohydrate = jNutrition.getString("carbohydrate");
                        String sugar = jNutrition.getString("sugar");
                        String sat_fat = jNutrition.getString("sat_fat");
                        String fiber = jNutrition.getString("fiber");
                        String sodium = jNutrition.getString("sodium");
                        NutritionRecipe nutritionRecipe = new NutritionRecipe();
                        nutritionRecipe.setCalorie(calorie);
                        nutritionRecipe.setCarbohydrate(carbohydrate);
                        nutritionRecipe.setFat(fat);
                        nutritionRecipe.setFiber(fiber);
                        nutritionRecipe.setProtein(protein);
                        nutritionRecipe.setSat_fat(sat_fat);
                        nutritionRecipe.setSodium(sodium);
                        nutritionRecipe.setSugar(sugar);
                        listNutritions.add(nutritionRecipe);



                        JSONObject jTime = c.getJSONObject("time");
                        String total = jTime.getString("total");
                        String baking = jTime.getString("baking");
                        String prep = jTime.getString("prep");
                        TimeRecipe timeRecipe = new TimeRecipe();
                        timeRecipe.setBaking(baking);
                        timeRecipe.setPrep(prep);
                        timeRecipe.setTotal(total);
                        listTimeRecipe.add(timeRecipe);

                        Recipe recipe = new Recipe();
                        recipe.setTitle(title);
                        recipe.setPicture_url(picture_url);
                        recipe.setPortions(portions);
                        recipe.setSteps(listStepRecipe)  ;
                        recipe.setIngredientRecipes(listIngredient);
                        recipe.setNutritionRecipes(listNutritions);
                        recipe.setTimeRecipes(listTimeRecipe);
                        listRecipe.add(recipe);
                    }
                } catch (final JSONException e) {
                    /*Log.e("YNOV", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });*/
                    callback.onErrorOccured(e);
                }
            } else {
                /*Log.e("YNOV", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });*/
                callback.onErrorOccured(new Exception("Couldn't get json from server."));
            }
            return listRecipe;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> result) {
            super.onPostExecute(result);
            callback.onPostExecute(result);

        }
    }

}
