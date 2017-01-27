package com.project.error404.burgerking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MasterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper myDB = new DatabaseHelper(this);
    myClass mc = new myClass();
    SharedPreferences myPrefs;
    String current_email;
    Cursor res;
    int current_id;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Cursor res = myDB.ViewItems(current_id);

                if (res.getCount() == 0)
                    Snackbar.make(view, "No items in cart yet", Snackbar.LENGTH_LONG).show();
                    //.setAction("Action", null).show();
                    //Toast.makeText(getApplicationContext(),"No items found.",Toast.LENGTH_LONG).show();
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

        myPrefs = getSharedPreferences(mc.getPrefsName(), 0);
        current_email = myPrefs.getString("email", "");
        res = myDB.getCurrentData(current_email);

        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("id", Integer.parseInt(res.getString(0)));
        editor.commit();

        current_id = myPrefs.getInt("id", 0);
        setTitle("HOME  ");

        TextView fullname = (TextView) header.findViewById(R.id.fullName);
        fullname.setText(res.getString(1)+" "+res.getString(2));

        TextView email = (TextView) header.findViewById(R.id.email);
        email.setText(res.getString(3));
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Input Current Password");

            //Set up the input
            input = new EditText(this);
            //Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            //Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String m_Text = input.getText().toString();
                    if (m_Text.equals(res.getString(4))) {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                    } else
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
        } else if (id == R.id.nav_history) {
            Cursor res = myDB.getHistory(current_id);

            if (res.getString(0)==null)
                showMessage("Order History", "Nothing found, order now!");
            else
                showMessage("Order History", res.getString(0));
        } else if (id == R.id.nav_logout) {
            myPrefs = getSharedPreferences(mc.getPrefsName(), 0);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.putString("email", "");
            editor.putInt("id", -1);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void onClickBurger(View view) {
        Bundle b = new Bundle();
        Intent i = new Intent(getApplicationContext(), OrderActivity.class);

        switch (view.getId()) {
            case R.id.hamburger:
                b.putDouble("alacarteprice", 40);
                b.putDouble("mealprice", 60);
                b.putString("itemname1", getString(R.string.hamburger_alacarte_name));
                b.putString("itemname2", getString(R.string.hamburger_meal_name));
                b.putInt("choice", 1);
                break;
            case R.id.cheeseburger:
                b.putDouble("alacarteprice", 50);
                b.putDouble("mealprice", 70);
                b.putString("itemname1", getString(R.string.cheeseburger_alacarte_name));
                b.putString("itemname2", getString(R.string.cheeseburger_meal_name));
                b.putInt("choice", 2);
                break;
            case R.id.doublecheeseburger:
                b.putDouble("alacarteprice", 79);
                b.putDouble("mealprice", 99);
                b.putString("itemname1", getString(R.string.doublecheeseburger_alacarte));
                b.putString("itemname2", getString(R.string.doublecheeseburger_meal_name));
                b.putInt("choice", 3);
                break;
            case R.id.baconcheeseburger:
                b.putDouble("alacarteprice", 85);
                b.putDouble("mealprice", 109);
                b.putString("itemname1", getString(R.string.baconcheeseburger_alacarte_name));
                b.putString("itemname2", getString(R.string.baconcheeseburger_meal_name));
                b.putInt("choice", 4);
                break;
            case R.id.bacondoublecheeseburger:
                b.putDouble("alacarteprice", 109);
                b.putDouble("mealprice", 129);
                b.putString("itemname1", getString(R.string.bacondoublecheeseburger_alacarte_name));
                b.putString("itemname2", getString(R.string.bacondoublecheeseburger_meal_name));
                b.putInt("choice", 5);
                break;
            case R.id.baconcheesewhopper:
                b.putDouble("alacarteprice", 99);
                b.putDouble("mealprice", 119);
                b.putString("itemname1", getString(R.string.baconcheesewhopper_alacarte_name));
                b.putString("itemname2", getString(R.string.baconcheesewhopper_meal_name));
                b.putInt("choice", 6);
                break;
            case R.id.doublewhopper:
                b.putDouble("alacarteprice", 129);
                b.putDouble("mealprice", 159);
                b.putString("itemname1", getString(R.string.doublewhopper_alacarte_name));
                b.putString("itemname2", getString(R.string.doublewhopper_meal_name));
                b.putInt("choice", 7);
                break;
            case R.id.whopper:
                b.putDouble("alacarteprice", 75);
                b.putDouble("mealprice", 99);
                b.putString("itemname1", getString(R.string.whopper_alacarte_name));
                b.putString("itemname2", getString(R.string.whopper_meal_name));
                b.putInt("choice", 8);
                break;
            case R.id.whopperjr:
                b.putDouble("alacarteprice", 65);
                b.putDouble("mealprice", 85);
                b.putString("itemname1", getString(R.string.whopperjr_alacarte_name));
                b.putString("itemname2", getString(R.string.whopperjr_meal_name));
                b.putInt("choice", 9);
                break;
        }

        b.putDouble("golargeprice", 40);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}
