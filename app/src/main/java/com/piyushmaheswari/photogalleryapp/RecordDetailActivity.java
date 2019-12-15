package com.piyushmaheswari.photogalleryapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.piyushmaheswari.photogalleryapp.Database.Constants;
import com.piyushmaheswari.photogalleryapp.Database.SqLiteHelper;

import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecordDetailActivity extends AppCompatActivity {

    private SqLiteHelper sqLiteHelper;
    private CircleImageView circleImageView;
    private TextView nameRe,dateRe,addedData,updatedData;

    private ActionBar actionBar;
    private String recordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        sqLiteHelper=new SqLiteHelper(this);

        circleImageView=findViewById(R.id.imageNOtes);
        nameRe=findViewById(R.id.nameRe);
        dateRe=findViewById(R.id.dateRe);
        addedData=findViewById(R.id.addedDateRe);
        updatedData=findViewById(R.id.updatedDateRe);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Record Details");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();
        recordID=intent.getStringExtra("RECORD_ID");

        showRecords();
    }

    private void showRecords() {
        String selectQuery="SELECT * FROM "+ Constants.TABLE_NAME + " WHERE "+Constants.C_ID
                +" =\"" + recordID+"\"";

        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do {
                String id=""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                String name=""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME));
                String image=""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));
                String date=""+cursor.getString(cursor.getColumnIndex(Constants.C_DATE));
                String addedTime=""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP));
                String updatedTime=""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP));

                Calendar calendar=Calendar.getInstance(Locale.getDefault());
                calendar.setTimeInMillis(Long.parseLong(addedTime));
                String timeAdded=""+ DateFormat.format("dd/MM/yyyy hh:mm:aa",calendar);

                Calendar calendar1=Calendar.getInstance(Locale.getDefault());
                calendar.setTimeInMillis(Long.parseLong(updatedTime));
                String timeUpdated=""+ DateFormat.format("dd/MM/yyyy hh:mm:aa",calendar1);


                nameRe.setText(name);
                dateRe.setText(date);
                addedData.setText(timeAdded);
                updatedData.setText(timeUpdated);
                if(circleImageView.equals("null"))
                {
                    circleImageView.setImageResource(R.drawable.ic_action_name_black);
                }
                else
                {
                    circleImageView.setImageURI(Uri.parse(image));
                }


            }while (cursor.moveToNext());
        }

        db.close();
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
