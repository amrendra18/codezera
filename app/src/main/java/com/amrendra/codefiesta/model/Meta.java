package com.amrendra.codefiesta.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amrendra Kumar on 06/04/16.
 */
public class Meta implements Parcelable {

    int limit;
    String next;
    int offset;
    String previous;

    @SerializedName("total_count")
    long totalCount;


    protected Meta(Parcel in) {
        limit = in.readInt();
        next = in.readString();
        offset = in.readInt();
        previous = in.readString();
        totalCount = in.readLong();
    }

    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        @Override
        public Meta createFromParcel(Parcel in) {
            return new Meta(in);
        }

        @Override
        public Meta[] newArray(int size) {
            return new Meta[size];
        }
    };

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(limit);
        dest.writeString(next);
        dest.writeInt(offset);
        dest.writeString(previous);
        dest.writeLong(totalCount);
    }
}
