package com.gnm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Barang implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("nama")
    private String mNama;

    @SerializedName("stok")
    private int mStok;

    @SerializedName("image")
    private String mImage;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeString(this.mNama);
        dest.writeValue(this.mStok);
        dest.writeString(this.mImage);
    }

    public Barang(Parcel in) {
        this.mId = (Integer) in.readValue(Long.class.getClassLoader());
        this.mNama = in.readString();
        this.mStok = (Integer) in.readValue(Long.class.getClassLoader());
        this.mImage = in.readString();
    }

    public static final Creator<Barang> CREATOR = new Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel in) {
            return new Barang(in);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
        }
    };


    public Barang(int id, String nama, int stok, String image) {
        mId = id;
        mNama = nama;
        mStok = stok;
        mImage = image;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmNama() {
        return mNama;
    }

    public void setmNama(String mNama) {
        this.mNama = mNama;
    }

    public int getmStok() {
        return mStok;
    }

    public void setmStok(int mStok) {
        this.mStok = mStok;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}
