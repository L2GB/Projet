package com.example.arthur.l2gb.Model.Communication;


import com.example.arthur.l2gb.Model.Jours_Model;
import com.example.arthur.l2gb.Model.JsonUtil;
import com.example.arthur.l2gb.Model.Objet_Model;
import com.example.arthur.l2gb.Model.Semaine_Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * \brief Classe qui implémente l'émission de notre transmission
 *
 */
public class Emission extends Thread {

    /**
     * \var mySocket
     * Socket utilisé pour envoyer les données.
     */
    private Socket mySocket;

    /**
     * \var message
     * La chaîne de charactère à envoyé a chaque lancement du thread.
     */
    private String message;

    /**
     * \var comJson
     * Contient toutes les méthodes pour communiquer avec la centrale au format JSON.
     */
    private JsonUtil comJson;

    /**
     * \brief Constructeur de l'Emission
     * <p/>
     * Prend en paramètre le socket et lance le thread d'émission.
     * <p/>
     * \param socket Socket utilisé pour envoyer les données.
     */
    public Emission(Socket socket) {
        this.setName("ThreadEmission");
        this.mySocket = socket;
        this.comJson = new JsonUtil();
        this.message ="";
        this.start();
    }

    @Override
    /**
     * \brief Méthode appelée au lancement du Thread
     *
     * A chaque fois qu'une donnée est à envoyée au robot, la méthode est appelée par l'objet
     * qui s'occupe du changement en question.
     */
    public void run() {
        try {

            PrintWriter out = null;
            out = new PrintWriter(mySocket.getOutputStream());
            out.println(message+'\0');
            out.flush();
            System.out.println("Thread emission" +message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***********************************************************
     Méthodes pour envoyer des messages à la centrale.

     Chaque méthode va modifier l'attibut message puis rappeler la méthode run,
     afin d'envoyer la requête à la centrale.
     ***********************************************************/


    /**
     * \brief Envoie le message JSON pour demander les objets enregistrés.
     */
    public void askObjetList()
    {
        System.out.println("emission ask objet list");
        this.message = "";
        this.message = comJson.getObjetModel();
        run();
    }

    /**
     * \brief Envoie le message JSON pour demander les profils jour enregistrés.
     */
    public void askJourList()
    {
        System.out.println("emission ask jour list");
        this.message = "";
        this.message = comJson.getJoursModel();
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour demander les profils semaine enregistrés.
     */
    public void askSemaineList()
    {
        System.out.println("emission ask semaine list");
        this.message = "";
        this.message = comJson.getSemaineModel();
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour demander la consommation de l'objet: "objet"
     * \param objet : l'objet en question.
     */
    public void askConsommation(Objet_Model objet)
    {
        System.out.println("emission ask consommation");
        this.message = "";
        this.message = comJson.getConsommation(objet);
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour demander l'ajout d'un objet Jours_Model: "jour"
     * \param jour : le jour en question.
     */
    public void addJourOnCentrale(Jours_Model jour)
    {
        System.out.println("emission add jour");
        this.message = "";
        this.message = comJson.joursModelToString(jour);
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour demander l'ajout d'un objet Semaine_Model: "semaine"
     * \param semaine : le jour en question.
     */
    public void addSemaineOnCentrale(Semaine_Model semaine)
    {
        System.out.println("emission add semaine");
        this.message = "";
        this.message = comJson.semaineModelToString(semaine);
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour demander la suppression de l'objet jour: "jour"
     * \param jour : l'objet jour en question.
     */
    public void removeJoursModel(Jours_Model jour)
    {
        System.out.println("emission ask consommation");
        this.message = "";
        this.message = comJson.removeJoursModel(jour);
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour demander la suppression de l'objet: "objet"
     * \param objet : l'objet en question.
     */
    public void removeSemaineModel(Semaine_Model semaine)
    {
        System.out.println("emission ask consommation");
        this.message = "";
        this.message = comJson.removeSemaineModel(semaine);
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour demander la suppression de l'objet: "objet"
     * \param objet : l'objet en question.
     */
    public void removeObjetModel(Objet_Model objet)
    {
        System.out.println("emission ask consommation");
        this.message = "";
        this.message = comJson.removeObjetModel(objet);
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour indiquer la modification d'un jour
     * \param jour : l'objet jour en question.
     */
    public void modifiedJour(Jours_Model jour)
    {
        System.out.println("emission modifié jour");
        this.message = "";
        this.message = comJson.joursModelToString(jour);
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour indiquer la modification d'une semaine
     * \param semaine : l'objet semaine en question.
     */
    public void modifiedSemaine(Semaine_Model semaine)
    {
        System.out.println("emission modifié semaine");
        this.message = "";
        this.message = comJson.semaineModelToString(semaine);
        this.run();
    }
    /**
     * \brief Envoie le message JSON pour indiquer la modification d'un objet
     * \param objet : l'objet en question.
     */
    public void modifiedObjet(Objet_Model objet)
    {
        System.out.println("emission modifié objet");
        this.message = "";
        this.message = comJson.objetModelToString(objet);
        this.run();
    }
    /**
     * \brief Envoie le message JSON pour éteindre la prise spécifiée.
     * \param objet : l'objet que l'on veut éteindre.
     */
    public void powerOff(Objet_Model objet)
    {
        System.out.println("emmission power off");
        this.message = "";
        this.message = comJson.prisePowerOff(objet);
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour allumer la prise spécifiée.
     * \param objet : l'objet que l'on veut allumer.
     */
    public void powerOn(Objet_Model objet)
    {
        System.out.println("Emmission power on");
        this.message = "";
        this.message = comJson.prisePowerOn(objet);
        this.run();

    }

    /**
     * \brief Envoie le message JSON pour passer en mode inclusion.
     */
    public void inclusionMode()
    {
        System.out.println("Emmission inclusion mode");
        this.message = "";
        this.message = comJson.inclusionMode();
        this.run();

    }




}
