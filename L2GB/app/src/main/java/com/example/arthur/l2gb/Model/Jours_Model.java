package com.example.arthur.l2gb.Model;

import java.util.ArrayList;

/**
 * Created by pierre on 12/01/2015.
 */
public class Jours_Model {

    private ArrayList<Creneaux_Model> creneauList;
    private String name;

    public Jours_Model() {
        this.name = null;
        this.creneauList = new ArrayList<Creneaux_Model>();
    }

    public Jours_Model(String name)
    {
        this.name = name;
        this.creneauList = new ArrayList<Creneaux_Model>();
    }

    public Jours_Model(String name, ArrayList<Creneaux_Model> creneauList)
    {
        this.name = name;
        this.creneauList = new ArrayList<Creneaux_Model>();
        this.creneauList = creneauList;
    }



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
}
