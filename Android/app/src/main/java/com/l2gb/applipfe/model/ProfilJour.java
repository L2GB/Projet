package com.l2gb.applipfe.model;

import java.util.ArrayList;

/**
 * Created by pierre on 12/01/2015.
 */
public class ProfilJour {

    private ArrayList<Creneau> creneauList;
    private String name;

    public ProfilJour() {
        this.name = null;
        this.creneauList = new ArrayList<Creneau>();
    }

    public ProfilJour(String name)
    {
        this.name = name;
        this.creneauList = new ArrayList<Creneau>();
    }

    public ProfilJour(String name, ArrayList<Creneau> creneauList )
    {
        this.name = name;
        this.creneauList = new ArrayList<Creneau>();
        this.creneauList = creneauList;
    }



    public ArrayList<Creneau> getCreneauList()
    {
        return creneauList;
    }

    public void setCreneauList(ArrayList<Creneau> creneauList)
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
}
