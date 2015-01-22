package com.example.arthur.l2gb.Model;


/**
 * Created by pierrebaranger on 21/01/2015.
 */
public class Objet_Model {

    private String name;
    private boolean connecte;
    private String type;
    private Integer temperature_confort;
    private Integer temperature_economique;
    private Semaine_Model profilSemaine;

    public Objet_Model(String name)
    {
        this.profilSemaine = new Semaine_Model();
        this.connecte = false;
        this.name = name;
        this.type = Constante.TYPE_CHAUFFAGE;
        this.temperature_confort = Constante.TEMP_CONFORT;
        this.temperature_economique = Constante.TEMP_ECO;
    }

    public Integer getTemperature_economique() {
        return temperature_economique;
    }

    public void setTemperature_economique(Integer temperature_economique) {
        this.temperature_economique = temperature_economique;
    }

    public Integer getTemperature_confort() {
        return temperature_confort;
    }

    public void setTemperature_confort(Integer temperature_confort) {
        this.temperature_confort = temperature_confort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Semaine_Model getProfilSemaine() {
        return profilSemaine;
    }

    public void setProfilSemaine(Semaine_Model profilSemaine) {
        this.profilSemaine = profilSemaine;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getConnecte() {
        return this.connecte;
    }

    public void setConnecte(boolean connecte) {
        this.connecte = connecte;
    }


}