package com.example.arthur.l2gb.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by arthur on 15/01/2015.
 */
public class Model implements Parcelable {
    private ArrayList<Objet_Model> objet_model;
    private ArrayList<Jours_Model> jours_Model;
    private ArrayList<Semaine_Model> profilSemaine_model;

    public ArrayList<Objet_Model> getObjet_model() {
        return objet_model;
    }

    public void setObjet_model(ArrayList<Objet_Model> objet_model) {
        this.objet_model = objet_model;
    }

    public ArrayList<Jours_Model> getJours_Model() {
        return jours_Model;
    }

    public void setJours_Model(ArrayList<Jours_Model> jours_Model) {
        this.jours_Model = jours_Model;
    }

    public ArrayList<Semaine_Model> getProfilSemaine_model() {
        return profilSemaine_model;
    }

    public void setProfilSemaine_model(ArrayList<Semaine_Model> profilSemaine_model) {
        this.profilSemaine_model = profilSemaine_model;
    }

    protected Model(Parcel in) {
        if (in.readByte() == 0x01) {
            objet_model = new ArrayList<Objet_Model>();
            in.readList(objet_model, Objet_Model.class.getClassLoader());
        } else {
            objet_model = null;
        }
        if (in.readByte() == 0x01) {
            jours_Model = new ArrayList<Jours_Model>();
            in.readList(jours_Model, Jours_Model.class.getClassLoader());
        } else {
            jours_Model = null;
        }
        if (in.readByte() == 0x01) {
            profilSemaine_model = new ArrayList<Semaine_Model>();
            in.readList(profilSemaine_model, Semaine_Model.class.getClassLoader());
        } else {
            profilSemaine_model = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (objet_model == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(objet_model);
        }
        if (jours_Model == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(jours_Model);
        }
        if (profilSemaine_model == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(profilSemaine_model);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Model> CREATOR = new Parcelable.Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}