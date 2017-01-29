package com.project.error404.burgerking;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Onel on 1/28/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<RecyclerModel> rC;
    private Context context;

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        ImageView category_img = (ImageView) view.findViewById(R.id.category_img);
        TextView category_name = (TextView) view.findViewById(R.id.category_name);
        return new ViewHolder(view, category_img, category_name);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.category_name.setText(rC.get(i).getCategory_name());
        viewHolder.category_name.setTextColor(Color.parseColor("#8B4513"));
        Picasso.with(context).load(rC.get(i).getCategory_img()).resize(350, 350).into(viewHolder.category_img);
    }

    public DataAdapter(Context context, ArrayList<RecyclerModel> rC) {
        this.rC = rC;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView category_img;
        private TextView category_name;

        public ViewHolder(View view, ImageView category_img, TextView category_name) {
            super(view);
            view.setOnClickListener(this);
            this.category_img = category_img;
            this.category_name = category_name;
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return rC.size();
    }
}
