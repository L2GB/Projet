package com.l2gb.applipfe.model.communication;

/**
 * @file RepartiteurConnexion.java
 *
 * @brief Cette classe gère la connexion avec la tablette.
 * @version [0.1]
 * @date de création [19/01/2015]
 * @author(s) [Pierre Baranger]
 * */

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.os.Handler;


public class RepartiteurConnexion extends Thread {
    /*
     * -------------------------------------------------------------- Attributs
     * --------------------------------------------------------------
     */
    private String adresseIP;
    private int port;
    public static boolean connecte;
    Communication client;
    private Handler uiHandler;
    private CommunicationSortie flux_sortie;


	/*
	 * --------------------------------------------------------------
	 * Constructeur
	 * --------------------------------------------------------------
	 */
    /**
     * @brief Constructeur
     * @func RepartiteurConnexion(ControleurActivite CA, String ip, int port,Handler uiHandler)
     * @param ip : ip du serveur auquel on veut se connecter
     * @param port : port du serveur auquel on veut se connecter
     * @param uiHandler : Handler présent dans le thread UI
     * @retval none
     */
    public RepartiteurConnexion(String ip, int port,Handler uiHandler) {
        this.setAdresseIP(ip);
        this.port = port;
        this.uiHandler=uiHandler;
    }


