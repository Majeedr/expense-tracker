/**
 * Copyright (c) 2012 Vinayak Solutions Private Limited
 * See the LICENSE file for copying permission.
*/


package com.vinsol.expensetracker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Entry implements Parcelable {

    public String id = null;
    public String amount;
    public String description;
    public String transactionType;
    public String type;
    public String location;
    public String myHash;
    public String idFromServer;
    public Boolean deleted;
    public String updatedAt;
    public String syncBit;
    public Boolean fileUploaded;
    public Boolean fileToDownload;
    public String fileUpdatedAt;

    public Long timeInMillis;
    public String favorite = null;

    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>() {
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    public Entry() {}

    public Entry(Parcel in) {
        id = in.readString();
        amount = in.readString();
        description = in.readString();
        transactionType = in.readString();
        type = in.readString();
        location = in.readString();
        favorite = in.readString();
        timeInMillis = in.readLong();
        myHash = in.readString();
        idFromServer = in.readString();
        deleted = in.readByte() == 1;
        updatedAt = in.readString();
        syncBit = in.readString();
        fileUploaded = in.readByte() == 1;
        fileToDownload = in.readByte() == 1;
        fileUpdatedAt = in.readString();
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(amount);
        dest.writeString(description);
        dest.writeString(transactionType);
        dest.writeString(type);
        dest.writeString(location);
        dest.writeString(favorite);
        dest.writeLong(timeInMillis);
        dest.writeString(myHash);
        dest.writeString(idFromServer);
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeString(updatedAt);
        dest.writeString(syncBit);
        dest.writeByte((byte) (fileUploaded ? 1 : 0));
        dest.writeByte((byte) (fileToDownload ? 1 : 0));
        dest.writeString(fileUpdatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
