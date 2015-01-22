package com.example.arthur.l2gb.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by pierrebaranger1 on 21/01/2015.
 */
public class Semaine_Model implements Parcelable {

    private String name;
    private ArrayList<Jours_Model> profilJourList;

    public Semaine_Model()
    {
        this.name = new String();
        this.profilJourList = new ArrayList<Jours_Model>();
    }

    public Semaine_Model(String name)
    {
        this.name = name;
        this.profilJourList = new ArrayList<Jours_Model>();
    }

    public Semaine_Model(String name, ArrayList<Jours_Model> profilJourList)
    {
        this.name = name;
        this.profilJourList = new ArrayList<Jours_Model>();
        this.profilJourList = profilJourList;
    }

    public ArrayList<Jours_Model> getProfilJourList()
    {
        return profilJourList;
    }

    public void setProfilJourList(ArrayList<Jours_Model> profilJourList)
    {
        this.profilJourList = profilJourList;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    protected Semaine_Model(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            profilJourList = new ArrayList<Jours_Model>();
            in.readList(profilJourList, Jours_Model.class.getClassLoader());
        } else {
            profilJourList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (profilJourList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(profilJourList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Semaine_Model> CREATOR = new Parcelable.Creator<Semaine_Model>() {
        @Override
        public Semaine_Model createFromParcel(Parcel in) {
            return new Semaine_Model(in);
        }

        @Override
        public Semaine_Model[] newArray(int size) {
            return new Semaine_Model[size];
        }
    };
}