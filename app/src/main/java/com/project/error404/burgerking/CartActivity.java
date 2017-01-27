package com.project.error404.burgerking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//TEST FOR COMMIT

public class CartActivity extends AppCompatActivity {

    TextView list;
    EditText input;

    int current_id;
    double total=0;

    SharedPreferences myPrefs;
    StringBuffer buffer;

    AlertDialog.Builder builder;
    myClass mc = new myClass();
    DatabaseHelper myDB = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        list = (TextView) findViewById(R.id.textView9);
        list.setMovementMethod(new ScrollingMovementMethod());

        myPrefs = getSharedPreferences(mc.getPrefsName(), 0);
        current_id = myPrefs.getInt("id", 0);

        viewItems();
    }

    public void viewItems() {
        Cursor res = myDB.ViewItems(current_id);
        buffer = new StringBuffer();

        if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(),"No items found",Toast.LENGTH_LONG).show();
        }
        else {
            do {
                buffer.append("Item No.: " + res.getString(1) + "\n");
                buffer.append("Item: " + res.getString(2) + "\n");
                buffer.append("Quantity: " + res.getString(3) + "\n");
                //if (!res.getString(2).equals("Hamburger (Ala carte-PHP 40)")) {
                buffer.append("Drink: " + res.getString(4) + "\n");
                buffer.append("Go Large: " + res.getString(5) + "\n");
                //}
                buffer.append("Price: " + res.getString(6) + "\n\n");
                total=mc.computeTotal(Double.parseDouble(res.getString(6)));
            } while (res.moveToNext());

            buffer.append("Total Price: " + total);
            list.setText("/*Start of Order*/\nList of Items:\n"+buffer);
        }
    }

    public void onClickPurchase(View view) {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Total Price: " + total);

        //Set up the input
        input = new EditText(this);
        //Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        //Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Double m_Text = Double.parseDouble(input.getText().toString());
                if (m_Text>=total) {
                    Cursor res = myDB.getHistory(current_id);
                    if (res.getString(0)==null)
                        myDB.updateHistory(current_id, list.getText().toString()+"\nCash: "+m_Text+"\nChange: "+mc.computeChange(m_Text, total)+"\n/*End of Order*/\n\n");
                    else
                        myDB.updateHistory(current_id, res.getString(0)+list.getText().toString()+"\nCash: "+m_Text+"\nChange: "+mc.computeChange(m_Text, total)+"\n/*End of Order*/\n\n");
                    myDB.deleteCart(current_id);
                    Toast.makeText(getApplicationContext(), "Success! Items added to history!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MasterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter correct amount", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void onClickDelete(View view) {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Item No.");

        //Set up the input
        input = new EditText(this);
        //Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        //Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int m_Text = Integer.parseInt(input.getText().toString());
                int deletedRows = myDB.deleteItemInCart(current_id, String.valueOf(m_Text));
                if (deletedRows>0) {
                    int lastitemno = myDB.getLastItemNo(current_id);
                    if (m_Text<lastitemno) {
                        myDB.updateItemsAfterDelete(current_id, m_Text);
                    }
                    total=0; mc.total=0;
                    Cursor res = myDB.ViewItems(current_id);
                    if (res.getCount() == 0) {
                        Toast.makeText(getApplicationContext(),"All item(s) deleted, going back",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MasterActivity.class));
                    }
                    else {
                        viewItems();
                        list.setMovementMethod(new ScrollingMovementMethod());
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Item no. not found", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void onClickEdit(View view) {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Item No.");

        //Set up the input
        input = new EditText(this);
        //Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        //Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int m_Text = Integer.parseInt(input.getText().toString());
                Cursor hasRow = myDB.selectItemInCart(current_id, String.valueOf(m_Text));
                if (hasRow.getCount()>0) {
                    //builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Edit quantity for:\n"+hasRow.getString(0)+"\nQuantity: "+hasRow.getString(1));
                    //input = new EditText(getApplicationContext());
                    //builder.setView(input);

                    //Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter a valid item no", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MasterActivity.class));
        finish();
    }
}
