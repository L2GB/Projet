package com.l2gb.applipfe.model.communication;

/**
 * @file Connexion.java
 *
 * @brief Cette classe sert à initialiser la connexion d'une socket client avec un serveur.
 * @version [0.1]
 * @date de création [19/01/2015]
 * @author(s) [Pierre Baranger]
 */
public class Connexion implements Runnable {
    private Communication client;
    private String adresseIp;
    private int port;

    /**
     * @brief Constructeur
     * @func Connexion(Communication c,String adresseIp,int port)
     * @retval none
     */

    public Connexion(Communication c,String adresseIp,int port) {
        this.client = c;
        this.adresseIp=adresseIp;
        this.port=port;
    }
    /**
     * @brief Méthode run du thread de connexion
     * @func run()
     * @retval none
     */
    public void run() {
        client.parametrerCommunication(adresseIp, port);
        client.connecterServeur();
        if (client.connecte) {
            RepartiteurConnexion.connecte = true;
        }

    }
}