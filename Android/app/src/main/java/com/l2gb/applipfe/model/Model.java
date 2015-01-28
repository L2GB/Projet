package com.l2gb.applipfe.model;

import com.l2gb.applipfe.Constante;
import com.l2gb.applipfe.model.communicationTest.Connexion;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by arthur on 15/01/2015.
 */
public class Model {
    private ArrayList<Objet_Model> objet_modelArrayList;
    private ArrayList<Jours_Model> jours_modelArrayList;
    private ArrayList<Semaine_Model> semaine_modelArrayList;
    private Connexion connexion;
    private String ipServeur;
    private int portServeur;
    private Thread connexionThread;
    private JsonUtil communication;


    public ArrayList<Objet_Model> getObjet_model() {
        return objet_modelArrayList;
    }

    public void setObjet_model(ArrayList<Objet_Model> objet_modelArrayList) {
        this.objet_modelArrayList = objet_modelArrayList;
    }

    public ArrayList<Jours_Model> getJours_Model() {
        return jours_modelArrayList;
    }

    public void setJours_Model(ArrayList<Jours_Model> jours_modelArrayList) {
        this.jours_modelArrayList = jours_modelArrayList;
    }

    public ArrayList<Semaine_Model> getProfilSemaine_model() {
        return this.semaine_modelArrayList;
    }

    public void setProfilSemaine_model(ArrayList<Semaine_Model> semaine_modelArrayList) {
        this.semaine_modelArrayList = semaine_modelArrayList;
    }

    public String getIpServeur() {
        return this.ipServeur;
    }

    public void setIpServeur(String ipServeur) {
        this.ipServeur = ipServeur;
    }

    public int getPort() {
        return this.portServeur;
    }

    public void setPort(int port) {
        this.portServeur = port;
    }


    public Model()
    {
        System.out.println("Création de l'objet connexion");
        this.connexion = new Connexion(Constante.IP_SERVEUR, Constante.PORT_SERVEUR);
        this.connexionThread = new Thread(this.connexion);
        this.connexionThread.start();


        //System.out.println("model chat"+this.connexion.getChat().toString());
        this.connexion.getChat().askProfilObjet();



        //this.connexion.getChat().sendRequest(test);
    }
}
