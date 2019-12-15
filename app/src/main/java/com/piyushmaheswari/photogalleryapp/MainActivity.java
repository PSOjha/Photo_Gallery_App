package com.piyushmaheswari.photogalleryapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.piyushmaheswari.photogalleryapp.Adapter.ModelAdapter;
import com.piyushmaheswari.photogalleryapp.Database.Constants;
import com.piyushmaheswari.photogalleryapp.Database.SqLiteHelper;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;

    private SqLiteHelper sqLiteHelper;

    ActionBar actionBar;


    String orderByNewest=Constants.C_ADDED_TIMESTAMP+" DESC";
    String orderByOldest=Constants.C_ADDED_TIMESTAMP+" ASC";
    String orderByTitleAsc=Constants.C_NAME+" ASC";
    String orderByTitleDesc=Constants.C_NAME+" DESC";

    String currentOrderBy=orderByNewest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar=getSupportActionBar();
        actionBar.setTitle("All Records");
        sqLiteHelper=new SqLiteHelper(this);

        fab=findViewById(R.id.addPhotos);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AddUpdatePhotosActivity.class);
                i.putExtra("isEditMode",false);
                startActivity(i);
            }
        });

        recyclerView=findViewById(R.id.records);
        loadRecords(orderByNewest);

    }

    private void loadRecords(String orderBy) {
        currentOrderBy=orderBy;
        ModelAdapter modelAdapter=new ModelAdapter(MainActivity.this,
                sqLiteHelper.getAllRecords(orderBy));

        recyclerView.setAdapter(modelAdapter);
        actionBar.setSubtitle("Total: "+sqLiteHelper.getRecordsCount());

    }

    private void searchRecords(String query) {
        ModelAdapter modelAdapter=new ModelAdapter(MainActivity.this,
                sqLiteHelper.searchRecords(query));

        recyclerView.setAdapter(modelAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords(currentOrderBy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecords(newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.action_sort)
        {

        }

        return super.onOptionsItemSelected(item);
    }
}
