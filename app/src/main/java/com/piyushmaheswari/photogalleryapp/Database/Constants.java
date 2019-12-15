package com.piyushmaheswari.photogalleryapp.Database;

public class Constants {

    public static final String DB_NAME="MY_PHOTOS";

    public static final int DB_VERSION=1;

    public static final String TABLE_NAME="MY_PHOTOS_TABLE";

    public static final String C_ID="ID";
    public static final String C_NAME="NAME";
    public static final String C_IMAGE="IMAGE";
    public static final String C_DATE="DATE";
    public static final String C_ADDED_TIMESTAMP="ADDED_TIME_STAMP";
    public static final String C_UPDATED_TIMESTAMP="UPDATES_TIMESTAMP";


    public static final String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME + "("
            +C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +C_NAME + " TEXT,"
            +C_IMAGE + " TEXT,"
            +C_DATE + " TEXT,"
            +C_ADDED_TIMESTAMP + " TEXT,"
            +C_UPDATED_TIMESTAMP + " TEXT"
            +")";







}
