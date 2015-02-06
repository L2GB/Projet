package com.example.arthur.l2gb.Model.Communication;

/**
 * Created by pierrebaranger1 on 27/01/2015.
 */

import java.io.IOException;
import java.net.Socket;

/**
 * @brief Classe singleton qui implémente la création du socket
 */
public class SocketCreation extends Thread
{

    /**
     * ipServeur
     */
    private String ipServeur;
    /**
     * portServeur
     */
    private int portServeur;
    /**
     * mySocket
     * Socket privé qui sert à ouvrir la connexion
     */
    private static Socket mySocket;
    /**
     * isConnected
     * Enregistre localement l'état de la connexion
     */
    private static boolean isConnected;

    /**
     * \@rief Constructeur de SocketCreation
     *
     * Prends en compte l'IP et le port donnés par l'utilisateur et ouvre un socket.
     *
     * @param ip IP du robot renseigné par l'utilisateur
     * @param port Port du robot renseigné par l'utilisateur
     */
    public SocketCreation(String ip, int port)
    {
        ipServeur = ip;
        portServeur = port;
        this.start();
    }

    @Override
    /**
     * @brief Méthode appelée au lancement du Thread
     *
     * Méthode appelée par le constructeur de SocketCreation et qui ouvre le socket.
     * Il récupère également un booléen qui indique si la connexion a réussi.
     */
    public void run()
    {
        try
        {
            mySocket = new Socket(ipServeur, portServeur);
            isConnected = true;
        } catch (IOException e)
        {
            isConnected = false;

        }
    }

    /**
     * @brief Fermeture du socket
     */
    public static void socketClosing()
    {
        try
        {
            mySocket.shutdownInput();
            mySocket.shutdownOutput();
            mySocket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @brief retourne l'IP de l'instance donnée de SocketCreation
     * @return ipServeur
     */
    public String getIP()
    {
        return ipServeur;
    }

    /**
     * @brief retourne le port de l'instance donnée de SocketCreation
     * @return portServeur
     */
    public int getPort()
    {
        return portServeur;
    }

    /**
     * @brief retourne le booleen de connexion
     * @return isConnected
     */
    public static boolean isConnected()
    {
        return isConnected;
    }

    /**
     * @brief Permet de récupérer le socket créé
     */
    public static Socket getSocket()
    {
        return mySocket;
    }

}