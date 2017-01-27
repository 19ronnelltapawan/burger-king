package com.project.error404.burgerking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PlaceholderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);
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
