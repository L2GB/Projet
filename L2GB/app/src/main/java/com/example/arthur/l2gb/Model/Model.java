package com.example.arthur.l2gb.Model;

import java.util.ArrayList;

/**
 * Created by arthur on 15/01/2015.
 */
public class Model {
    private ArrayList<Objet_Model> objet_model;
    private ArrayList<Jours_Model> jours_Model;
    private ArrayList<Semaine_Model> semaine_model;

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

    public ArrayList<Semaine_Model> getSemaine_model() {
        return semaine_model;
    }

    public void setSemaine_model(ArrayList<Semaine_Model> semaine_model) {
        this.semaine_model = semaine_model;
    }
}
