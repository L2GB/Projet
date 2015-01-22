package com.l2gb.applipfe.model.communication;

/**
 * @file CommunicationSortie.java
 *
 * @brief Cette classe sert à personnaliser et accéder facilement au flux de sortie de la socket,
 * et envoyer des trames dans ce flux.
 * @version [0.1]
 * @date de création [19/01/2015]
 * @author(s) [Pierre Baranger]
 */
import java.io.IOException;
import java.io.OutputStream;

public class CommunicationSortie extends OutputStream {
    private OutputStream fluxSortie;

    /**
     * @brief Constructeur
     * @func public CommunicationSortie(OutputStream flux)
     * @retval none
     */
    public CommunicationSortie(OutputStream flux) {
        fluxSortie = flux;
    }

    public void write(int arg0) throws IOException {
        throw new Error("Cannot be called");
    }

    /**
     * @brief Cette méthode permet d'envoyer au serveur la trame prise en paramêtre.
     * @func void envoyerInstruction(byte[] donnees_brutes)
     * @param donnees_brutes: tableau dans lequelle on stocke la trame
     * @retval none
     */
    public void envoyerInstruction(byte[] donnees_brutes) {
        try {
            fluxSortie.write(donnees_brutes);
        } catch (IOException e) {
            System.out.println("\nErreur: Envoi impossible\n");
            e.printStackTrace();
        }
    }
}