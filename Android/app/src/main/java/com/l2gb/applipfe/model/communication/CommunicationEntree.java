package com.l2gb.applipfe.model.communication;

/**
 * @file CommunicationEntree.java
 *
 * @brief Cette classe sert à personnaliser et accéder facilement au flux d'entrée de la socket,
 * et lire les trames reçues dans ce flux.
 * @version [0.1]
 * @date de création [19/01/2015]
 * @author(s) [Pierre Baranger]
 */

import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;



public class CommunicationEntree extends InputStream {
    private Handler uiHandler;// permet d'envoyer des messages associé à un thread
    private InputStream fluxEntree;

    /**
     * @brief Constructeur
     * @func CommunicationEntree(InputStream ce_flux)
     * @param flux: flux d'entrée
     * @retval none
     */

    public CommunicationEntree(InputStream flux,Handler uiHandler) {
        super();
        fluxEntree = flux;
        this.uiHandler=uiHandler;
    }


    /**
     * Reads a single byte from this stream and returns it as an integer in the
     * range from 0 to 255. Returns -1 if the end of the stream has been
     * reached. Blocks until one byte has been read, the end of the source
     * stream is detected or an exception is thrown.
     *
     * @throws IOException
     *             if the stream is closed or another IOException occurs.
     */
    public int read() throws IOException {
        throw new Error("Cannot be called");
    }

    /**
     * @brief Cette méthode lit la trame reçue et applique immédiatement les commandes et consignes reçues par la trame.
     * @func lireCommande()
     * @throws IOException
     * @retval int commande : commande reçue
     */
    public void lireCommande() throws IOException  {



    }

}