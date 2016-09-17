/**
 * Copyright (c) 2012 Vinayak Solutions Private Limited
 * See the LICENSE file for copying permission.
*/


package com.vinsol.expensetracker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite extends Entry {

    public static final Parcelable.Creator<Favorite> CREATOR = new Parcelable.Creator<Favorite>() {
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public Favorite() {}

    public Favorite(Parcel in) {
        amount = in.readString();
        description = in.readString();
        transactionType = in.readString();
        type = in.readString();
        id = in.readString();
        location = in.readString();
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
        dest.writeString(amount);
        dest.writeString(description);
        dest.writeString(transactionType);
        dest.writeString(type);
        dest.writeString(id);
        dest.writeString(location);
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
