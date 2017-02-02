package com.project.error404.burgerking.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.error404.burgerking.classes.DatabaseHelper;
import com.project.error404.burgerking.R;
import com.project.error404.burgerking.classes.myClass;
import com.rengwuxian.materialedittext.MaterialEditText;

public class OrderActivity extends AppCompatActivity {

    Button add;
    CheckBox golarge;
    ImageView logo;
    MaterialEditText quantity;
    RadioButton opt1, opt2;
    Spinner drink;
    TextView desc, current_price;

    String selected_drink, golarge_checked, item, alacarte, meal;
    int current_id;
    double alacarteprice, mealprice, golargeprice;

    Bundle b;
    Cursor res;
    DatabaseHelper myDB;
    Intent i;
    myClass mc;
    SharedPreferences myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        quantity = (MaterialEditText) findViewById(R.id.editText1);
        drink = (Spinner) findViewById(R.id.spinner1);
        desc = (TextView) findViewById(R.id.textView1);
        golarge = (CheckBox) findViewById(R.id.checkBox);
        opt1 = (RadioButton) findViewById(R.id.radioButton1);
        opt2 = (RadioButton) findViewById(R.id.radioButton2);
        current_price = (TextView) findViewById(R.id.textView7);
        add = (Button) findViewById(R.id.button11);
        logo = (ImageView) findViewById(R.id.img_order);

        mc = new myClass();
        myDB = new DatabaseHelper(this);
        myPrefs = getSharedPreferences(mc.getPrefsName(), 0);
        current_id = myPrefs.getInt("id", 0);

        alacarte = getString(R.string.alacarte);
        meal = getString(R.string.meal);

        Initialize();

        b = getIntent().getExtras();
        switch (b.getInt("choice")) {
            case 1:
                desc.setText(R.string.hamburger_desc);
                opt1.setText(alacarte+ " - " +getString(R.string.hamburger_alacarte_price));
                opt2.setText(meal+ " - " +getString(R.string.hamburger_meal_price));
                break;
            case 2:
                desc.setText(R.string.cheeseburger_desc);
                opt1.setText(alacarte+ " - " +getString(R.string.cheeseburger_alacarte_price));
                opt2.setText(meal+ " - " +getString(R.string.cheeseburger_meal_price));
                break;
            case 3:
                desc.setText(R.string.doublecheeseburger_desc);
                opt1.setText(alacarte+ " - " +getString(R.string.doublecheeseburger_alacarte_price));
                opt2.setText(meal+ " - " +getString(R.string.doublecheeseburger_meal_price));
                break;
            case 4:
                desc.setText(R.string.baconcheeseburger_desc);
                opt1.setText(alacarte+ " - " +getString(R.string.baconcheeseburger_alacarte_price));
                opt2.setText(meal+ " - " +getString(R.string.baconcheeseburger_meal_price));
                break;
            case 5:
                desc.setText(R.string.bacondoublecheeseburger_desc);
                opt1.setText(alacarte+ " - " +getString(R.string.doublecheeseburger_alacarte_price));
                opt2.setText(meal+ " - " +getString(R.string.doublecheeseburger_meal_price));
                break;
            case 6:
                desc.setText(R.string.baconcheesewhopper_desc);
                opt1.setText(alacarte+ " - " +getString(R.string.baconcheesewhopper_alarcarte_price));
                opt2.setText(meal+ " - " +getString(R.string.baconcheesewhopper_meal_price));
                break;
            case 7:
                desc.setText(R.string.doublewhopper_desc);
                opt1.setText(alacarte+ " - " +getString(R.string.doublewhopper_alacarte_price));
                opt2.setText(meal+ " - " +getString(R.string.doublewhopper_meal_price));
                break;
            case 8:
                desc.setText(R.string.whopper_desc);
                opt1.setText(alacarte+ " - " +getString(R.string.whopper_alacarte_price));
                opt2.setText(meal+ " - " +getString(R.string.whopper_meal_price));
                break;
            case 9:
                desc.setText(R.string.whopperjr_desc);
                opt1.setText(alacarte+ " - " +getString(R.string.whopperjr_alacarte_price));
                opt2.setText(meal+ " - " +getString(R.string.whopperjr_meal_price));
                break;
            default:
                startActivity(new Intent(getApplicationContext(), MasterActivity.class));
                finish();
        }

        logo.setImageResource(b.getInt("burger"));

        alacarteprice = b.getDouble("alacarteprice");
        mealprice = b.getDouble("mealprice");
        golargeprice = b.getDouble("golargeprice");

