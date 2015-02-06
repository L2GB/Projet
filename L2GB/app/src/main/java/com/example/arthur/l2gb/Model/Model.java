package com.example.arthur.l2gb.Model;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.arthur.l2gb.Model.Communication.Emission;
import com.example.arthur.l2gb.Model.Communication.Reception;
import com.example.arthur.l2gb.Model.Communication.SocketCreation;

import java.net.Socket;
import java.util.ArrayList;

/**
 * @file Model.java
 * @brief Cette est "la base donnée" de notre application
 * @date Created on 15/01/2015.
 * @author Arthur
 */
public class Model implements Parcelable {

    /**
     * objet_model
     * Liste d'objet Objet
     */
    private ArrayList<Objet_Model> objet_model;

    /**
     * jours_model
     * Liste d'objet Jours
     */
    private ArrayList<Jours_Model> jours_Model;
    private ArrayList<Semaine_Model> profilSemaine_model;
    private Handler myHandler;

    /**
     * myEmission
     * Instance de la classe Emission.
     */
    private Emission myEmission;

    /**
     * ipServeur
     * Stock l'adresse ip du serveur
     */
    private String ipServeur;

    /**
     *  portServeur
     *  Stock le numéro de port du serveur
     */
    private int portServeur;

    /**
     * communication
     * Permet d'utiliser les méthodes de communication
     */
    private JsonUtil communication;

    /**
     * @brief Constructeur de la class
     * @param ip l'adresse ip du serveur
     */
    public Model(String ip){
       this.objet_model = new ArrayList<Objet_Model>();
       this.jours_Model = new ArrayList<Jours_Model>();
       this.profilSemaine_model = new ArrayList<Semaine_Model>();
       //creerfakeModel();

        /** Mise en place de la communication**/

        boolean testconnection = setConnection(ip,Constante.PORT_SERVEUR);
        System.out.println("Communication réussie ? " +testconnection);

        try
        {
            // Met le thread en attente que la connexion s'établisse
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        askJourList();
        try
        {
            // Pause entre 2 requêtes
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        askSemaineList();
        try
        {
            // Pause entre 2 requêtes
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        askObjetList();
    }

    /**
     * @brief Permet de créer un faux model à fin de tester l'application
     */
    private void creerfakeModel(){
        Creneaux_Model creneauxModelTravail1 = new Creneaux_Model(6,8);
        Creneaux_Model creneauxModelTravail2 = new Creneaux_Model(16,21);

        Creneaux_Model creneauxModelVacance1 = new Creneaux_Model(12,15);
        Creneaux_Model creneauxModelVacance2 = new Creneaux_Model(20,23);

        Jours_Model joursTravail = new Jours_Model("Travail");
        joursTravail.getCreneauList().add(creneauxModelTravail1);
        joursTravail.getCreneauList().add(creneauxModelTravail2);
        this.jours_Model.add(joursTravail);


        Jours_Model joursVacance = new Jours_Model("Vacance");
        joursVacance.getCreneauList().add(creneauxModelVacance1);
        joursVacance.getCreneauList().add(creneauxModelVacance2);
        this.jours_Model.add(joursVacance);

        Semaine_Model semaineClassique = new Semaine_Model("Classique");
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursVacance);
        semaineClassique.getProfilJourList().add(joursVacance);
        this.profilSemaine_model.add(semaineClassique);

        Semaine_Model semaineSpe = new Semaine_Model("Spécial");
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursVacance);
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursVacance);
        semaineSpe.getProfilJourList().add(joursVacance);
        this.profilSemaine_model.add(semaineSpe);

        Semaine_Model semaineSki = new Semaine_Model("Ski");
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        this.profilSemaine_model.add(semaineSki);


        Objet_Model lumiereSalon = new Objet_Model("Lumiere salon", semaineSpe);
        lumiereSalon.setType("PRISE");
        lumiereSalon.setConsommation(100);
        lumiereSalon.setInconnu(false);
        lumiereSalon.setConnecte(true);
        lumiereSalon.setDeviceId(0);
        lumiereSalon.setInstanceNum(0);
        lumiereSalon.setAllume(true);
        Objet_Model chauffageSalon = new Objet_Model("Chauffage salon", semaineSpe);
        chauffageSalon.setConsommation(75);
        chauffageSalon.setInconnu(false);
        chauffageSalon.setConnecte(true);
        chauffageSalon.setDeviceId(0);
        chauffageSalon.setInstanceNum(1);
        Objet_Model chauffageChambre = new Objet_Model("chauffage chambre", semaineClassique);
        chauffageChambre.setConsommation(50);
        chauffageChambre.setInconnu(false);
        chauffageChambre.setConnecte(true);
        chauffageChambre.setDeviceId(1);
        chauffageChambre.setInstanceNum(1);
        Objet_Model lumiereChambre = new Objet_Model("Lumiere chambre", semaineSki);
        lumiereChambre.setType("PRISE");
        lumiereChambre.setConsommation(10);
        lumiereChambre.setInconnu(false);
        lumiereChambre.setConnecte(true);
        lumiereChambre.setDeviceId(2);
        lumiereChambre.setInstanceNum(2);
        Objet_Model console = new Objet_Model("Console");
        console.setInconnu(false);
        console.setConnecte(false);
        console.setDeviceId(3);
        console.setInstanceNum(2);
        Objet_Model iDinconnu = new Objet_Model("F4F33F3F3F");
        iDinconnu.setInconnu(true);
        iDinconnu.setConnecte(false);
        iDinconnu.setDeviceId(3);
        iDinconnu.setInstanceNum(4);
        this.objet_model.add(console);
        this.objet_model.add(iDinconnu);
        this.objet_model.add(lumiereSalon);
        this.objet_model.add(chauffageSalon);
        this.objet_model.add(chauffageChambre);
        this.objet_model.add(lumiereChambre);
    }


