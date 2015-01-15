package com.l2gb.applipfe;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arthur on 12/01/2015.
 */
public class ListeObject implements Parcelable{
    public int x=2;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
