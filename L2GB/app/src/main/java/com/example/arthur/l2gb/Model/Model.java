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
 * Created by arthur on 15/01/2015.
 */
public class Model implements Parcelable {

    private ArrayList<Objet_Model> objet_model;
    private ArrayList<Jours_Model> jours_Model;
    private ArrayList<Semaine_Model> profilSemaine_model;

    Handler myHandler;

    /**
     * \var myEmission
     * Instance de la classe Emission.
     */
    private Emission myEmission;
    private String ipServeur;
    private int portServeur;
    private JsonUtil communication;

    public Model(String ip){
       this.objet_model = new ArrayList<Objet_Model>();
       this.jours_Model = new ArrayList<Jours_Model>();
       this.profilSemaine_model = new ArrayList<Semaine_Model>();
       //creerfakeModel();

        /** Mise en place de la communication**/

        boolean testconnection = setConnection(ip,Constante.PORT_SERVEUR);
        System.out.println("Communication réussie ? " +testconnection);
        askJourList();
        try
        {
            // Met le thread en attente que la connexion s'établisse
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        askSemaineList();
        try
        {
            // Met le thread en attente que la connexion s'établisse
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        askObjetList();
    }

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

    public void addJour(Jours_Model jour)
    {
        //System.out.println("model add objett");
        this.myEmission.addJourOnCentrale(jour);
    }

    public void addSemaine(Semaine_Model semaine)
    {
        //System.out.println("model add semaine");
        this.myEmission.addSemaineOnCentrale(semaine);
    }

    public void removeJour(Jours_Model jour)
    {
        //System.out.println("model remove jour");
        this.myEmission.removeJoursModel(jour);
    }

    public void removeSemaine(Semaine_Model semaine)
    {
        //System.out.println("model remove jour");
        this.myEmission.removeSemaineModel(semaine);
    }

    public void removeObjet(Objet_Model objet)
    {
        //System.out.println("model remove jour");
        this.myEmission.removeObjetModel(objet);
    }

    public void modifiedJour(Jours_Model jour)
    {
        //System.out.println("model modifier jour");
        this.myEmission.modifiedJour(jour);
    }

    public void modifiedSemaine(Semaine_Model semaine)
    {
        //System.out.println("model modifier semaine");
        this.myEmission.modifiedSemaine(semaine);
    }

    public void modifiedObjet(Objet_Model objet)
     {
         //System.out.println("model modifier objet");
         this.myEmission.modifiedObjet(objet);
     }

    public void powerOn(Objet_Model objet)
    {
        //System.out.println("model power on");
        this.myEmission.powerOn(objet);
    }

    public void powerOff(Objet_Model objet)
    {
        //System.out.println("model power off");
        this.myEmission.powerOff(objet);
    }

    public void askConsommation(Objet_Model objet)
    {
        //System.out.println("model ask consommation");
        this.myEmission.askConsommation(objet);
    }

    public void inclusionMode()
    {
        //System.out.println("model ask consommation");
        this.myEmission.inclusionMode();
    }


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