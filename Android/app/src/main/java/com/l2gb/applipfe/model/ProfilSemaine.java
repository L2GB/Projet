package com.l2gb.applipfe.model;

import java.util.ArrayList;

/**
 * Created by pierrebaranger1 on 21/01/2015.
 */
public class ProfilSemaine {

    private String name;
    private ArrayList<ProfilJour> profilJourList;

    public ProfilSemaine()
    {
        this.name = new String();
        this.profilJourList = new ArrayList<ProfilJour>();
    }

    public ProfilSemaine(String name)
    {
        this.name = name;
        this.profilJourList = new ArrayList<ProfilJour>();
    }

    public ProfilSemaine(String name,ArrayList<ProfilJour> profilJourList)
    {
        this.name = name;
        this.profilJourList = new ArrayList<ProfilJour>();
        this.profilJourList = profilJourList;
    }

    public ArrayList<ProfilJour> getProfilJourList()
    {
        return profilJourList;
    }

    public void setProfilJourList(ArrayList<ProfilJour> profilJourList)
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
