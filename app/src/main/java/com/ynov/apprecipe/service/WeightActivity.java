package com.ynov.apprecipe.service;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ynov.apprecipe.MainActivity;
import com.ynov.apprecipe.R;
import com.ynov.apprecipe.managers.WeightManager;
import com.ynov.apprecipe.model.Account;
import com.ynov.apprecipe.model.Weight;

import java.util.ArrayList;

public class WeightActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final WeightManager weightManager = new WeightManager(this);
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerWeight mAdapter;
    public ImageView btnRemove;
    public ArrayList<Weight> mWeights;
    private LineChart mChart;
    private Button mAddWeight;
    private EditText mWeightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
               /* openDialog();*/

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(WeightActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.weight_dialog,null);
               mWeightValue = mView.findViewById(R.id.weight_add_value);

                mBuilder.setView(mView)
                        .setPositiveButton("add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               /* mWeightValue = findViewById(R.id.weight_add_value);*/
                                Float weightValue = Float.valueOf(mWeightValue.getText().toString());
                                Weight weight = new Weight(0,"11/02/2018",weightValue);

                                Log.i("DATA", "weight : " + weightValue);
                                mWeights.add(weight);
                                weightManager.addWeight(weight);
                                mChart.notifyDataSetChanged();
                                mChart.invalidate();
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("retour", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                /*mAddWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Float weightValue = Float.valueOf(mWeightValue.getText().toString());
                        Weight weight = new Weight(0,"11/02/2018",weightValue);

                        Log.i("DATA", "weight : " + weightValue);
                        mWeights.add(weight);
                        weightManager.addWeight(weight);
                        mChart.notifyDataSetChanged();
                        mChart.invalidate();
                        mAdapter.notifyDataSetChanged();
                    }
                });*/

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mWeights = weightManager.getAllWeight();

        btnRemove = findViewById(R.id.button_remove_weight);
        mChart = findViewById(R.id.line_chart);

        buildRecyclerView();
        graphChart();

    }

    private void graphChart(){

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        /*yValues.add(new Entry(0, 70f));
        yValues.add(new Entry(1, 60f));
        yValues.add(new Entry(2, 40f));
        yValues.add(new Entry(3, 90f));
        yValues.add(new Entry(4, 80f));
        yValues.add(new Entry(5, 50f));*/

        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<Weight> mW;
        mW = weightManager.getAllWeight();
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i <= mW.size() - 1; i++) {
            /*if(lineWeight.getV().equals(null)){
                xVals.add(i,"");
            }*/
            Weight m = mW.get(i);
            m.getValue();

            if (m.getDate() == null ){
                xVals.add(i,"");
            }

            yValues.add(new Entry(i,m.getValue()));


            /*xVals.add(i, mW.get());
            yVals.add(new Entry(data.getValue(), i));*/
        }


        LineDataSet set1 = new LineDataSet(yValues,"Weight");

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.YELLOW);
        set1.setValueTextColor(Color.BLACK);
        set1.setLineWidth(4.5f);

        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        mChart.setData(data);
        mChart.notifyDataSetChanged();
        mChart.invalidate();

    }

    private void buildRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView_weight);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerWeight(mWeights);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerWeight.IOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mWeights.get(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onUpdateClick(int position) {

            }

            private void removeItem(int position) {
                if (mWeights.size() != 1){
                    weightManager.removeWeight(mWeights.get(position));
                    mWeights.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mChart.notifyDataSetChanged();
                    mChart.invalidate();
                    Toast.makeText(WeightActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(WeightActivity.this, "Add a weight to be able to remove the one there .", Toast.LENGTH_LONG).show();
                }

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
        getMenuInflater().inflate(R.menu.weight, menu);
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
            Intent intent = new Intent(this, RecipeActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