        drink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Choose drink"))
                    selected_drink="None";
                else if (selectedItem.equals("Coke"))
                    selected_drink="Coke";
                else if (selectedItem.equals("Sprite"))
                    selected_drink="Sprite";
                else if (selectedItem.equals("Royal"))
                    selected_drink="Royal";
                else if (selectedItem.equals("Iced tea"))
                    selected_drink="Ice tea";
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClickCheckbox(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkBox:
                if (checked) {
                    golarge_checked="Yes";
                    mc.golarge = golargeprice;
                }
                else {
                    golarge_checked="No";
                    mc.golarge=0;
                }
                break;
        }

        current_price.setText("Price: "+mc.computePrice());
    }

    public void onClickRadio(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButton1: //alacarte
                if (checked) {
                    drink.setEnabled(false);
                    golarge.setEnabled(false);
                    mc.price = alacarteprice;
                    if (golarge.isChecked()) {
                        golarge.setChecked(false);
                        mc.price -= 40;
                    }

                    item = b.getString("itemname1");
                }
                break;
            case R.id.radioButton2: //meal
                if (checked) {
                    drink.setEnabled(true);
                    golarge.setEnabled(true);
                    mc.price = mealprice;

                    item = b.getString("itemname2");
                }
                break;
        }

        quantity.setEnabled(true);
        add.setEnabled(true);
        current_price.setText("Price: "+mc.computePrice());
    }

    public void onClickQuantity(View view) {
        if (TextUtils.isEmpty(quantity.getText().toString()) || Integer.parseInt(quantity.getText().toString())==0) {
            quantity.setError("Invalid quantity");
            add.setEnabled(false);
        }
        else {
            mc.quantity=Double.parseDouble(quantity.getText().toString());
            current_price.setText("Price: "+mc.computePrice());
            add.setEnabled(true);
        }
    }

    public void onClickAdd(View view) {
        if (TextUtils.isEmpty(quantity.getText().toString()))
            quantity.setError("Invalid quantity");
        else {
            mc.quantity = Double.parseDouble(quantity.getText().toString());

            String current_drink = drink.getSelectedItem().toString();
            if (current_drink.equals("Choose drink"))
                selected_drink = "None";
            else if (current_drink.equals("Coke"))
                selected_drink = "Coke";
            else if (current_drink.equals("Sprite"))
                selected_drink = "Sprite";
            else if (current_drink.equals("Royal"))
                selected_drink = "Royal";
            else if (current_drink.equals("Iced tea"))
                selected_drink = "Ice tea";

            if (golarge.isChecked())
                golarge_checked = "Yes";
            else
                golarge_checked = "No";

            if (opt1.isChecked()) {
                if (Integer.parseInt(quantity.getText().toString()) > 0) {
                    item = b.getString("itemname1");
                    CheckIfSame();
                    Initialize();
                } else
                    Toast.makeText(getApplicationContext(), "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            } else if (opt2.isChecked()) {
                if (!selected_drink.equals("None") && Integer.parseInt(quantity.getText().toString()) > 0) {
                    item = b.getString("itemname2");
                    CheckIfSame();
                    Initialize();
                } else
                    Toast.makeText(getApplicationContext(), "Please check all of the requirements", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickViewItems(View view) {
        res = myDB.selectItems(current_id);

        if (res.getCount() == 0)
            Toast.makeText(getApplicationContext(),"No items found",Toast.LENGTH_LONG).show();
        else {
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    private void Initialize() {
        add.setEnabled(false);
        quantity.setEnabled(false);
        quantity.setText("");
        drink.setEnabled(false);
        drink.setSelection(0);
        golarge.setEnabled(false);
        golarge.setChecked(false);
        golarge_checked = "";
        mc.price = 0;
        mc.golarge = 0;
        mc.quantity = 0;
        item = "";
        selected_drink = "";
        current_price.setText("Price: 0.0");
    }

    public void CheckIfSame() {
        Cursor res = myDB.checkIfSameOrder(current_id, item, selected_drink, golarge_checked);
        if (res.getCount()==0)
            myDB.insertItem(current_id, item, quantity.getText().toString(), selected_drink, golarge_checked, String.valueOf(mc.computePrice()), b.getInt("burger"));
        else {
            myDB.updateItem(current_id,
                    Integer.parseInt(res.getString(1)),
                    item,
                    String.valueOf(mc.updateQuantity(Integer.parseInt(quantity.getText().toString()), Integer.parseInt(res.getString(3)))),
                    selected_drink,
                    golarge_checked,
                    String.valueOf(mc.updatePrice(mc.computePrice(), Double.parseDouble(res.getString(6)))));
        }
    }
}
