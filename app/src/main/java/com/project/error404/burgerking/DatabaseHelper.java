package com.project.error404.burgerking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

/**
 * Created by Ronnell on 12/10/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BurgerKing.db";
    public static final String TABLE_NAME = "users_table";
    public static final String TABLE_NAME2 = "current_items";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, Fname TEXT, Lname TEXT, Email TEXT Unique, Password TEXT, History TEXT)");
        db.execSQL("Create Table "+TABLE_NAME2+"(ID INTEGER, ItemNo INTEGER, Item TEXT, Quantity TEXT, Drink TEXT, GoLarge TEXT, Price TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }

    //Inserting data
    public boolean SaveRecord(String fname, String lname, String email, String password) {
        SQLiteDatabase saveCmd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Fname", fname);
        contentValues.put("Lname", lname);
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        long result = saveCmd.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Update data
    public boolean updateRecord(String id, String fname, String lname, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("Fname", fname);
        contentValues.put("Lname", lname);
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        long result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        if (result == -1)
            return false;
        else
            return true;
    }

    //Select all users
    public Cursor viewEmailRecords(String email) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT Email FROM "+TABLE_NAME+" WHERE Email='"+email+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }

    //Get data from current user
    public Cursor getCurrentData(String email) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Email='"+email+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }

    //Check login info
    public String getLoginInfo(String email) {
        Cursor cursor = this.getWritableDatabase().query(TABLE_NAME, null, "Email=?", new String[] {email}, null, null, null);
        if (cursor.getCount() < 1)
            return "Email does not exist";
        cursor.moveToFirst();
        String password = cursor.getString(4);
        return password;
    }

    //Inserting item
    public void InsertItem(int id, String item, String quantity, String drink, String golarge, String price) {
        SQLiteDatabase saveCmd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("ItemNo", getLastItemNo(id));
        contentValues.put("Item", item);
        contentValues.put("Quantity", quantity);
        contentValues.put("Drink", drink);
        contentValues.put("GoLarge", golarge);
        contentValues.put("Price", price);
        saveCmd.insert("current_items", null, contentValues);
    }

    //Updating item
    public void updateItem(int id, int itemno, String item, String quantity, String drink, String golarge, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("ItemNo", itemno);
        contentValues.put("Item", item);
        contentValues.put("Quantity", quantity);
        contentValues.put("Drink", drink);
        contentValues.put("GoLarge", golarge);
        contentValues.put("Price", price);
        db.update("current_items", contentValues, "ItemNo = ?", new String[] {String.valueOf(itemno)});
    }

    //Select last item no.
    public int getLastItemNo(int id) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT IFNULL(MAX(ItemNo), 0) + 1 FROM current_items WHERE ID='"+id+"'", null);

        if (resultSet != null && resultSet.moveToFirst()) {
            int itemno=Integer.parseInt(resultSet.getString(0));
            return itemno;
        }
        else
            return 1;
    }

    //Check if the item is already inserted
    public Cursor checkIfSame(int id, String item, String drink, String golarge) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT * FROM current_items WHERE ID='"+id+"' AND Item='"+item+"' AND Drink='"+drink+"' AND GoLarge='"+golarge+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }

    //Select all items
    public Cursor ViewItems(int id) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT * FROM current_items WHERE ID='"+id+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }

    public void updateHistory(int id, String history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("History", history);
        db.update("users_table", contentValues, "ID = ?", new String[] {String.valueOf(id)});
    }

    public Cursor getHistory(int id) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT History FROM users_table WHERE ID='"+id+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }

    public void deleteCart(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("current_items", "ID = ?",new String[] {String.valueOf(id)});
    }

    public Integer deleteItemInCart(int id, String itemno) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("current_items", "ID = ? AND ItemNo = ?",new String[] {String.valueOf(id), itemno});
    }

    public void updateItemsAfterDelete(int id, int m_Text) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE current_items SET ItemNo = CASE WHEN ItemNo < '"+m_Text+"' THEN ItemNo = ItemNo ELSE ItemNo - 1 END WHERE ID ='"+id+"'";
        db.execSQL(strSQL);
    }

    public Cursor selectItemInCart(int id, String itemno) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT ItemNo, Item FROM current_items WHERE ID='"+id+"' AND ItemNo='"+itemno+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }
}
