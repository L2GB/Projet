package com.l2gb.applipfe.model.communication;

/**
 * @file Communication.java
 * @brief Cette classe sert à manipuler les socket et gérer une connexion socket client avec un serveur.
 * @version [0.1]
 * @date de création [19/01/2015]
 * @author(s) [Pierre Baranger]
 */

import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Communication {
	/*
	 * --------------------------------------------------------------
	 * Attributs
	 * --------------------------------------------------------------
	 */

    public boolean connecte = false;// indique si le Communication est connecte ou non
    public Socket socket = null;
    private PrintWriter out = null;
    public BufferedReader in = null;
    private String host = null;
    public InputStream fluxEntree;
    private int port;
    private CommunicationEntree flux_entree;
    private Handler uiHandler;


	/*
	 * --------------------------------------------------------------
	 * Méthodes
	 * --------------------------------------------------------------
	 */

    /**
     * @brief Constructeur de la classe
     * @func Communication(Handler uiHandler)
     * @param uiHandler: Handler présent dans le thread UI
     * @retval none
     */
    public Communication(Handler uiHandler){
        this.uiHandler=uiHandler;
    }
    /**
     * @brief Permet d'assigner une ip et un port au client socket
     * @func void parametrerCommunication(String host, int port)
     * @param host:adresse ip du serveur
     * @param port: port du serveur
     * @retval none
     */
    public void parametrerCommunication(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * @brief Connecte la socket au serveur
     * @func void connecterServeur()
     * @retval none
     */
    public void connecterServeur() {

        try {
            if (socket == null) {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 5000);
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Connecté");
                this.connecte=true;//le Communication est maintenant connecté
            }
        } catch (IOException e) {
            System.out.println("Connection impossible");
            e.printStackTrace();
        }

    }

    /**
     * @brief Déconnecte la socket du serveur
     * @func void deconnecterServeur()
     * @retval none
     */
    public void deconnecterServeur() {
        if (socket != null) {
            if (socket.isConnected()) {
                try {
                    this.connecte = false;// on indique avant que le client
                    // ne sera plus connecté
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @brief Permet de recevoir une trame et lire la commande contenue
     * @func int RecevoirCommande()
     * @retval int commande
     */
    public void RecevoirCommande() throws IOException {
        flux_entree = new CommunicationEntree(
                socket.getInputStream(),uiHandler);
        flux_entree.lireCommande();
    }
}



