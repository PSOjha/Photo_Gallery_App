package com.piyushmaheswari.photogalleryapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqLiteHelper extends SQLiteOpenHelper {

    public SqLiteHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertPhotos(String name,String image, String date, String addedTime, String updatedTime)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        //adding Data
        contentValues.put(Constants.C_NAME,name);
        contentValues.put(Constants.C_IMAGE,image);
        contentValues.put(Constants.C_DATE,date);
        contentValues.put(Constants.C_ADDED_TIMESTAMP,addedTime);
        contentValues.put(Constants.C_UPDATED_TIMESTAMP,updatedTime);

        long id=db.insert(Constants.TABLE_NAME,null,contentValues);

        db.close();

        return id;

    }
}
