package com.piyushmaheswari.photogalleryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.piyushmaheswari.photogalleryapp.Database.SqLiteHelper;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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

    private String name,date;
    private SqLiteHelper sqLiteHelper;

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


        sqLiteHelper=new SqLiteHelper(this);

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
                addData();
            }
        });

    }

    private void addData() {
        name=""+addName.getText().toString().trim();
        date=""+addDate.getText().toString().trim();

        String timeStamp=""+System.currentTimeMillis();

        long id=sqLiteHelper.insertPhotos(
                ""+name,
                ""+imageUri,
                ""+date,
                ""+timeStamp,
                ""+timeStamp);

        Toast.makeText(this, "Record added against id: "+id, Toast.LENGTH_SHORT).show();
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

        Intent galleryIntent=new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY);
    }

    private void pickFromCamera() {

        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Description");

        imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK)
        {
            if(requestCode==IMAGE_PICK_GALLERY)
            {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if(requestCode==IMAGE_PICK_CAMERA)
            {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }

            else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            {
                CropImage.ActivityResult result=CropImage.getActivityResult(data);
                if(resultCode == RESULT_OK){
                    Uri resultUri=result.getUri();
                    imageUri=resultUri;

                    addPhoto.setImageURI(resultUri);
                }
                else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
                {
                    Exception exception=result.getError();
                    Toast.makeText(this, "Error"+exception, Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
