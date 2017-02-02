package com.project.error404.burgerking.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.error404.burgerking.classes.DatabaseHelper;
import com.project.error404.burgerking.R;
import com.project.error404.burgerking.classes.RecyclerModel;
import com.project.error404.burgerking.classes.CartAdapter;
import com.project.error404.burgerking.classes.myClass;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    EditText input;

    int currentID, deletedRows, itemID, lastItemNo;
    double inputCash, total;
    String itemDesc;
    StringBuffer items;

    AlertDialog.Builder builder;
    ArrayList list;
    CartAdapter adapter;
    Cursor res;
    DatabaseHelper myDB;
    myClass mC;
    RecyclerModel rC;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mC = new myClass();
        myDB = new DatabaseHelper(this);
        myPrefs = getSharedPreferences(mC.getPrefsName(), 0);
        currentID = myPrefs.getInt("id", 0);

        initViews();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void onClickPurchase(View view) {
        buildDialog();
        builder.setTitle("Total amount is: " +total);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
                else {
                    inputCash = Double.parseDouble(input.getText().toString());
                    if (inputCash >= total) {
                        Cursor res = myDB.selectHistory(currentID);
                        if (res.getString(0) == null)
                            myDB.updateHistory(currentID, "/*Start of Order*/\n" + items + "\nCash: " + inputCash + "\nChange: " + mC.computeChange(inputCash, total) + "\n/*End of Order*/");
                        else
                            myDB.updateHistory(currentID, res.getString(0) + "\n\n/*Start of Order*/\n" + items + "\nCash: " + inputCash + "\nChange: " + mC.computeChange(inputCash, total) + "\n/*End of Order*/");
                        myDB.deleteCart(currentID);
                        Toast.makeText(getApplicationContext(), "Success! Items added to history!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MasterActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finishAfterTransition();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Input is smaller than the total price", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }
            }
        });
        builder.show();
    }

    public void onClickDelete(View view) {
        buildDialog();
        builder.setTitle("Input Item No.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemID = Integer.parseInt(input.getText().toString());
                deletedRows = myDB.deleteItemInCart(currentID, String.valueOf(inputCash));
                if (deletedRows > 0) {
                    lastItemNo = myDB.selectLastItemNo(currentID);
                    if (inputCash < lastItemNo)
                        myDB.updateItemsAfterDelete(currentID, itemID);
                    total=0;
                    mC.total=0;
                    res = myDB.selectItems(currentID);
                    if (res.getCount() == 0) {
                        Toast.makeText(getApplicationContext(),"All item(s) deleted, going back",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MasterActivity.class));
                    }
                    else
                        initViews();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Item no. not found", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        });
        builder.show();
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
        adapter = new CartAdapter(getApplicationContext(), list, this);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList prepareData(){
        list = new ArrayList<>();
        items = new StringBuffer();
        res = myDB.selectItems(currentID);
        if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No items found", Toast.LENGTH_LONG).show();
            finishAfterTransition();
        }
        else {
            do {
                rC = new RecyclerModel();
                itemDesc = "Item No.: " + res.getString(1) + "\n" + "Quantity: " + res.getString(3) + "\n" + "Drink: " + res.getString(4) + "\n" + "Go Large: " + res.getString(5) + "\n" + "Price: " + res.getString(6) + "\n";
                total = mC.computeTotal(Double.parseDouble(res.getString(6)));
                rC.setSubcategory_img(Integer.parseInt(res.getString(7)));
                rC.setSubcategory_name(res.getString(2));
                rC.setItem_desc(itemDesc);
                list.add(rC);
                items.append(res.getString(2) + "\n" + itemDesc + "\nTotal: " + total);
            }
            while (res.moveToNext());
        }
        return list;
    }

    private void buildDialog() {
        builder = new AlertDialog.Builder(this);
        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }
}
