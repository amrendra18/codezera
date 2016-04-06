package com.amrendra.codefiesta.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amrendra Kumar on 06/04/16.
 */
public class Website implements Parcelable {
    int id;
    String name;

    public Website() {

    }

    public static final Creator<Website> CREATOR = new Creator<Website>() {
        @Override
        public Website createFromParcel(Parcel in) {
            return new Website(in);
        }

        @Override
        public Website[] newArray(int size) {
            return new Website[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    protected Website(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Website[" + getName() + " id:" + getId() + "]\n";
    }

    public static final class Response {

        @Expose
        @SerializedName("meta")
        public Meta meta;

        @Expose
        @SerializedName("objects")
        public List<Website> websites = new ArrayList<>();
    }
}
