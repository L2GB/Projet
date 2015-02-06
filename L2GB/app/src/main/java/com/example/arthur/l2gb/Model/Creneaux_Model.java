package com.example.arthur.l2gb.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @date Created by pierre on 12/01/2015.
 * @brief Class qui permet de créer les créneaux horraires
 * @author pierrebaranger1
 */
public class Creneaux_Model implements Parcelable {

    private int hDebut;
    private int mDebut;
    private int hFin;
    private int mFin;
    private Boolean autorisation;

    /**
     * @brief Constructeur de la classe
     * @param hDebut heure à laquelle débute le créneau
     * @param mDebut minute à laquelle débute le créneau
     * @param hFin heure à laquelle finie le créneau
     * @param mFin minute à laquelle finie le créneau
     * @param autorisation autorisation ou non de controle par la centrale durant ce créneau
     */
    public Creneaux_Model(int hDebut, int mDebut, int hFin, int mFin, boolean autorisation)
    {
        this.hDebut=hDebut;
        this.mDebut=mDebut;
        this.hFin=hFin;
        this.mFin=mFin;
        this.autorisation=autorisation;
    }

    /**
     * @brief Constructeur de la classe
     * @param hDebut heure à laquelle débute le créneau
     * @param hFin heure à laquelle finie le créneau
     * @param autorisation autorisation ou non de controle par la centrale durant ce créneau
     */
    public Creneaux_Model(int hDebut, int hFin, boolean autorisation)
    {
        this.hDebut=hDebut;
        this.mDebut=00;
        this.hFin=hFin;
        this.mFin=00;
        this.autorisation=autorisation;
    }

    /**
     * @brief Constructeur de la classe
     * @param hDebut heure à laquelle débute le créneau
     * @param hFin heure à laquelle finie le créneau
     */
    public Creneaux_Model(int hDebut, int hFin)
    {
        this.hDebut=hDebut;
        this.mDebut=00;
        this.hFin=hFin;
        this.mFin=00;
        this.autorisation=true;
    }

    /**
     * @brief Constructeur de la classe
     */
    public Creneaux_Model()
    {
        this.hDebut=25;
        this.mDebut=00;
        this.hFin=25;
        this.mFin=00;
        this.autorisation=true;
    }

    /*****************************************************************
     * Getter et setter
     *****************************************************************/

    public int gethDebut() {
        return hDebut;
    }

    public void sethDebut(int hDebut) {
        this.hDebut = hDebut;
    }

    public int getmDebut() {
        return mDebut;
    }

    public void setmDebut(int mDebut) {
        this.mDebut = mDebut;
    }

    public int gethFin() {
        return hFin;
    }

    public void sethFin(int hFin) {
        this.hFin = hFin;
    }

    public int getmFin() {
        return mFin;
    }

    public void setmFin(int mFin) {
        this.mFin = mFin;
    }

    public Boolean getAutorisation() {
        return autorisation;
    }

    public void setAutorisation(Boolean autorisation) {
        this.autorisation = autorisation;
    }


    /*****************************************************************
     * Methodes utilisées pour la serialisation des objets du model
     *****************************************************************/

    protected Creneaux_Model(Parcel in) {
        hDebut = in.readInt();
        mDebut = in.readInt();
        hFin = in.readInt();
        mFin = in.readInt();
        byte autorisationVal = in.readByte();
        autorisation = autorisationVal == 0x02 ? null : autorisationVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hDebut);
        dest.writeInt(mDebut);
        dest.writeInt(hFin);
        dest.writeInt(mFin);
        if (autorisation == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (autorisation ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Creneaux_Model> CREATOR = new Parcelable.Creator<Creneaux_Model>() {
        @Override
        public Creneaux_Model createFromParcel(Parcel in) {
            return new Creneaux_Model(in);
        }

        @Override
        public Creneaux_Model[] newArray(int size) {
            return new Creneaux_Model[size];
        }
    };
}