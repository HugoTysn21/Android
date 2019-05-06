package com.ynov.apprecipe.service;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.ynov.apprecipe.managers.MeasurementManager;
import com.ynov.apprecipe.model.Measure;

import java.util.ArrayList;

public class MeasurementActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerMeasure mAdapter;
    public ImageView btnRemove;
    public MeasurementManager measurementManager = new MeasurementManager(this);
    public ArrayList<Measure> myMeasure;
    public ArrayList<Measure> mTypeMeasure;
    private LineChart mChart;
    private EditText mMeasureValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*mTypeMeasure = measurementManager.getMeasureType("");*/
        mTypeMeasure = measurementManager.getMeasureType("");
        myMeasure = measurementManager.getAllMeasure();



        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*openDialog();*/

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MeasurementActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.pop_measure,null);
                final RadioGroup radioGroup = mView.findViewById(R.id.add_type_measure_v);
                mMeasureValue = mView.findViewById(R.id.measure_add_value);


                mBuilder.setView(mView)
                        .setPositiveButton("add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                /* mWeightValue = findViewById(R.id.weight_add_value);*/
                                int selectedId = radioGroup.getCheckedRadioButtonId();
                                radioButton = (RadioButton) mView.findViewById(selectedId);
                                Float measureValue = Float.valueOf(mMeasureValue.getText().toString());
                                String measureTypeValue = radioButton.getText().toString();
                                Measure measure = new Measure(0, "11/02/2018", measureValue, measureTypeValue);
                                myMeasure.add(measure);
                                Log.i("dff", "onClick: " + measureTypeValue);
                                mTypeMeasure.add(measure);
                                measurementManager.addMeasure(measure);
                                mAdapter.notifyDataSetChanged();
                                /* startActivity(intent);*/
                                mChart.notifyDataSetChanged();
                                mChart.invalidate();

                                Toast.makeText(MeasurementActivity.this, "Cliquer sur un autre onglet pour que le graph s'actualise. je n'arrive pas a set les data quand je lui ajoute une donnée", Toast.LENGTH_LONG).show();
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


                /*EditText measureText = findViewById(R.id.add_measure_v);
                 *//*EditText measureTypeText = findViewById(R.id.add_type_measure_v);*//*
                radioGroup = findViewById(R.id.add_type_measure_v);
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                Float measureValue = Float.valueOf(measureText.getText().toString());
                String measureTypeValue = radioButton.getText().toString();

                Measure measure = new Measure(0, "11/02/2018", measureValue, measureTypeValue);

                Log.i("DATA", "measure : " + measureValue + measureTypeValue);
                measurementManager.addMeasure(measure);
                Intent intent = getIntent();
                finish();
                startActivity(intent);*/

        Button all = findViewById(R.id.measure_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button buttonArm = findViewById(R.id.measure_bras);
                Button buttonHip = findViewById(R.id.measure_hanche);
                Button buttonLegs = findViewById(R.id.measure_cuisse);
                Button buttonHeight = findViewById(R.id.measure_taille);
                Button buttonAll = findViewById(R.id.measure_all);

                buttonAll.setTextColor(getApplication().getResources().getColor(R.color.design_default_color_primary));

                //for "disable" UI button
                buttonHeight.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonHip.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonArm.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonLegs.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));

                buildRecyclerView();
                graphAllChart("Cuisse", "Bras", "Hanche", "Taille");

            }
        });

        Button buttonArm = findViewById(R.id.measure_bras);
        buttonArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button buttonArm = findViewById(R.id.measure_bras);
                Button buttonHip = findViewById(R.id.measure_hanche);
                Button buttonLegs = findViewById(R.id.measure_cuisse);
                Button buttonHeight = findViewById(R.id.measure_taille);
                Button buttonAll = findViewById(R.id.measure_all);

                buttonArm.setTextColor(getApplication().getResources().getColor(R.color.design_default_color_primary));

                //for "disable" UI button
                buttonHeight.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonHip.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonAll.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonLegs.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));

                getDetails("Bras");
                graphChartType("Bras");
            }
        });

        Button buttonHip = findViewById(R.id.measure_hanche);
        buttonHip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button buttonArm = findViewById(R.id.measure_bras);
                Button buttonHip = findViewById(R.id.measure_hanche);
                Button buttonLegs = findViewById(R.id.measure_cuisse);
                Button buttonHeight = findViewById(R.id.measure_taille);
                Button buttonAll = findViewById(R.id.measure_all);

                buttonHip.setTextColor(getApplication().getResources().getColor(R.color.design_default_color_primary));

                //for "disable" UI button
                buttonHeight.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonArm.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonAll.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonLegs.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));


                getDetails("Hanche");
                graphChartType("Hanche");
            }
        });

        Button buttonLegs = findViewById(R.id.measure_cuisse);
        buttonLegs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button buttonArm = findViewById(R.id.measure_bras);
                Button buttonHip = findViewById(R.id.measure_hanche);
                Button buttonLegs = findViewById(R.id.measure_cuisse);
                Button buttonHeight = findViewById(R.id.measure_taille);
                Button buttonAll = findViewById(R.id.measure_all);

                buttonLegs.setTextColor(getApplication().getResources().getColor(R.color.design_default_color_primary));

                //for "disable" UI button
                buttonHeight.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonHip.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonAll.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonArm.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));

                getDetails("Cuisse");
                graphChartType("Cuisse");
            }
        });

        Button buttonHeight = findViewById(R.id.measure_taille);
        buttonHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button buttonArm = findViewById(R.id.measure_bras);
                Button buttonHip = findViewById(R.id.measure_hanche);
                Button buttonLegs = findViewById(R.id.measure_cuisse);
                Button buttonHeight = findViewById(R.id.measure_taille);
                Button buttonAll = findViewById(R.id.measure_all);

                buttonHeight.setTextColor(getApplication().getResources().getColor(R.color.design_default_color_primary));

                //for "disable" UI button
                buttonArm.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonHip.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonAll.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                buttonLegs.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));

                getDetails("Taille");
                graphChartType("Taille");
            }
        });

        btnRemove = findViewById(R.id.button_remove_m);
        mChart = findViewById(R.id.line_chart_measure);

        buildRecyclerView();
        graphAllChart("Cuisse", "Bras", "Hanche", "Taille");

    }

    private void openDialog() {

        /*DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        final DialogMesaure customMeasure = new DialogMesaure(this);
        customMeasure.setTitle("Add your Weight");

        customMeasure.getMaddMeasure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText measureText = findViewById(R.id.add_measure_v);
                EditText measureTypeText = findViewById(R.id.add_type_measure_v);
                radioGroup = findViewById(R.id.add_type_measure_v);
                radioButton = findViewById(radioId);


                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                Float measureValue = Float.valueOf(customMeasure.getmMeValue().getText().toString());
                RadioGroup measureGroup = customMeasure.getRadioGroup();
                Intent intent = new Intent(MeasurementActivity.this,MeasurementActivity.class);
                intent.putExtra("SomeTag",""+radioButton.getText());
                String measureTypeValue = customMeasure.getRadioButton(radioGroup).getText().toString();

                Measure measure = new Measure(0, "11/02/2018", measureValue, measureTypeValue);
                myMeasure.add(measure);
                Log.i("DATA", "measure : " + measureValue + measureTypeValue);
                measurementManager.addMeasure(measure);
                mAdapter.notifyDataSetChanged();
                 startActivity(intent);
                customMeasure.dismiss();
            }
        });
        customMeasure.buildPopUp();*/
    }

    private void graphChartType(String type) {

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        /*yValues.add(new Entry(0, 70f));
        yValues.add(new Entry(1, 60f));
        yValues.add(new Entry(2, 40f));
        yValues.add(new Entry(3, 90f));
        yValues.add(new Entry(4, 80f));
        yValues.add(new Entry(5, 50f));*/

        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<Measure> mMeasure = new ArrayList<>();
        mMeasure = measurementManager.getMeasureType(type);
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i <= mMeasure.size() - 1; i++) {
            /*if(lineWeight.getV().equals(null)){
                xVals.add(i,"");
            }*/
            Measure m = mMeasure.get(i);
            m.getValue();

            if (m.getDate() == null) {
                xVals.add(i, "");
            }
            xVals.add(i, m.getDate());
            yValues.add(new Entry(i, m.getValue()));


            /*xVals.add(i, mW.get());
            yVals.add(new Entry(data.getValue(), i));*/
        }


        LineDataSet set1 = new LineDataSet(yValues, type);
        /*set1.setFillAlpha(110);*/

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.RED);
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        /*set1.setDrawCircles(false);
        set1.setDrawValues(false);*/
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);

        //refresh
        mChart.invalidate();

    }

    private void graphAllChart(String one, String two, String three, String four) {

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Measure> mMeasureArm;
        ArrayList<Measure> mMeasureHeight;
        ArrayList<Measure> mMeasureLegs;
        ArrayList<Measure> mMeasureHip;

        ArrayList<Entry> yValuesOne = new ArrayList<>();
        ArrayList<Entry> yValuesTwo = new ArrayList<>();
        ArrayList<Entry> yValuesThree = new ArrayList<>();
        ArrayList<Entry> yValuesFour = new ArrayList<>();

        mMeasureLegs = measurementManager.getMeasureType(one);
        mMeasureArm = measurementManager.getMeasureType(two);
        mMeasureHip = measurementManager.getMeasureType(three);
        mMeasureHeight = measurementManager.getMeasureType(four);

        ArrayList<String> xValsOne = new ArrayList<String>();
        ArrayList<String> xValsTwo = new ArrayList<String>();
        ArrayList<String> xValsThree = new ArrayList<String>();
        ArrayList<String> xValsFour = new ArrayList<String>();

        // for loop for line arm
        for (int i = 0; i <= mMeasureArm.size() - 1; i++) {
            /*if(lineWeight.getV().equals(null)){
                xVals.add(i,"");
            }*/
            Measure m = mMeasureArm.get(i);
            m.getValue();

            if (m.getDate() == null) {
                xValsOne.add(i, "");
            }

            yValuesTwo.add(new Entry(i, m.getValue()));


            /*xVals.add(i, mW.get());
            yVals.add(new Entry(data.getValue(), i));*/
        }


        // for loop for line legs
        for (int i = 0; i <= mMeasureLegs.size() - 1; i++) {

            Measure m = mMeasureLegs.get(i);
            m.getValue();

            if (m.getDate() == null) {
                xValsTwo.add(i, "");
            }

            yValuesOne.add(new Entry(i, m.getValue()));

        }

        // for loop for line legs
        for (int i = 0; i <= mMeasureHip.size() - 1; i++) {

            Measure m = mMeasureHip.get(i);
            m.getValue();

            if (m.getDate() == null) {
                xValsThree.add(i, "");
            }

            yValuesThree.add(new Entry(i, m.getValue()));

        }

        // for loop for line legs
        for (int i = 0; i <= mMeasureHeight.size() - 1; i++) {

            Measure m = mMeasureHeight.get(i);
            m.getValue();

            if (m.getDate() == null) {
                xValsFour.add(i, "");
            }

            yValuesFour.add(new Entry(i, m.getValue()));

        }

        /*// for loop for single line (not clean)
        for (int i = 0; i <= mMeasure.size() - 1; i++) {
            *//*if(lineWeight.getV().equals(null)){
                xVals.add(i,"");
            }*//*
            Measure m = mMeasure.get(i);
            m.getValue();

            if (m.getDate() == null ){
                xVals.add(i,"");
            }

            yValues.add(new Entry(i,m.getValue()));


            *//*xVals.add(i, mW.get());
            yVals.add(new Entry(data.getValue(), i));*//*
        }*/

        LineDataSet set1 = new LineDataSet(yValuesOne, "Legs");
        LineDataSet set2 = new LineDataSet(yValuesTwo, "Arm");
        LineDataSet set3 = new LineDataSet(yValuesThree, "Hip");
        LineDataSet set4 = new LineDataSet(yValuesFour, "Height");


        //setup line
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.RED);
        set1.setValueTextColor(Color.RED);
        set1.setLineWidth(3.5f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(Color.GREEN);
        set2.setValueTextColor(Color.GREEN);
        set2.setLineWidth(3.5f);
        set2.setFillAlpha(65);
        set2.setFillColor(ColorTemplate.getHoloBlue());
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setDrawCircleHole(false);

        set3.setAxisDependency(YAxis.AxisDependency.LEFT);
        set3.setColor(Color.BLUE);
        set3.setValueTextColor(Color.BLUE);
        set3.setLineWidth(3.5f);
        set3.setFillAlpha(65);
        set3.setFillColor(ColorTemplate.getHoloBlue());
        set3.setHighLightColor(Color.rgb(244, 117, 117));
        set3.setDrawCircleHole(false);

        set4.setAxisDependency(YAxis.AxisDependency.LEFT);
        set4.setColor(Color.DKGRAY);
        set4.setValueTextColor(Color.DKGRAY);
        set4.setLineWidth(3.5f);
        set4.setFillAlpha(65);
        set4.setFillColor(ColorTemplate.getHoloBlue());
        set4.setHighLightColor(Color.rgb(244, 117, 117));
        set4.setDrawCircleHole(false);
        // end setup line

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);

        // set data
        LineData data = new LineData(dataSets);
        mChart.setData(data);

        //refresh data mais cfjiznfij ça marche passssss fait chierr
        mChart.invalidate();

    }
    private void buildRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView_measure);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerMeasure(myMeasure);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerMeasure.IOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                myMeasure.get(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            private void removeItem(int position) {
                if (myMeasure.size() != 1) {
                    measurementManager.removeMeasure(myMeasure.get(position));
                    myMeasure.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mChart.notifyDataSetChanged();
                    mChart.invalidate();
                    Toast.makeText(MeasurementActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MeasurementActivity.this, "You can't delete the last item.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void getDetails(String type) {

        final ArrayList<Measure> mList = measurementManager.getMeasureType(type);

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView_measure);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerMeasure(mList);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerMeasure.IOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mList.get(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            private void removeItem(int position) {
                if (mList.size() != 1) {
                    measurementManager.removeMeasure(mList.get(position));
                    mList.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(MeasurementActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MeasurementActivity.this, "Add a measure to be able to remove the one there.", Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.measurement, menu);
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
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, WeightActivity.class);
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
