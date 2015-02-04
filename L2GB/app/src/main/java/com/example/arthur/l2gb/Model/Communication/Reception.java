package com.example.arthur.l2gb.Model.Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


import android.os.Handler;

import com.example.arthur.l2gb.Model.Jours_Model;
import com.example.arthur.l2gb.Model.JsonUtil;
import com.example.arthur.l2gb.Model.Objet_Model;
import com.example.arthur.l2gb.Model.Semaine_Model;


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
     * Variable utilisée pour stocker les chaînes de caractères reçues par la centrale .
     */
    private String buffer;

    /**
     * \var comJson
     * Contient toutes les méthodes pour communiquer avec la centrale au format JSON.
     */
    private JsonUtil comJson;

    /**
     * \var in
     * variable pour envoyer des chaines de charactère par socket.
     */
    private BufferedReader in = null;

    /**
     * \var objetList
     * Variable pour stocker la liste d'objets enregistrés dans centrale  .
     */
    private ArrayList<Objet_Model> objetList;

    /**
     * \var profilJourList
     * Variable pour stocker la liste de profil jour enregistré dans centrale  .
     */
    private ArrayList<Jours_Model> profilJourList;

    /**
     * \var profilSemaineList
     * Variable pour stocker la liste de semaine enregistré dans centrale  .
     */
    private ArrayList<Semaine_Model> profilSemaineList;

    /**
     * \var message
     * Variable pour stocker les message en reception  .
     */
    private byte[] message = new byte[2048];

    /**
     * \var typeMsg
     * Variable pour stocker le type de message à chaque reception  .
     */
    private Integer typeMsg = 0;

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
        this.buffer = null;
        this.comJson = new JsonUtil();
        this.setName("ThreadReception");

        System.out.println("Creation du thread de reception");

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
        while(true){
            try {
                int readBytes = mySocket.getInputStream().read(message, 0, 2048);
                this.buffer = new String(message, 0, readBytes, "UTF-8");
                System.out.println("Le buffer contient : "+buffer+"\n");
                this.typeMsg = this.comJson.readResponse(buffer);
            } catch (IOException e) {
                System.out.println("Exception catch \n");
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
        int responseType=0;
        responseType = this.comJson.readResponse(buffer);
        System.out.println("Parsage, la réponse est de type : "+responseType);

        /** Liste de jour**/
        if(responseType == 1){
            //System.out.println(buffer);
            this.comJson.stringToJoursModel(buffer,profilJourList);
            System.out.println("Liste de profils jour reçues \n");
        }
        /** Liste de semaine **/
        else if(responseType == 2){
            //System.out.println(buffer);
            this.comJson.stringToSemaineModel(buffer,profilSemaineList);
            System.out.println("Liste de profils semaine reçues \n");
        }
        /** Liste d'objet **/
        else if(responseType == 3){
            //System.out.println(buffer);
            this.comJson.stringToObjetModel(buffer,objetList);
            System.out.println("Liste des objets reçues \n");
        }
        /** nouvel objet **/
        else if(responseType ==4){
            //System.out.println(buffer);
            this.comJson.stringToNewObjet(buffer,objetList);
            System.out.println("Nouvel objet ajouté \n");
        }
        /** Consommation d'un objet **/
        else if(responseType ==5){
            //System.out.println(buffer);
            this.comJson.stringToConsommation(buffer,objetList );
            System.out.println("Consommation changée \n");
        }

    }

}