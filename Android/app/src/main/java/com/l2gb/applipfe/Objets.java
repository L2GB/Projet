package com.l2gb.applipfe;

/**
 * Created by arthur on 12/01/2015.
 */
public class Objets {
    public int getId_objet() {
        return id_objet;
    }

    public int getConsommatin() {
        return consommation;
    }

    public Type_Objet getType_objet() {
        return type_objet;
    }

    public char getPiece() {
        return piece;
    }

    public void setId_objet(int id_objet) {
        this.id_objet = id_objet;
    }

    public void setConsommatin(int consommation) {
        this.consommation = consommation;
    }

    public void setType_objet(Type_Objet type_objet) {
        this.type_objet = type_objet;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }

    private int id_objet;
    private int consommation;
    private Type_Objet type_objet;
    private char piece;
}
