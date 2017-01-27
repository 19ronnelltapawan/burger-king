package com.project.error404.burgerking;

/**
 * Created by Ronnell on 12/10/2016.
 */

public class myClass {

    double price=0, golarge=0, quantity=0, total=0;

    public String getPrefsName() { return "myPrefs"; }

    public int updateQuantity(int currentqty, int previousqty) { return currentqty+previousqty; }

    public double updatePrice(double currentprice, double previousprice) { return currentprice+previousprice; }

    public double computePrice() {return (price+golarge)*quantity; }

    public double computeChange(double cash, double amount) { return cash-amount; }

    public double computeTotal(double previoustotal) { return total+=previoustotal; }
}
