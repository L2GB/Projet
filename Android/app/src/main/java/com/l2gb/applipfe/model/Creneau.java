package com.l2gb.applipfe.model;

import java.sql.Time;

/**
 * Created by pierre on 12/01/2015.
 */
public class Creneau {

    private int hDebut;
    private int mDebut;
    private int hFin;
    private int mFin;
    private Boolean autorisation;

    public Creneau(int hDebut, int mDebut, int hFin, int mFin, boolean autorisation)
    {
        this.hDebut=hDebut;
        this.mDebut=mDebut;
        this.hFin=hFin;
        this.mFin=mFin;
        this.autorisation=autorisation;
    }

    public Creneau(int hDebut, int hFin, boolean autorisation)
    {
        this.hDebut=hDebut;
        this.mDebut=00;
        this.hFin=hFin;
        this.mFin=00;
        this.autorisation=autorisation;
    }

    public Creneau(int hDebut, int hFin)
    {
        this.hDebut=hDebut;
        this.mDebut=00;
        this.hFin=hFin;
        this.mFin=00;
        this.autorisation=true;
    }

    public Creneau()
    {

    }

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

}
