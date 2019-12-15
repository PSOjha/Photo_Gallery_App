package com.piyushmaheswari.photogalleryapp.Model;

public class ModelRecord {

    String id,name,image,date,addedTime,updateTime;

    public ModelRecord(String id, String name, String image, String date, String addedTime, String updateTime) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.date = date;
        this.addedTime = addedTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
