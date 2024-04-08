package com.example.phonedb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class Phone {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "brand")
    private String brand;

    @NonNull
    @ColumnInfo(name = "model")
    private String model;

    @NonNull
    @ColumnInfo(name = "android_version")
    private double androidVersion;

    @NonNull
    @ColumnInfo(name = "website")
    private String website;

    public void setId(long id) {
        this.id = id;
    }

    public void setBrand(@NonNull String brand) {
        this.brand = brand;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    public void setAndroidVersion(double androidVersion) {
        this.androidVersion = androidVersion;
    }

    public void setWebsite(@NonNull String website) {
        this.website = website;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getBrand() {
        return brand;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public double getAndroidVersion() {
        return androidVersion;
    }

    @NonNull
    public String getWebsite() {
        return website;
    }

    public Phone(String brand, String model, double androidVersion, String website) {
        this.brand = brand;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }

    public Phone(long id, String brand, String model, double androidVersion, String website) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }

    public Phone() {

    }
}
