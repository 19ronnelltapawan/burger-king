package com.project.error404.burgerking.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.error404.burgerking.classes.DatabaseHelper;
import com.project.error404.burgerking.R;
import com.project.error404.burgerking.classes.RecyclerModel;
import com.project.error404.burgerking.classes.DataAdapter;
import com.project.error404.burgerking.classes.myClass;

import java.util.ArrayList;

public class MasterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String category_name[] = {
            "BURGERS",
            "HOTDOGS",
            "CHICKEN & MORE",
            "SALADS & VEGGIES",
            "BREAKFAST",
            "BEVERAGES",
            "COFFEE",
            "SIDES",
            "SWEETS",
            "VALUE MENU"
    };

    private final String category_image[] = {
            "http://i.imgur.com/jB27NM6.jpg",
            "http://i.imgur.com/RvhJl4A.jpg",
            "http://i.imgur.com/XQ023QN.png",
            "http://i.imgur.com/aVab3Yb.jpg",
            "http://i.imgur.com/V4Wv39k.jpg",
            "http://i.imgur.com/hBM38zu.jpg",
            "http://i.imgur.com/INpmqHP.jpg",
            "http://i.imgur.com/C55wUrL.jpg",
            "http://i.imgur.com/gcvHJXz.jpg",
            "http://i.imgur.com/HijhWpE.png"
    };

    EditText input;
    TextView fullName, email;

    int id, currentID;
    String currentEmail, inputID;

    ActionBarDrawerToggle toggle;
    AlertDialog.Builder builder;
    ArrayList list;
    Cursor res;
    DataAdapter adapter;
    DatabaseHelper myDB;
    DrawerLayout drawer;
    FloatingActionButton fab;
    myClass mC;
    NavigationView navigationView;
    RecyclerModel rC;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                res = myDB.selectItems(currentID);
                if (res.getCount() == 0)
                    Snackbar.make(view, "No items in cart yet", Snackbar.LENGTH_LONG).show();
                else
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));

            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        // start
        initViews();
        fullName = (TextView) header.findViewById(R.id.fullName);
        email = (TextView) header.findViewById(R.id.email);

        mC = new myClass();
        myDB = new DatabaseHelper(this);
        myPrefs = getSharedPreferences(mC.getPrefsName(), 0);

        currentEmail = myPrefs.getString("email", "");
        res = myDB.selectCurrentData(currentEmail);

        editor = myPrefs.edit();
        editor.putInt("id", Integer.parseInt(res.getString(0)));
        editor.commit();
        currentID = myPrefs.getInt("id", 0);

        fullName.setText(res.getString(1)+" "+res.getString(2));
        email.setText(res.getString(3));

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();

        if (id == R.id.nav_profile) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter current password");
            input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    res = myDB.selectCurrentData(currentEmail);

                    inputID = input.getText().toString();
                    if (inputID.equals(res.getString(4)))
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    else
                        Toast.makeText(getApplicationContext(), "Password did not match", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        else if (id == R.id.nav_history) {
            res = myDB.selectHistory(currentID);
            builder = new AlertDialog.Builder(this);

            if (res.getString(0) == null)
                builder.setMessage("Nothing found, order now!");
            else
                builder.setMessage(res.getString(0));

            builder.setCancelable(true);
            builder.setTitle("Order History");
            builder.show();
        }
        else if (id == R.id.nav_logout) {
            myPrefs = getSharedPreferences(mC.getPrefsName(), 0);
            editor = myPrefs.edit();
            editor.putBoolean("isFromSplash", false);
            editor.putBoolean("isLoggedIn", false);
            editor.putInt("id", -1);
            editor.putString("email", "");
            editor.commit();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finishAfterTransition();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else {
            super.onBackPressed();
            finishAfterTransition();
        }
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fab.show();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        list = prepareData();
        adapter = new DataAdapter(getApplicationContext(), list, this);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList prepareData(){
        list = new ArrayList<>();
        for(int i=0; i<category_name.length; i++) {
            rC = new RecyclerModel();
            rC.setCategory_name(category_name[i]);
            rC.setCategory_img(category_image[i]);
            list.add(rC);
        }
        return list;
    }
}
