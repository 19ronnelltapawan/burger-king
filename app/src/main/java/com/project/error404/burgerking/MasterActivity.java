package com.project.error404.burgerking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
    TextView fullname, email;

    int current_id;
    String current_email, inputText;

    AlertDialog.Builder builder;
    Cursor res;
    DatabaseHelper myDB;
    FloatingActionButton fab;
    myClass mC;
    RecyclerModel rC;
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setScrollBarDefaultDelayBeforeFade(1);
        fab.setScrollbarFadingEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                res = myDB.ViewItems(current_id);

                if (res.getCount() == 0)
                    Snackbar.make(view, "No items in cart yet", Snackbar.LENGTH_LONG).show();
                else
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        // start
        initViews();
        fullname = (TextView) header.findViewById(R.id.fullName);
        email = (TextView) header.findViewById(R.id.email);

        mC = new myClass();
        myDB = new DatabaseHelper(this);
        myPrefs = getSharedPreferences(mC.getPrefsName(), 0);

        current_email = myPrefs.getString("email", "");
        current_id = myPrefs.getInt("id", 0);
        res = myDB.getCurrentData(current_email);

        editor = myPrefs.edit();
        editor.putInt("id", Integer.parseInt(res.getString(0)));
        editor.commit();

        fullname.setText(res.getString(1)+" "+res.getString(2));
        email.setText(res.getString(3));
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
        int id = item.getItemId();

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
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter current password");
            input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inputText = input.getText().toString();
                    if (inputText.equals(res.getString(4)))
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
            builder = new AlertDialog.Builder(this);
            res = myDB.getHistory(current_id);

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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        ArrayList list = prepareData();
        DataAdapter adapter = new DataAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList prepareData(){
        ArrayList list = new ArrayList<>();

        for(int i=0; i<category_name.length; i++) {
            rC = new RecyclerModel();
            rC.setCategory_name(category_name[i]);
            rC.setCategory_img(category_image[i]);
            list.add(rC);
        }

        return list;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}
