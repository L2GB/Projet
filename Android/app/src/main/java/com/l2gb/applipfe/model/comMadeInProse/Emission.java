package com.l2gb.applipfe.model.comMadeInProse;


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
     * \brief Constructeur de l'Emission
     * <p/>
     * Prend en paramètre le socket et lance le thread d'émission.
     * <p/>
     * \param socket Socket utilisé pour envoyer les données.
     */
    public Emission(Socket socket) {
        this.setName("ThreadEmission");
        this.mySocket = socket;
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
            out.println(message);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /***********************************************************
     TODO: TOUTE les méthodes d'émission se trouve ici
     Chaque méthode va modifier l'attibut message puis rappeler la méthode run, Afin d'envoyer la
     requête.
     ***********************************************************/




}
