package com.piyushmaheswari.photogalleryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUpdatePhotosActivity extends AppCompatActivity {

    CircleImageView addPhoto;
    EditText addName,addDate;
    Button save;

    private ActionBar actionBar;


    public static final int CAMERA_REQUEST=100;
    public static final int STORAGE_REQUEST=101;
    public static final int IMAGE_PICK_CAMERA=102;
    public static final int IMAGE_PICK_GALLERY=103;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_photos);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Add/Update Photos");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        addDate=findViewById(R.id.dateEt);
        addName=findViewById(R.id.nameEt);
        addPhoto=findViewById(R.id.photoAdded);
        save=findViewById(R.id.saveBtn);


        cameraPermissions=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};




        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void imagePickDialog() {
        String options[]={"Camera","Gallery"};

        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Pick Image from");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0)
                {
                    if(!checkCameraPermissions())
                    {
                        requestCamera();
                    }
                    else
                    {
                        pickFromCamera();
                    }
                }
                else if(i==1)
                {
                    if(!checkStoragePermission())
                    {
                        requestStorage();
                    }
                    else
                    {
                        pickFromGallery();
                    }
                }
            }
        });

        builder.create().show();
    }

    private void pickFromGallery() {
    }

    private void pickFromCamera() {
    }

    private boolean checkStoragePermission()
    {
        boolean result= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)== (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStorage()
    {
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST);
    }

    private boolean checkCameraPermissions()
    {
        boolean result= ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)== (PackageManager.PERMISSION_GRANTED);
        boolean result1= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)== (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCamera()
    {
        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case CAMERA_REQUEST:{

                if(grantResults.length>0)
                {
                    boolean cameraAccepted=grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted=grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted)
                    {
                        pickFromCamera();
                    }
                    else
                    {
                        Toast.makeText(this, "CAmera and storage permissions required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case STORAGE_REQUEST:{

                if(grantResults.length>0)
                {
                    boolean storageAccepted=grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted)
                        pickFromGallery();
                    else
                        Toast.makeText(this, "Storage Permissions required", Toast.LENGTH_SHORT).show();
                }
            }
            break;

        }
    }
}
