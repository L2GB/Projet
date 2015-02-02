package com.l2gb.applipfe.model;

import com.l2gb.applipfe.Constante;

/**
 * Created by pierrebaranger on 21/01/2015.
 */
public class Objet_Model {

    private String name;
    private boolean connecte;
    private boolean inconnu;
    private String type;
    private Integer temperature_confort;
    private Integer temperature_economique;
    private Semaine_Model profilSemaine;
    private Integer instanceNum;
    private Integer deviceId;
    private Integer consommation;


    public Objet_Model(String name)
    {
        this.profilSemaine = new Semaine_Model();
        this.connecte = false;
        this.inconnu = false;
        this.instanceNum = 0;
        this.deviceId = 0;
        this.name = name;
        this.type = Constante.TYPE_CHAUFFAGE;
        this.temperature_confort = Constante.TEMP_CONFORT;
        this.temperature_economique = Constante.TEMP_ECO;
    }

    public Objet_Model(String name, Semaine_Model profilSemaine)
    {
        this.profilSemaine = new Semaine_Model();
        this.profilSemaine = profilSemaine;
        this.connecte = false;
        this.inconnu = false;
        this.instanceNum = 0;
        this.deviceId = 0;
        this.name = name;
        this.type = Constante.TYPE_CHAUFFAGE;
        this.temperature_confort = Constante.TEMP_CONFORT;
        this.temperature_economique = Constante.TEMP_ECO;
    }

    public Integer getConsommation() {
        return this.consommation;
    }

    public void setConsommation(Integer consommation) {
        this.consommation = consommation;
    }

    public Integer getTemperature_economique() {
        return this.temperature_economique;
    }

    public void setTemperature_economique(Integer temperature_economique) {
        this.temperature_economique = temperature_economique;
    }

    public Integer getTemperature_confort() {
        return this.temperature_confort;
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

    public void setConnecte(boolean connecte) {
        this.connecte = connecte;
    }

    public boolean isInconnu() {
        return this.inconnu;
    }

    public void setInconnu(boolean inconnu) {
        this.inconnu = inconnu;
    }

    public boolean isConnecte() {
        return this. connecte;
    }

    public Integer getInstanceNum() {
        return instanceNum;
    }

    public void setInstanceNum(Integer instanceNum) {
        this.instanceNum = instanceNum;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }


}