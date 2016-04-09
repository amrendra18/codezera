package com.amrendra.codefiesta.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.amrendra.codefiesta.db.DBContract;
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

    int show = 1;

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
        dest.writeInt(show);
    }

    protected Website(Parcel in) {
        id = in.readInt();
        name = in.readString();
        show = in.readInt();
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

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "Website[" + getName() + " id:" + getId() + " show:" + getShow() + "]\n";
    }

    public ContentValues getContentValues() {
        ContentValues value = new ContentValues();
        value.put(DBContract.ResourceEntry.RESOURCE_ID_COL, getId());
        value.put(DBContract.ResourceEntry.RESOURCE_NAME_COL, getName());
        value.put(DBContract.ResourceEntry.RESOURCE_SHOW_COL, getShow());
        return value;
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
