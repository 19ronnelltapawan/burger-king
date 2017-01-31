package com.project.error404.burgerking;

/**
 * Created by Onel on 1/28/2017.
 */

public class RecyclerModel {
    private String category_name;
    private String category_img;

    private String subcategory_name;
    private int subcategory_img;

    public String getCategory_name() { return category_name; }

    public void setCategory_name(String category_name) { this.category_name = category_name; }

    public String getCategory_img() { return category_img; }

    public void setCategory_img(String category_img) { this.category_img = category_img; }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) { this.subcategory_name = subcategory_name; }

    public int getSubcategory_img() {
        return subcategory_img;
    }

    public void setSubcategory_img(int subcategory_img) {
        this.subcategory_img = subcategory_img;
    }
}