    public void run() {

        // Tant que le client n'est pas connecté on ne fait rien:
        while (!client.connecte) {
            try {
                Thread.sleep(20);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Attention il faut que la connexion ait d'abord été établie
        while (client.connecte) {
            try {
                // tant qu'il n'y a pas de données dans le flux d'entrée on ne
                // fait rien
                while (client.connecte
                        && client.socket.getInputStream().available() == 0
                        ) {
                    Thread.sleep(60);
                    //client.connecte=checkConnexion(client);//ne peut plus recevoir quand on active
                }
                //CA.afficherParametres();
                if (client.connecte) {
                    client.RecevoirCommande();
                }
                // Il faut vérifier si le serveur est toujours présent sinon on
                // se déconnecte:

            } catch (InterruptedException e) {
                System.out.println("Affichage de la reception impossible\n");
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //Le serveur s'est déconnecté on lance la page de connexion:
        uiHandler.sendEmptyMessage(0);
    }
    /**
     * @brief Verifie que la connexion marche toujours
     * @func checkConnexion(Communication client)
     * @param client : client contient la socket client qui se connecte au serveur
     * @retval none
     */
    public boolean checkConnexion(Communication client){
        try {
            if(client.in.read()==-1){
                return false;
            }else{
                return true;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }


    }
    /**
     * @brief Verifie que l'ip est valide
     * @func boolean checkIp(String ip)
     * @param ip : ip à verifier
     * @retval boolean : valide ou non
     */
    public boolean checkIp(String ip) {
        if (ip == null || ip.isEmpty())
            return false;
        ip = ip.trim();
        if ((ip.length() < 6) & (ip.length() > 15))
            return false;

        try {
            Pattern pattern = Pattern
                    .compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        } catch (PatternSyntaxException ex) {
            return false;
        }
    }

    /**
     * @brief Initialise la connexion avec le serveur
     * @func void initialiserConnexion()
     * @throws IOException
     * @retval none
     */
    public void initialiserConnexion()  {
        client = new Communication(uiHandler);
        // On est obliger d'executer les instructions dans un thread sinon ça
        // plante
        Thread connection = new Thread(new Connexion(client,getAdresseIP(), port));
        connection.start();
        try {
            // On attend la fin du thread pour notifier si la connection à
            // réussi:
            connection.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            flux_sortie= new CommunicationSortie(
                    client.socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    /**
     * @brief ferme la connexion avec le serveur
     * @func void fermerConnection()
     * @retval none
     */
    public void fermerConnection() {
        client.deconnecterServeur();

    }



    /***************** COMMANDES ******************/
    /**
     * @brief envoie la commande de demande de carte au serveur
     * @func void demanderCarte()
     * @retval none
     * @throws IOException
     */
    public void demanderCarte() throws IOException {
        byte[] donnees_brutes = new byte[26];

        // Ecriture du numéro de commande :1
        for (int i = 0; i < 4; i++)
            donnees_brutes[i] = (byte) ((int) 1 >>> ((3 - i) * 8));


        flux_sortie.envoyerInstruction(donnees_brutes);
    }

    /**
     * @brief envoie la commande de changement de mode au serveur
     * @func void changerMode(int mode)
     * @param mode: cet entier identifie le mode
     * @retval none
     */
    public void changerMode(int mode)  {
        byte[] donnees_brutes = new byte[26];

        // Ecriture du numéro de commande :2
        for (int i = 0; i < 4; i++)
            donnees_brutes[i] = (byte) ((int) 2 >>> ((3 - i) * 8));

        // Ecriture du numéro de Mode :
        for (int i = 0; i < 4; i++)
            donnees_brutes[i + 4] = (byte) (mode >>> ((3 - i) * 8));

        flux_sortie.envoyerInstruction(donnees_brutes);
    }

    /***
     * @brief envoie la commande de demande de pause au serveur
     * @func void pauseDeplacement()
     * @retval none
     * @throws IOException
     */
    public void pauseDeplacement() throws IOException {
        byte[] donnees_brutes = new byte[26];

        // Ecriture du numéro de commande :3
        for (int i = 0; i < 4; i++)
            donnees_brutes[i] = (byte) ((int) 3 >>> ((3 - i) * 8));

        flux_sortie.envoyerInstruction(donnees_brutes);
    }

    /**
     * @brief envoie la commande de lancement de déplacement
     * @func void lancerDeplacement(int id)
     * @param id : identifie la salle à laquelle doit se rendre le robot
     * @retval none
     */
    public void lancerDeplacement(int id)
            throws IOException {
        byte[] donnees_brutes = new byte[26];

        // Ecriture du numéro de commande :4
        for (int i = 0; i < 4; i++)
            donnees_brutes[i] = (byte) ((int) 4 >>> ((3 - i) * 8));

        // Ecriture du numéro de l'id:
        for (int i = 0; i < 4; i++)
            donnees_brutes[i + 4] = (byte) (id >>> ((3 - i) * 8));

        flux_sortie.envoyerInstruction(donnees_brutes);
    }

    /**
     * @brief envoie la commande de reprise de déplacement
     * @func void repriseDeplacement()
     * @retval none
     * @throws IOException
     */
    public void repriseDeplacement() throws IOException {
        byte[] donnees_brutes = new byte[26];

        // Ecriture du numéro de commande :5
        for (int i = 0; i < 4; i++)
            donnees_brutes[i] = (byte) ((int) 5 >>> ((3 - i) * 8));

        flux_sortie.envoyerInstruction(donnees_brutes);
    }

    /**
     * @brief envoie la commande de demande de déplacement au serveur
     * @func void commandeDeplacement(int x, int y)
     * @param x : abscisse de la direction
     * @param y : ordonnée de la direction
     * @retval none
     * @throws IOException
     */
    public void commandeDeplacement(int x, int y) throws IOException {
        byte[] donnees_brutes = new byte[26];
        // Ecriture du numéro de commande :6
        for (int i = 0; i < 4; i++)
            donnees_brutes[i] = (byte) ((int) 6 >>> ((3 - i) * 8));
        // Ecriture de la vitesse:
        for (int i = 0; i < 4; i++)
            donnees_brutes[i + 4] = (byte) ((int) x >>> ((3 - i) * 8));
        // Ecriture de la direction:
        for (int i = 0; i < 4; i++)
            donnees_brutes[i + 8] = (byte) ((int) y >>> ((3 - i) * 8));
        flux_sortie.envoyerInstruction(donnees_brutes);
    }

    /**
     * @brief envoie la commande qui signale l'envoie d'un mail
     * @func void signalerEnvoiMail()
     * @retval none
     * @throws IOException
     */
    public void signalerEnvoiMail() throws IOException {
        byte[] donnees_brutes = new byte[26];
        // Ecriture du numéro de commande :7
        for (int i = 0; i < 4; i++)
            donnees_brutes[i] = (byte) ((int) 7 >>> ((3 - i) * 8));
        flux_sortie.envoyerInstruction(donnees_brutes);
    }

    /**
     * @brief envoie la commande d'arret d'urgence
     * @func void demanderArretUrgenceDistant()
     * @retval none
     * @throws IOException
     */
    public void demanderArretUrgenceDistant() throws IOException {
        byte[] donnees_brutes = new byte[26];

        // Ecriture du numéro de commande :9
        for (int i = 0; i < 4; i++)
            donnees_brutes[i] = (byte) ((int) 9 >>> ((3 - i) * 8));


        flux_sortie.envoyerInstruction(donnees_brutes);
    }


    /************** FIN DE COMMANDES ******************/

    /**
     * @func String getAdresseIP()
     * @retval String adresseIP
     */
    public String getAdresseIP() {
        return adresseIP;
    }

    /**
     * @func void setAdresseIP(String adresseIP)
     * @retval none
     */
    public void setAdresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
    }


}