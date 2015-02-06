package com.example.arthur.l2gb.Model;

/**
 * Created by pierrebaranger1 on 19/01/2015.
 * Classe contenant toutes les constantes utilisées par le package model
 */
public class Constante {

    /** Objet de type CHAUFFAGE. */
    public static final String TYPE_CHAUFFAGE = "CHAUFFAGE";
    /** Objet de type PRISE. */
    public static final String TYPE_PRISE = "PRISE";
    /** Temperature de confort par defaut */
    public static final int TEMP_CONFORT = 20;
    /** Temperature economique par defaut */
    public static final int TEMP_ECO = 17;
    /** Adresse ip du serveur par defaut **/
    public static final String IP_SERVEUR = "192.168.0.21";
    /** Port de connexion du serveur par defaut **/
    public static final int PORT_SERVEUR = 2048;
    /**
     * Constructeur vide.
     */
    private Constante() {
        throw new UnsupportedOperationException();
    }

}
