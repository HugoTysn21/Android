package com.ynov.apprecipe.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.ynov.apprecipe.MainActivity;
import com.ynov.apprecipe.R;
import com.ynov.apprecipe.interfaces.IAsyncTaskCallback;
import com.ynov.apprecipe.interfaces.IRecyclerViewListener;
import com.ynov.apprecipe.managers.DataManager;
import com.ynov.apprecipe.model.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static com.ynov.apprecipe.R.layout.activity_recipe;

public class RecipeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecipeAdapter mAdapter;
    private Button btnSort;
    private Button unSort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_recipe);
        setupView();
        loadData();
    }

    private void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnSort = findViewById(R.id.sort);
        unSort = findViewById(R.id.unSort);

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Collections.sort();*/
            }
        });
    }


    //Respect du Principe S.O.L.I.D un class ne doit pas chercher la data la lire la transmettre etc, découpe des steps
    private void loadData() {


        // je recupere mon manager et je load les data en faisant appel a ma task (interface)
        DataManager.getInstance().LoadDataFromApi(new IAsyncTaskCallback() {

            @Override
            public void onPreExecute() {
                //  nothing to do here
                //  show loading message, ...
            }

            @Override
            public void onErrorOccured(final Exception ex) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + ex.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            @Override
            public void onPostExecute(ArrayList objects) {
                mAdapter = new RecipeAdapter(objects, new IRecyclerViewListener() {
                    @Override
                    public void onItemClick(Recipe recipe) {

                        // je lui passe l'id de recipe en value
                        Intent newRecipeItemPage = RecipeItem.newIntent(RecipeActivity.this, recipe.getId());
                        RecipeActivity.this.startActivity(newRecipeItemPage);
                    }
                });
                recyclerView.setAdapter(mAdapter);
            }

        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, MeasurementActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, WeightActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
