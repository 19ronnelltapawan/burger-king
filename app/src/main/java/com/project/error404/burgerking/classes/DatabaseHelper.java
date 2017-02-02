package com.project.error404.burgerking.classes;

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
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Fname TEXT, " +
                "Lname TEXT, " +
                "Email TEXT Unique, " +
                "Password TEXT, " +
                "History TEXT)");
        db.execSQL("Create Table "+TABLE_NAME2+"(ID INTEGER, " +
                "ItemNo INTEGER, " +
                "Item TEXT, " +
                "Quantity TEXT, " +
                "Drink TEXT, " +
                "GoLarge TEXT, " +
                "Price TEXT, " +
                "ItemImage INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }

    public boolean insertRecord(String fname, String lname, String email, String password) {
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

    public Cursor selectEmailRecords(String id, String email) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT Email FROM "+TABLE_NAME+" WHERE ID != '"+id+"' AND Email = '"+email+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }

    public Cursor selectCurrentData(String email) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Email = '"+email+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }

    public String selectLoginInfo(String email) {
        Cursor cursor = this.getWritableDatabase().query(TABLE_NAME, null, "Email=?", new String[] {email}, null, null, null);

        if (cursor.getCount() < 1)
            return "Email does not exist";
        else {
            cursor.moveToFirst();
            String password = cursor.getString(4);
            return password;
        }
    }

    public void insertItem(int id, String item, String quantity, String drink, String golarge, String price, int itemimage) {
        SQLiteDatabase saveCmd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("ItemNo", selectLastItemNo(id));
        contentValues.put("Item", item);
        contentValues.put("Quantity", quantity);
        contentValues.put("Drink", drink);
        contentValues.put("GoLarge", golarge);
        contentValues.put("Price", price);
        contentValues.put("ItemImage", itemimage);
        saveCmd.insert("current_items", null, contentValues);
    }

    public int selectLastItemNo(int id) {
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

    public Cursor checkIfSameOrder(int id, String item, String drink, String golarge) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT * FROM current_items WHERE ID='"+id+"' AND Item='"+item+"' AND Drink='"+drink+"' AND GoLarge='"+golarge+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }

    public Cursor selectItems(int id) {
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

    public Cursor selectHistory(int id) {
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
        db.execSQL("UPDATE current_items SET ItemNo = CASE WHEN ItemNo < '"+m_Text+"' THEN ItemNo = ItemNo ELSE ItemNo - 1 END WHERE ID ='"+id+"'");
    }

    public Cursor selectItemInCart(int id, String itemno) {
        SQLiteDatabase viewRec = this.getWritableDatabase();
        Cursor resultSet;
        resultSet = viewRec.rawQuery("SELECT ItemNo, Item FROM current_items WHERE ID='"+id+"' AND ItemNo='"+itemno+"'", null);
        resultSet.moveToFirst();
        return resultSet;
    }
}
