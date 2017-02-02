package com.project.error404.burgerking.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.error404.burgerking.R;
import com.project.error404.burgerking.classes.DataAdapterSub;
import com.project.error404.burgerking.classes.RecyclerModel;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    private final int[] subcategory_image = {
            R.drawable.hamburger,
            R.drawable.cheeseburger,
            R.drawable.doublecheeseburger,
            R.drawable.baconcheeseburger,
            R.drawable.bacondoublecheeseburger,
            R.drawable.baconcheesewhopper,
            R.drawable.doublewhopper,
            R.drawable.whopper,
            R.drawable.whopperjr
    };

    private final int[] subcategory_name = {
            R.string.hamburger_name,
            R.string.cheeseburger_name,
            R.string.doublecheeseburger_name,
            R.string.baconcheeseburger_name,
            R.string.bacondoublecheeseburger_name,
            R.string.baconcheesewhopper_name,
            R.string.doublewhopper_name,
            R.string.whopper_name,
            R.string.whopperjr_name
    };

    private final int[] subcategory_alacarte_price = {
            R.string.hamburger_alacarte_price,
            R.string.cheeseburger_alacarte_price,
            R.string.doublecheeseburger_alacarte_price,
            R.string.baconcheeseburger_alacarte_price,
            R.string.baconcheeseburger_alacarte_price,
            R.string.baconcheesewhopper_alarcarte_price,
            R.string.doublewhopper_alacarte_price,
            R.string.whopper_alacarte_price,
            R.string.whopperjr_alacarte_price
    };

    private final int[] subcategory_meal_price = {
            R.string.hamburger_meal_price,
            R.string.cheeseburger_alacarte_price,
            R.string.doublecheeseburger_meal_price,
            R.string.baconcheeseburger_meal_price,
            R.string.bacondoublecheeseburger_meal_price,
            R.string.baconcheesewhopper_meal_price,
            R.string.doublewhopper_meal_price,
            R.string.whopper_meal_price,
            R.string.whopperjr_meal_price
    };

    ArrayList list;
    DataAdapterSub adapter;
    RecyclerModel rC;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        initViews();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        list = prepareData();
        adapter = new DataAdapterSub(getApplicationContext(), list, this);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList prepareData() {
        list = new ArrayList<>();

        for(int i = 0; i < subcategory_name.length; i++){
            rC = new RecyclerModel();
            rC.setSubcategory_img(subcategory_image[i]);
            rC.setSubcategory_name(getString(subcategory_name[i]));
            rC.setSubcategory_alacarte_price(getString(subcategory_alacarte_price[i]));
            rC.setSubcategory_meal_price(getString(subcategory_meal_price[i]));
            list.add(rC);
        }

        return list;
    }
}
