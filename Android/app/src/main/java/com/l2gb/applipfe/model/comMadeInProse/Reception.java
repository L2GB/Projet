package com.l2gb.applipfe.model.comMadeInProse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


import android.os.Handler;

import com.l2gb.applipfe.model.Jours_Model;
import com.l2gb.applipfe.model.Objet_Model;
import com.l2gb.applipfe.model.Semaine_Model;

/**
 * Classe qui implémente la réception de notre transmission
 */
public class Reception extends Thread {

    /**
     * \var myHandler
     * Handler qui sert de boîte aux lettres pour afficher les données reçues
     */
    private Handler myHandler;

    /**
     * \var mySocket
     * Socket utilisé pour envoyer les données.
     */
    private Socket mySocket;


    /**
     * \var buffer
     * Contient les données sous forme de chaîne de caractères reçues par la centrale .
     */
    private String buffer;

    /**
     * \var in
     * variable pour envoyer des chaines de charactère par socket.
     */
    private BufferedReader in = null;

    /**
     * \var objetList
     * Contient la liste d'objet enregistré dans centrale  .
     */
    private ArrayList<Objet_Model> objetList;

    /**
     * \var profilJourList
     * Contient la liste de profil jour enregistré dans centrale  .
     */
    private ArrayList<Jours_Model> profilJourList;

    /**
     * \var profilSemaineList
     * Contient la liste de semaine enregistré dans centrale  .
     */
    private ArrayList<Semaine_Model> profilSemaineList;

    /**
     * \brief Constructeur de Reception
     * <p/>
     * Prend en paramètre le socket et le handler et lance la reception
     * <p/>
     * \param socket
     * \param handler
     * \param objetList
     * \param profilSemaineList
     * \param profilJourList
     */
    public Reception(Socket socket, Handler handler, ArrayList<Objet_Model> objetList,
                     ArrayList<Jours_Model> profilJourList, ArrayList<Semaine_Model> profilSemaineList) {
        this.myHandler = handler;
        this.mySocket = socket;
        this.objetList = objetList;
        this.profilJourList = profilJourList;
        this.profilSemaineList = profilSemaineList;
        this.setName("ThreadReception");
        buffer = "";
        try {
            this.in = new BufferedReader(new InputStreamReader(this.mySocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    @Override
    /**
     * \brief Méthode appelée au lancement du Thread
     *
     * A chaque fois qu'une donnée est reçu de la centrale, récupère la chaine de charactère et le retourne
     * grâce au handler dans l'UI Thread.
     */
    public void run() {
        while (true) {
            try {
                buffer = this.in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    parseReceiveString(buffer);
                }

            });
        }
    }


    /**
     * \brief Analyse la chaîne de caractère reçu pour identifier le message
     * <p/>
     * Récupère la chaîne de caractère reçu de la centrale et l'analyse pour modifier
     * les objets concernés.
     * <p/>
     * \param buffer Chaîne de caractère reçu.
     */
    private void parseReceiveString(String buffer) {

    }

}