package com.example.arthur.l2gb.Model.Communication;


import com.example.arthur.l2gb.Model.JsonUtil;
import com.example.arthur.l2gb.Model.Objet_Model;

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
     * \brief Envoie le message JSON pour éteindre la prise : "name".
     * \param name : l'identifiant de la prise.
     */
    public void powerOff(String name)
    {
        System.out.println("emmission power off");
        this.message = "";
        this.message = comJson.prisePowerOff(name);
        this.run();
    }

    /**
     * \brief Envoie le message JSON pour allumer la prise : "name".
     * \param name : l'identifiant de la prise.
     */
    public void powerOn(String name)
    {
        System.out.println("Emmission power on");
        this.message = "";
        this.message = comJson.prisePowerOn(name);
        this.run();

    }

}
