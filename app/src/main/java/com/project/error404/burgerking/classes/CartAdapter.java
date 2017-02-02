package com.project.error404.burgerking.classes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.error404.burgerking.R;

import java.util.ArrayList;

/**
 * Created by Onel on 2/1/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<RecyclerModel> rC;
    private Context context;
    private Activity activity;

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_sub, viewGroup, false);
        ImageView subcategory_img = (ImageView) view.findViewById(R.id.subcategory_img);
        TextView subcategory_name = (TextView) view.findViewById(R.id.subcategory_name);
        TextView subcategory_price = (TextView) view.findViewById(R.id.subcategory_price);
        return new CartAdapter.ViewHolder(view, subcategory_img, subcategory_name, subcategory_price);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder viewHolder, int i) {
        viewHolder.subcategory_img.setImageResource(rC.get(i).getSubcategory_img());
        viewHolder.subcategory_name.setText(rC.get(i).getSubcategory_name());
        viewHolder.subcategory_name.setTextColor(Color.parseColor("#8B4513"));
        viewHolder.subcategory_price.setText(rC.get(i).getItem_desc());
        //viewHolder.subcategory_price.setText(context.getString(R.string.alacarte)+ " - " +rC.get(i).getSubcategory_alacarte_price());
        //viewHolder.subcategory_price.append("\n" +context.getString(R.string.meal)+ " - " +rC.get(i).getSubcategory_meal_price());
    }

    public CartAdapter(Context context, ArrayList<RecyclerModel> rC, Activity activity) {
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

            }
    }

    @Override
    public int getItemCount() {
        return rC.size();
    }
}
