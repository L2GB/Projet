package com.l2gb.applipfe.model;

import android.os.Handler;

import com.l2gb.applipfe.Constante;
import com.l2gb.applipfe.model.comMadeInProse.Emission;
import com.l2gb.applipfe.model.comMadeInProse.Reception;
import com.l2gb.applipfe.model.comMadeInProse.SocketCreation;

import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by arthur on 15/01/2015.
 */
public class Model {
    private ArrayList<Objet_Model> objet_modelArrayList;
    private ArrayList<Jours_Model> jours_modelArrayList;
    private ArrayList<Semaine_Model> semaine_modelArrayList;
    Handler myHandler;

    /**
     * \var myEmission
     * Instance de la classe Emission.
     */
    private Emission myEmission;
    //private Reception myReception;
    private String ipServeur;
    private int portServeur;
    private JsonUtil communication;

    public JsonUtil getCommunication() {
        return communication;
    }

    public void setCommunication(JsonUtil communication) {
        this.communication = communication;
    }

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


    public void askJourList()
    {
        //System.out.println("model ask jour list");
        this.myEmission.askJourList();
    }

    public void askSemaineList()
    {
        //System.out.println("model ask jour list");
        this.myEmission.askSemaineList();
    }

    public void askObjetList()
    {
        //System.out.println("model ask objet list");
        this.myEmission.askObjetList();
    }

    public void powerOn(String name)
    {
        //System.out.println("model power on");
        this.myEmission.powerOn(name);
    }

    public void powerOff(String name)
    {
        //System.out.println("model power off");
        this.myEmission.powerOff(name);
    }

    public void askConsommation(String name)
    {
        //System.out.println("model ask consommation");
        this.myEmission.powerOff(name);
    }


    public Model()
    {
        objet_modelArrayList = new ArrayList<Objet_Model>();
        jours_modelArrayList = new ArrayList<Jours_Model>();
        semaine_modelArrayList = new ArrayList<Semaine_Model>();
        communication =  new JsonUtil();

        /** Communication made in prose **/
        boolean testconnection = setConnection(Constante.IP_SERVEUR,Constante.PORT_SERVEUR);
        System.out.println("Communication réussie ? " +testconnection);

        //myEmission.powerOff("prise1");

        //System.out.println(jours_modelArrayList.get(0).getName());

        /*while (true) {

            myEmission.powerOn("prise1");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myEmission.powerOff("prise1");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }*/

        //this.connexion.getChat().sendRequest(test);
    }

    public void creerfakeModel(){
        Creneaux_Model creneauxModelTravail1 = new Creneaux_Model(6,8);
        Creneaux_Model creneauxModelTravail2 = new Creneaux_Model(16,21);

        Creneaux_Model creneauxModelVacance1 = new Creneaux_Model(12,15);
        Creneaux_Model creneauxModelVacance2 = new Creneaux_Model(20,23);

        Jours_Model joursTravail = new Jours_Model("Travail");
        joursTravail.getCreneauList().add(creneauxModelTravail1);
        joursTravail.getCreneauList().add(creneauxModelTravail2);
        this.jours_modelArrayList.add(joursTravail);


        Jours_Model joursVacance = new Jours_Model("Vacance");
        joursVacance.getCreneauList().add(creneauxModelVacance1);
        joursVacance.getCreneauList().add(creneauxModelVacance2);
        this.jours_modelArrayList.add(joursVacance);

        Semaine_Model semaineClassique = new Semaine_Model("Classique");
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursVacance);
        semaineClassique.getProfilJourList().add(joursVacance);
        this.semaine_modelArrayList.add(semaineClassique);

        Semaine_Model semaineSpe = new Semaine_Model("Spécial");
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursVacance);
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursVacance);
        semaineSpe.getProfilJourList().add(joursVacance);
        this.semaine_modelArrayList.add(semaineSpe);

        Semaine_Model semaineSki = new Semaine_Model("Ski");
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        this.semaine_modelArrayList.add(semaineSki);


        Objet_Model lumiereSalon = new Objet_Model("Lumiere salon", semaineSpe);
        lumiereSalon.setConsommation(100);
        lumiereSalon.setInconnu(true);
        lumiereSalon.setConnecte(true);
        Objet_Model chauffageSalon = new Objet_Model("Chauffage salon", semaineSpe);
        chauffageSalon.setConsommation(75);
        chauffageSalon.setInconnu(true);
        chauffageSalon.setConnecte(true);
        Objet_Model chauffageChambre = new Objet_Model("chauffage chambre", semaineClassique);
        chauffageChambre.setConsommation(50);
        chauffageChambre.setInconnu(true);
        chauffageChambre.setConnecte(true);
        Objet_Model lumiereChambre = new Objet_Model("Lumiere chambre", semaineSki);
        lumiereChambre.setConsommation(10);
        lumiereChambre.setInconnu(true);
        lumiereChambre.setConnecte(true);
        Objet_Model console = new Objet_Model("Console");
        console.setInconnu(true);
        console.setConnecte(false);
        Objet_Model iDinconnu = new Objet_Model("F4F33F3F3F");
        iDinconnu.setInconnu(false);
        iDinconnu.setConnecte(false);
        this.objet_modelArrayList.add(console);
        this.objet_modelArrayList.add(iDinconnu);
        this.objet_modelArrayList.add(lumiereSalon);
        this.objet_modelArrayList.add(chauffageSalon);
        this.objet_modelArrayList.add(chauffageChambre);
        this.objet_modelArrayList.add(lumiereChambre);
    }

    /**
     * \brief Ouverture de la connexion
     *
     * Ouvre un socket et si la connexion est établie construction de Emission et Reception.
     *
     * \param ipRobot IP du robot
     * \param portRobot Port du robot
     */
    public boolean setConnection(String ipRobot, int portRobot)
    {

        this.myHandler = new Handler();
        // On créer une Connexion
        new SocketCreation(ipRobot, portRobot);
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
            new Reception(newSocket, myHandler,objet_modelArrayList,jours_modelArrayList,semaine_modelArrayList);
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
}
