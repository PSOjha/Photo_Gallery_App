package com.piyushmaheswari.photogalleryapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.piyushmaheswari.photogalleryapp.Model.ModelRecord;

import java.util.ArrayList;

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

    public ArrayList<ModelRecord> getAllRecords(String orderBy) {
        ArrayList<ModelRecord> records=new ArrayList<>();

        String selectQuery="SELECT * FROM "+Constants.TABLE_NAME+ " ORDER BY "+orderBy;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DATE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP))
                );

                records.add(modelRecord);
            }while (cursor.moveToNext());
        }

        db.close();
        return records;
    }

    public ArrayList<ModelRecord> searchRecords(String query) {
        ArrayList<ModelRecord> records=new ArrayList<>();

        String selectQuery="SELECT * FROM "+Constants.TABLE_NAME+ " WHERE "+Constants.C_NAME+ " LIKE '%" + query +"%'";

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DATE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP))
                );

                records.add(modelRecord);
            }while (cursor.moveToNext());
        }

        db.close();
        return records;
    }

    public int getRecordsCount()
    {
        String countQuery="SELECT * FROM "+Constants.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);

        int count=cursor.getCount();
        cursor.close();
        return count;
    }

}
