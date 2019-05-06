package com.ynov.apprecipe.service;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ynov.apprecipe.MainActivity;
import com.ynov.apprecipe.R;
import com.ynov.apprecipe.managers.AccountManager;
import com.ynov.apprecipe.managers.MeasurementManager;
import com.ynov.apprecipe.model.Account;

public class AccountActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*private Button save;
    private EditText height;
    private EditText date;
    private EditText start;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private SharedPreferences preferences;
    private CheckBox checkBox;

    public final static String dateOF = "date";
    public final static String weightSTART = "weight";
    public final static String GENDER = "gender";
    public final static String HEIGHT = "height";*/

    private EditText editText_height;
    private EditText editText_birth;
    private EditText editText_startWeight;
    private CheckBox checkBox_man;
    private CheckBox checkBox_woman;
    private Button save;
    private static final String PREFS = "PREFS";
    private static final String PREFS_HEIGHT = "PREFS_HEIGHT";
    private static final String PREFS_BIRTH = "PREFS_BIRTH";
    private static final String PREFS_STARTWEIGHT = "PREFS_STARTWEIGHT";
    private static final String PREFS_CHECKBOX_MAN = "PREFS_CHECKBOX_MAN";
    private static final String PREFS_CHECKBOX_WOMAN = "PREFS_CHECKBOX_WOMAN";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        // TEST

        save = findViewById(R.id.save_button);
        editText_height = (EditText) findViewById(R.id.height);
        editText_birth = (EditText) findViewById(R.id.date);
        editText_startWeight = (EditText) findViewById(R.id.start);
        checkBox_man = (CheckBox) findViewById(R.id.man);
        checkBox_woman = (CheckBox) findViewById(R.id.woman);

        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        editText_height.setText(sharedPreferences.getString(PREFS_HEIGHT, ""));
        editText_birth.setText(sharedPreferences.getString(PREFS_BIRTH, ""));
        editText_startWeight.setText(sharedPreferences.getString(PREFS_STARTWEIGHT, ""));

        if (sharedPreferences.getInt(PREFS_CHECKBOX_MAN, 0) == 1) {
            checkBox_man.setChecked(true);
        } else {
            checkBox_man.setChecked(false);
        }

        if (sharedPreferences.getInt(PREFS_CHECKBOX_WOMAN, 0) == 1) {
            checkBox_woman.setChecked(true);
        } else {
            checkBox_woman.setChecked(false);
        }

        // make button listener
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveChanges(v);
                }
            });
        // END TEST


        /*final AccountManager accountManager = new AccountManager(this);

        *//*String height = heightText.getText().toString();
        String genderType = radioButton.getText().toString();
        String date_of =  dateText.getText().toString();
        String startWeight =  startText.getText().toString();*//*

        final EditText heightText = findViewById(R.id.height);
        final EditText dateText = findViewById(R.id.date);
        final EditText startText = findViewById(R.id.start);
        Button save = findViewById(R.id.save_button);

        radioGroup = findViewById(R.id.button_gender);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox.isChecked()) {
                    // permet d'ecrire les data des user dans un fichier que l'editor va editer
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(GENDER, radioButton.getText().toString());
                    editor.putString(HEIGHT, heightText.getText().toString());
                    editor.putString(dateOF, dateText.getText().toString());
                    editor.putString(weightSTART, startText.getText().toString());
                    editor.apply();
                }

                Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gender = preferences.getString(GENDER, "");
        String taille = preferences.getString(HEIGHT, "");
        String date = preferences.getString(dateOF, "");
        String weight = preferences.getString(weightSTART, "");


        radioButton.setText(gender);
        heightText.setText(taille);
        dateText.setText(date);
        startText.setText(weight);*/




        /* accountManager.addAccount();*/
        /*Toast toast = Toast.makeText(, "Votre compte a bien été enregistré", Toast.LENGTH_LONG).show();
         */
        /*Intent intent = getIntent();
        finish();
        startActivity(intent);*/


    }

    public void saveChanges(View view) {
        if (checkBox_man.isChecked()) {
            sharedPreferences
                    .edit()
                    .putString(PREFS_HEIGHT, editText_height.getText().toString())
                    .putString(PREFS_BIRTH, editText_birth.getText().toString())
                    .putString(PREFS_STARTWEIGHT, editText_startWeight.getText().toString())
                    .putInt(PREFS_CHECKBOX_MAN, 1)
                    .apply();
        } else if (checkBox_woman.isChecked()) {
            sharedPreferences
                    .edit()
                    .putString(PREFS_HEIGHT, editText_height.getText().toString())
                    .putString(PREFS_BIRTH, editText_birth.getText().toString())
                    .putString(PREFS_STARTWEIGHT, editText_startWeight.getText().toString())
                    .putInt(PREFS_CHECKBOX_WOMAN, 1)
                    .apply();
        }
        else{
            checkBox_man.setError("Rentrez votre genre");
        }
        hideKeyboard(this);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
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
            Intent intent = new Intent(this, RecipeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, WeightActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
