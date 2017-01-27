package com.project.error404.burgerking;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    EditText quantity;
    TextView desc, current_price;
    Spinner drink;
    CheckBox golarge;
    Button add;
    RadioButton opt1, opt2;

    String selected_drink="", golarge_checked="", item="";
    int current_id;
    double alacarteprice, mealprice, golargeprice;

    Bundle b;
    SharedPreferences myPrefs;

    myClass mc = new myClass();
    DatabaseHelper myDB = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        quantity = (EditText) findViewById(R.id.editText1);
        drink = (Spinner) findViewById(R.id.spinner1);
        desc = (TextView) findViewById(R.id.textView1);
        golarge = (CheckBox) findViewById(R.id.checkBox);
        opt1 = (RadioButton) findViewById(R.id.radioButton1);
        opt2 = (RadioButton) findViewById(R.id.radioButton2);
        current_price = (TextView) findViewById(R.id.textView7);
        add = (Button) findViewById(R.id.button11);

        myPrefs = getSharedPreferences(mc.getPrefsName(), 0);
        current_id = myPrefs.getInt("id", 0);

        Initialize();

        b = getIntent().getExtras();
        switch (b.getInt("choice")) {
            case 1:
                desc.setText(R.string.hamburger_desc);
                opt1.setText(R.string.hamburger_alacarte);
                opt2.setText(R.string.hamburger_meal);
                break;
            case 2:
                desc.setText(R.string.cheeseburger_desc);
                opt1.setText(R.string.cheeseburger_alacarte);
                opt2.setText(R.string.cheeseburger_meal);
                break;
            case 3:
                desc.setText(R.string.doublecheeseburger_desc);
                opt1.setText(R.string.doublecheeseburger_alacarte);
                opt2.setText(R.string.doublecheeseburger_meal);
                break;
            case 4:
                desc.setText(R.string.baconcheeseburger_desc);
                opt1.setText(R.string.baconcheeseburger_alacarte);
                opt2.setText(R.string.baconcheeseburger_meal);
                break;
            case 5:
                desc.setText(R.string.bacondoublecheeseburger_desc);
                opt1.setText(R.string.bacondoublecheeseburger_alacarte);
                opt2.setText(R.string.bacondoublecheeseburger_meal);
                break;
            case 6:
                desc.setText(R.string.baconcheesewhopper_desc);
                opt1.setText(R.string.baconcheesewhopper_alacarte);
                opt2.setText(R.string.baconcheesewhopper_meal);
                break;
            case 7:
                desc.setText(R.string.doublewhopper_desc);
                opt1.setText(R.string.doublewhopper_alacarte);
                opt2.setText(R.string.doublewhopper_meal);
                break;
            case 8:
                desc.setText(R.string.whopper_desc);
                opt1.setText(R.string.whopper_alacarte);
                opt2.setText(R.string.whopper_meal);
                break;
            case 9:
                desc.setText(R.string.whopperjr_desc);
                opt1.setText(R.string.whopperjr_alacarte);
                opt2.setText(R.string.whopperjr_meal);
                break;
            default:
                startActivity(new Intent(getApplicationContext(), MasterActivity.class));
                finish();
        }

        alacarteprice=b.getDouble("alacarteprice");
        mealprice=b.getDouble("mealprice");
        golargeprice=b.getDouble("golargeprice");

        drink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
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
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MasterActivity.class));
        finish();
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
            quantity.setError("Please enter a valid quantity");
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
        Cursor res = myDB.ViewItems(current_id);

        if (res.getCount() == 0)
            Toast.makeText(getApplicationContext(),"No items found",Toast.LENGTH_LONG).show();
        else
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
    }

    private void Initialize() {
        add.setEnabled(false);
        quantity.setEnabled(false);
        quantity.setText("");
        drink.setEnabled(false);
        golarge.setEnabled(false);
        golarge.setChecked(false);
        mc.price=0;
        mc.golarge=0;
        mc.quantity=0;
        item="";
        selected_drink="";
        golarge_checked="";
        drink.setSelection(0);
        current_price.setText("No Item Selected");
    }

    public void CheckIfSame() {
        Cursor res = myDB.checkIfSame(current_id, item, selected_drink, golarge_checked);
        if (res.getCount()==0)
            myDB.InsertItem(current_id, item, quantity.getText().toString(), selected_drink, golarge_checked, String.valueOf(mc.computePrice()));
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
