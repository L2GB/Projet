package com.example.arthur.l2gb.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @file Jours_Model
 * @brief Classe qui permet la création d'un objet jour
 * @date Created on 12/01/2015.
 * @author pierrebaranger1
 */
public class Jours_Model implements Parcelable {

    private ArrayList<Creneaux_Model> creneauList;
    private String name;

    /**
     * @brief Constructeur du Jours_Model
     */
    public Jours_Model() {
        this.name = null;
        this.creneauList = new ArrayList<Creneaux_Model>();
    }

    /**
     * @brief Constructeur du Jours_Model
     * @param name
     */
    public Jours_Model(String name)
    {
        this.name = name;
        this.creneauList = new ArrayList<Creneaux_Model>();
    }

    /**
     * @brief Constructeur du Jours_Model
     * @param name
     * @param creneauList
     */
    public Jours_Model(String name, ArrayList<Creneaux_Model> creneauList)
    {
        this.name = name;
        this.creneauList = new ArrayList<Creneaux_Model>();
        this.creneauList = creneauList;
    }

    /*****************************************************************
     * Getter et setter
     *****************************************************************/

    public ArrayList<Creneaux_Model> getCreneauList()
    {
        return creneauList;
    }

    public void setCreneauList(ArrayList<Creneaux_Model> creneauList)
    {
        this.creneauList = creneauList;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    /*****************************************************************
     * Methodes utilisées pour la serialisation des objets du package model
     *****************************************************************/

    protected Jours_Model(Parcel in) {
        if (in.readByte() == 0x01) {
            creneauList = new ArrayList<Creneaux_Model>();
            in.readList(creneauList, Creneaux_Model.class.getClassLoader());
        } else {
            creneauList = null;
        }
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (creneauList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(creneauList);
        }
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Jours_Model> CREATOR = new Parcelable.Creator<Jours_Model>() {
        @Override
        public Jours_Model createFromParcel(Parcel in) {
            return new Jours_Model(in);
        }

        @Override
        public Jours_Model[] newArray(int size) {
            return new Jours_Model[size];
        }
    };
}