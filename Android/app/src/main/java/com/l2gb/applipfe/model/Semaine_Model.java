package com.l2gb.applipfe.model;

import java.util.ArrayList;

/**
 * Created by pierrebaranger1 on 21/01/2015.
 */
public class Semaine_Model {

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

}
