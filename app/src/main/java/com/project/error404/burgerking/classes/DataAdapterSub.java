package com.project.error404.burgerking.classes;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.error404.burgerking.activities.OrderActivity;
import com.project.error404.burgerking.R;

import java.util.ArrayList;

/**
 * Created by Onel on 1/28/2017.
 */

public class DataAdapterSub extends RecyclerView.Adapter<DataAdapterSub.ViewHolder> {

    private ArrayList<RecyclerModel> rC;
    private Context context;
    private Activity activity;

    @Override
    public DataAdapterSub.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_sub, viewGroup, false);
        ImageView subcategory_img = (ImageView) view.findViewById(R.id.subcategory_img);
        TextView subcategory_name = (TextView) view.findViewById(R.id.subcategory_name);
        TextView subcategory_price = (TextView) view.findViewById(R.id.subcategory_price);
        return new ViewHolder(view, subcategory_img, subcategory_name, subcategory_price);
    }

    @Override
    public void onBindViewHolder(DataAdapterSub.ViewHolder viewHolder, int i) {
        viewHolder.subcategory_img.setImageResource(rC.get(i).getSubcategory_img());
        viewHolder.subcategory_name.setText(rC.get(i).getSubcategory_name());
        viewHolder.subcategory_name.setTextColor(Color.parseColor("#8B4513"));
        viewHolder.subcategory_price.setText(context.getString(R.string.alacarte)+ " - " +rC.get(i).getSubcategory_alacarte_price());
        viewHolder.subcategory_price.append("\n" +context.getString(R.string.meal)+ " - " +rC.get(i).getSubcategory_meal_price());
    }

    public DataAdapterSub(Context context, ArrayList<RecyclerModel> rC, Activity activity) {
        this.rC = rC;
        this.context = context;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView subcategory_img;
        private TextView subcategory_name;
        private TextView subcategory_price;

        public ViewHolder(View view, ImageView subcategory_img, TextView subcategory_name, TextView subcategory_price) {
            super(view);
            view.setOnClickListener(this);
            this.subcategory_img = subcategory_img;
            this.subcategory_name = subcategory_name;
            this.subcategory_price = subcategory_price;
        }

        @Override
        public void onClick(View view) {
            Bundle b = new Bundle();
            Intent i = new Intent(activity.getApplicationContext(), OrderActivity.class);

            switch (getLayoutPosition()) {
                case 0:
                    b.putDouble("alacarteprice", 40);
                    b.putDouble("mealprice", 60);
                    b.putString("itemname1", context.getString(R.string.hamburger_alacarte_name));
                    b.putString("itemname2", context.getString(R.string.hamburger_meal_name));
                    b.putInt("choice", 1);
                    b.putInt("burger", R.drawable.hamburger);
                    break;
                case 1:
                    b.putDouble("alacarteprice", 50);
                    b.putDouble("mealprice", 70);
                    b.putString("itemname1", context.getString(R.string.cheeseburger_alacarte_name));
                    b.putString("itemname2", context.getString(R.string.cheeseburger_meal_name));
                    b.putInt("choice", 2);
                    b.putInt("burger", R.drawable.cheeseburger);
                    break;
                case 2:
                    b.putDouble("alacarteprice", 79);
                    b.putDouble("mealprice", 99);
                    b.putString("itemname1", context.getString(R.string.doublecheeseburger_alacarte));
                    b.putString("itemname2", context.getString(R.string.doublecheeseburger_meal_name));
                    b.putInt("choice", 3);
                    b.putInt("burger", R.drawable.doublecheeseburger);
                    break;
                case 3:
                    b.putDouble("alacarteprice", 85);
                    b.putDouble("mealprice", 109);
                    b.putString("itemname1", context.getString(R.string.baconcheeseburger_alacarte_name));
                    b.putString("itemname2", context.getString(R.string.baconcheeseburger_meal_name));
                    b.putInt("choice", 4);
                    b.putInt("burger", R.drawable.baconcheeseburger);
                    break;
                case 4:
                    b.putDouble("alacarteprice", 109);
                    b.putDouble("mealprice", 129);
                    b.putString("itemname1", context.getString(R.string.bacondoublecheeseburger_alacarte_name));
                    b.putString("itemname2", context.getString(R.string.bacondoublecheeseburger_meal_name));
                    b.putInt("choice", 5);
                    b.putInt("burger", R.drawable.bacondoublecheeseburger);
                    break;
                case 5:
                    b.putDouble("alacarteprice", 99);
                    b.putDouble("mealprice", 119);
                    b.putString("itemname1", context.getString(R.string.baconcheesewhopper_alacarte_name));
                    b.putString("itemname2", context.getString(R.string.baconcheesewhopper_meal_name));
                    b.putInt("choice", 6);
                    b.putInt("burger", R.drawable.baconcheesewhopper);
                    break;
                case 6:
                    b.putDouble("alacarteprice", 129);
                    b.putDouble("mealprice", 159);
                    b.putString("itemname1", context.getString(R.string.doublewhopper_alacarte_name));
                    b.putString("itemname2", context.getString(R.string.doublewhopper_meal_name));
                    b.putInt("choice", 7);
                    b.putInt("burger", R.drawable.doublewhopper);
                    break;
                case 7:
                    b.putDouble("alacarteprice", 75);
                    b.putDouble("mealprice", 99);
                    b.putString("itemname1", context.getString(R.string.whopper_alacarte_name));
                    b.putString("itemname2", context.getString(R.string.whopper_meal_name));
                    b.putInt("choice", 8);
                    b.putInt("burger", R.drawable.whopper);
                    break;
                case 8:
                    b.putDouble("alacarteprice", 65);
                    b.putDouble("mealprice", 85);
                    b.putString("itemname1", context.getString(R.string.whopperjr_alacarte_name));
                    b.putString("itemname2", context.getString(R.string.whopperjr_meal_name));
                    b.putInt("choice", 9);
                    b.putInt("burger", R.drawable.whopperjr);
                    break;
            }

            b.putDouble("golargeprice", 40);
            i.putExtras(b);

            activity.startActivity(i, ActivityOptions.makeSceneTransitionAnimation(activity, subcategory_img, "myTransitionView").toBundle());
        }
    }

    @Override
    public int getItemCount() {
        return rC.size();
    }
}
