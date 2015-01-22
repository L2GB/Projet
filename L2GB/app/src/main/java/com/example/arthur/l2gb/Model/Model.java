package com.example.arthur.l2gb.Model;

import java.util.ArrayList;

/**
 * Created by arthur on 15/01/2015.
 */
public class Model {
    private ArrayList<Objet_Model> objet_model;
    private ArrayList<Jours_Model> jours_Model;
    private ArrayList<ProfilSemaine_Model> profilSemaine_model;

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

    public ArrayList<ProfilSemaine_Model> getProfilSemaine_model() {
        return profilSemaine_model;
    }

    public void setProfilSemaine_model(ArrayList<ProfilSemaine_Model> profilSemaine_model) {
        this.profilSemaine_model = profilSemaine_model;
    }
}
