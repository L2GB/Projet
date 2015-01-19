package com.example.arthur.l2gb.Model;

/**
 * Created by arthur on 15/01/2015.
 */
public class Objet_Model {
    private String name;
    private int id;
    private String piece;
    private boolean estConnecte;
    private Semaine_Model semaine_model;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public boolean isEstConnecte() {
        return estConnecte;
    }

    public void setEstConnecte(boolean estConnecte) {
        this.estConnecte = estConnecte;
    }

    public Semaine_Model getSemaine_model() {
        return semaine_model;
    }

    public void setSemaine_model(Semaine_Model semaine_model) {
        this.semaine_model = semaine_model;
    }
}