    /**
     * @brief Ouverture de la connexion
     * Ouvre un socket et si la connexion est établie construction de Emission et Reception.
     * @param ipServeur IP du serveur
     * @param port Port du serveur
     */
    public boolean setConnection(String ipServeur, int port)
    {

        this.myHandler = new Handler();
        // On créer une Connexion
        new SocketCreation(ipServeur, port);
        try
        {
            // Met le thread en attente que la connexion s'établisse
            Thread.sleep(500);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // Si la connexion est correcte, on instancie les classes Emission et Reception
        if (SocketCreation.isConnected() == true)
        {
            Socket newSocket = SocketCreation.getSocket();
            this.myEmission = new Emission(newSocket);
            new Reception(newSocket, myHandler,objet_model,jours_Model,profilSemaine_model);
        }

        if (SocketCreation.isConnected() == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    /*****************************************************************
     * Getter et setter
     *****************************************************************/

    public JsonUtil getCommunication() {
        return communication;
    }

    public void setCommunication(JsonUtil communication) {
        this.communication = communication;
    }

    public ArrayList<Objet_Model> getObjet_model() {
        return objet_model;
    }

    public void setObjet_model(ArrayList<Objet_Model> objet_modelArrayList) {
        this.objet_model = objet_modelArrayList;
    }

    public ArrayList<Jours_Model> getJours_Model() {
        return jours_Model;
    }

    public void setJours_Model(ArrayList<Jours_Model> jours_modelArrayList) {
        this.jours_Model = jours_modelArrayList;
    }

    public ArrayList<Semaine_Model> getProfilSemaine_model() {
        return this.profilSemaine_model;
    }

    public void setProfilSemaine_model(ArrayList<Semaine_Model> semaine_modelArrayList) {
        this.profilSemaine_model = semaine_modelArrayList;
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





    /***********************************************************
     Méthodes pour envoyer des messages à la centrale.
     ***********************************************************/


    /**
     * @brief Envoie le message JSON pour demander les profils jour enregistrés.
     */
    public void askJourList()
    {
        //System.out.println("model ask jour list");
        this.myEmission.askJourList();
    }
    /**
     * @brief Envoie le message JSON pour demander les profils semaine enregistrés.
     */
    public void askSemaineList()
    {
        //System.out.println("model ask jour list");
        this.myEmission.askSemaineList();
    }

    /**
     * @brief Envoie le message JSON pour demander les objets enregistrés.
     */
    public void askObjetList()
    {
        //System.out.println("model ask objet list");
        this.myEmission.askObjetList();
    }

    /**
     * @brief Envoie le message JSON pour demander l'ajout d'un objet Jours_Model: "jour"
     * @param jour : le jour en question.
     */
    public void addJour(Jours_Model jour)
    {
        //System.out.println("model add objet");
        this.myEmission.addJourOnCentrale(jour);
    }

    /**
     * @brief Envoie le message JSON pour demander l'ajout d'un objet Semaine_Model: "semaine"
     * @param semaine : le jour en question.
     */
    public void addSemaine(Semaine_Model semaine)
    {
        //System.out.println("model add semaine");
        this.myEmission.addSemaineOnCentrale(semaine);
    }

    /**
     * @brief Envoie le message JSON pour demander la suppression de l'objet jour: "jour"
     * @param jour : l'objet jour en question.
     */
    public void removeJour(Jours_Model jour)
    {
        //System.out.println("model remove jour");
        this.myEmission.removeJoursModel(jour);
    }

    /**
     * @brief Envoie le message JSON pour demander la suppression de la semaine: "semaine"
     * @param semaine : l'objet semaine en question.
     */
    public void removeSemaine(Semaine_Model semaine)
    {
        //System.out.println("model remove jour");
        this.myEmission.removeSemaineModel(semaine);
    }

    /**
     * @brief Envoie le message JSON pour demander la suppression de l'objet: "objet"
     * @param objet : l'objet en question.
     */
    public void removeObjet(Objet_Model objet)
    {
        //System.out.println("model remove jour");
        this.myEmission.removeObjetModel(objet);
    }

    /**
     * @brief Envoie le message JSON pour indiquer la modification d'un jour
     * @param jour : l'objet jour en question.
     */
    public void modifiedJour(Jours_Model jour)
    {
        //System.out.println("model modifier jour");
        this.myEmission.modifiedJour(jour);
    }

    /**
     * @brief Envoie le message JSON pour indiquer la modification d'une semaine
     * @param semaine : l'objet semaine en question.
     */
    public void modifiedSemaine(Semaine_Model semaine)
    {
        //System.out.println("model modifier semaine");
        this.myEmission.modifiedSemaine(semaine);
    }

    /**
     * @brief Envoie le message JSON pour indiquer la modification d'un objet
     * @param objet : l'objet en question.
     */
    public void modifiedObjet(Objet_Model objet)
     {
         //System.out.println("model modifier objet");
         this.myEmission.modifiedObjet(objet);
     }

    /**
     * @brief Envoie le message JSON pour allumer la prise spécifiée.
     * @param objet : l'objet que l'on veut allumer.
     */
    public void powerOn(Objet_Model objet)
    {
        //System.out.println("model power on");
        this.myEmission.powerOn(objet);
    }

    /**
     * @brief Envoie le message JSON pour éteindre la prise spécifiée.
     * @param objet : l'objet que l'on veut éteindre.
     */
    public void powerOff(Objet_Model objet)
    {
        //System.out.println("model power off");
        this.myEmission.powerOff(objet);
    }

    /**
     * @brief Envoie le message JSON pour demander la consommation de l'objet: "objet"
     * \param objet : l'objet en question.
     */
    public void askConsommation(Objet_Model objet)
    {
        //System.out.println("model ask consommation");
        this.myEmission.askConsommation(objet);
    }

    /**
     * @brief Envoie le message JSON pour passer en mode inclusion.
     */
    public void inclusionMode()
    {
        //System.out.println("model ask consommation");
        this.myEmission.inclusionMode();
    }


    /*****************************************************************
     * Methodes utilisées pour la serialisation des objets du package model
     *****************************************************************/

    protected Model(Parcel in) {
        if (in.readByte() == 0x01) {
            objet_model = new ArrayList<Objet_Model>();
            in.readList(objet_model, Objet_Model.class.getClassLoader());
        } else {
            objet_model = null;
        }
        if (in.readByte() == 0x01) {
            jours_Model = new ArrayList<Jours_Model>();
            in.readList(jours_Model, Jours_Model.class.getClassLoader());
        } else {
            jours_Model = null;
        }
        if (in.readByte() == 0x01) {
            profilSemaine_model = new ArrayList<Semaine_Model>();
            in.readList(profilSemaine_model, Semaine_Model.class.getClassLoader());
        } else {
            profilSemaine_model = null;
        }
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (objet_model == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(objet_model);
        }
        if (jours_Model == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(jours_Model);
        }
        if (profilSemaine_model == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(profilSemaine_model);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Model> CREATOR = new Parcelable.Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}