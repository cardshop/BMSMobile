package com.example.wesley.mobileapp_bms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley Martins on 19.01.14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    Context context;

    private static final String DATABASE_NAME = "contactManager",
            TABLE_CONTACTS = "contacts",
            KEY_ID = "id",
            KEY_DESC = "desc",
            KEY_SIZE = "size",
            KEY_PRICE = "price",
            KEY_QUANTITY = "quantity",
            KEY_LOCATION = "location",
            KEY_TIME = "time",
            KEY_TYPE = "type",
            KEY_IMAGEURI = "imageUri";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DESC + " TEXT," + KEY_SIZE + " TEXT," + KEY_PRICE + " TEXT," + KEY_QUANTITY + " TEXT," + KEY_LOCATION + " TEXT,"+ KEY_TIME + " TEXT," + KEY_TYPE + " TEXT,"+ KEY_IMAGEURI + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }

    public void createContact(Info info) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DESC, info.getDesc());
        values.put(KEY_SIZE, info.getSize());
        values.put(KEY_PRICE, info.getPrice());
        values.put(KEY_QUANTITY, info.getquantity());
        values.put(KEY_LOCATION, info.getLocation());
        values.put(KEY_TIME, info.getTime());
        values.put(KEY_TYPE, info.getType());
        values.put(KEY_IMAGEURI, info.getImageURI().toString());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Info getContact(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_DESC, KEY_SIZE, KEY_PRICE, KEY_QUANTITY, KEY_LOCATION, KEY_IMAGEURI }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Info info = new Info(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),Uri.parse(cursor.getString(8)));
        db.close();
        cursor.close();
        return info;
    }

    public void deleteContact(Info info) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + "=?", new String[] { String.valueOf(info.getId()) });
        db.close();
    }

    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateContact(Info info) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DESC, info.getDesc());
        values.put(KEY_SIZE, info.getSize());
        values.put(KEY_PRICE, info.getPrice());
        values.put(KEY_QUANTITY, info.getquantity());
        values.put(KEY_LOCATION, info.getLocation());
        values.put(KEY_TIME, info.getTime());
        values.put(KEY_TYPE, info.getType());
        values.put(KEY_IMAGEURI, info.getImageURI().toString());

        int rowsAffected = db.update(TABLE_CONTACTS, values, KEY_ID + "=?", new String[] { String.valueOf(info.getId()) });
        db.close();

        return rowsAffected;
    }

    public List<Info> getAllContacts() {
        List<Info> infos = new ArrayList<Info>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);

        if (cursor.moveToFirst()) {
            do {
                infos.add(new Info(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getString(6), cursor.getString(7), Uri.parse(cursor.getString(8))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return infos;
    }
}
