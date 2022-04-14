package com.example.login22_01_19_h1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

//    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context) {
        super(context,"UserData",null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(userID TEXT primary key,name TEXT,password PASSWORD,number NUMBER)");
        DB.execSQL("create Table cars(carname TEXT,cartype NUMBER,carid NUMBER primary key)");
        DB.execSQL("create Table Test(carName TEXT,carType NUMBER,carCompany TEXT,carid NUMBER primary key)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserDetails");
        DB.execSQL("drop Table if exists cars");
        DB.execSQL("drop Table if exists Test");
    }

}