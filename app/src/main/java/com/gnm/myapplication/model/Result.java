package com.gnm.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result implements Parcelable {

    String value;
    String message;
    @SerializedName("result")
    private List<Barang> mResultBarang;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mResultBarang);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.mResultBarang = in.createTypedArrayList(Barang.CREATOR);
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public List<Barang> getmResultBarang() {
        return mResultBarang;
    }

    public void setmResultBarang(List<Barang> mResultSoal) {
        this.mResultBarang = mResultSoal;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
