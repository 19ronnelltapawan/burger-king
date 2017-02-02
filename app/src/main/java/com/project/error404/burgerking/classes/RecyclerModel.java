package com.project.error404.burgerking.classes;

/**
 * Created by Onel on 1/28/2017.
 */

public class RecyclerModel {

    private String category_name;
    private String category_img;
    private String item_desc;
    private String subcategory_name;
    private String subcategory_alacarte_price;
    private String subcategory_meal_price;
    private int subcategory_img;

    public String getCategory_name() { return category_name; }

    public void setCategory_name(String category_name) { this.category_name = category_name; }

    public String getCategory_img() { return category_img; }

    public void setCategory_img(String category_img) { this.category_img = category_img; }

    public String getItem_desc() { return item_desc; }

    public void setItem_desc(String item_desc) { this.item_desc = item_desc; }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) { this.subcategory_name = subcategory_name; }

    public String getSubcategory_alacarte_price() { return subcategory_alacarte_price; }

    public void setSubcategory_alacarte_price(String subcategory_alacarte_price) { this.subcategory_alacarte_price = subcategory_alacarte_price; }

    public String getSubcategory_meal_price() { return subcategory_meal_price; }

    public void setSubcategory_meal_price(String subcategory_meal_price) { this.subcategory_meal_price = subcategory_meal_price; }

    public int getSubcategory_img() {
        return subcategory_img;
    }

    public void setSubcategory_img(int subcategory_img) { this.subcategory_img = subcategory_img; }
}
