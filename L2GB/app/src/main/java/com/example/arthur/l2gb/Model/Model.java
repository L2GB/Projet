package com.example.arthur.l2gb.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by arthur on 15/01/2015.
 */
public class Model implements Parcelable {
    private ArrayList<Objet_Model> objet_model;
    private ArrayList<Jours_Model> jours_Model;
    private ArrayList<Semaine_Model> profilSemaine_model;

    public Model(){
       this.objet_model = new ArrayList<Objet_Model>();
       this.jours_Model = new ArrayList<Jours_Model>();
       this.profilSemaine_model = new ArrayList<Semaine_Model>();
       creerfakeModel();
    }

    private void creerfakeModel(){
        Creneaux_Model creneauxModelTravail1 = new Creneaux_Model(6,8);
        Creneaux_Model creneauxModelTravail2 = new Creneaux_Model(16,21);

        Creneaux_Model creneauxModelVacance1 = new Creneaux_Model(12,15);
        Creneaux_Model creneauxModelVacance2 = new Creneaux_Model(20,23);

        Jours_Model joursTravail = new Jours_Model("Travail");
        joursTravail.getCreneauList().add(creneauxModelTravail1);
        joursTravail.getCreneauList().add(creneauxModelTravail2);
        this.jours_Model.add(joursTravail);


        Jours_Model joursVacance = new Jours_Model("Vacance");
        joursVacance.getCreneauList().add(creneauxModelVacance1);
        joursVacance.getCreneauList().add(creneauxModelVacance2);
        this.jours_Model.add(joursVacance);

        Semaine_Model semaineClassique = new Semaine_Model("Classique");
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursTravail);
        semaineClassique.getProfilJourList().add(joursVacance);
        semaineClassique.getProfilJourList().add(joursVacance);
        this.profilSemaine_model.add(semaineClassique);

        Semaine_Model semaineSpe = new Semaine_Model("Spécial");
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursVacance);
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursTravail);
        semaineSpe.getProfilJourList().add(joursVacance);
        semaineSpe.getProfilJourList().add(joursVacance);
        this.profilSemaine_model.add(semaineSpe);

        Semaine_Model semaineSki = new Semaine_Model("Ski");
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        semaineSki.getProfilJourList().add(joursTravail);
        this.profilSemaine_model.add(semaineSki);


        Objet_Model lumiereSalon = new Objet_Model("Lumiere salon", semaineSpe);
        lumiereSalon.setConsommation(100);
        lumiereSalon.setInconnu(true);
        lumiereSalon.setConnecte(true);
        Objet_Model chauffageSalon = new Objet_Model("Chauffage salon", semaineSpe);
        chauffageSalon.setConsommation(75);
        chauffageSalon.setInconnu(true);
        chauffageSalon.setConnecte(true);
        Objet_Model chauffageChambre = new Objet_Model("chauffage chambre", semaineClassique);
        chauffageChambre.setConsommation(50);
        chauffageChambre.setInconnu(true);
        chauffageChambre.setConnecte(true);
        Objet_Model lumiereChambre = new Objet_Model("Lumiere chambre", semaineSki);
        lumiereChambre.setConsommation(10);
        lumiereChambre.setInconnu(true);
        lumiereChambre.setConnecte(true);
        Objet_Model console = new Objet_Model("Console");
        console.setInconnu(true);
        console.setConnecte(false);
        Objet_Model iDinconnu = new Objet_Model("F4F33F3F3F");
        iDinconnu.setInconnu(false);
        iDinconnu.setConnecte(false);
        this.objet_model.add(console);
        this.objet_model.add(iDinconnu);
        this.objet_model.add(lumiereSalon);
        this.objet_model.add(chauffageSalon);
        this.objet_model.add(chauffageChambre);
        this.objet_model.add(lumiereChambre);
    }

    public ArrayList<Objet_Model> getObjet_model() {
        return objet_model;
    }

    public void setObjet_model(ArrayList<Objet_Model> objet_model) {
        this.objet_model = objet_model;
    }

    public ArrayList<Jours_Model> getJours_Model() {
        return jours_Model;
    }

    public void setJours_Model(ArrayList<Jours_Model> jours_Model) {
        this.jours_Model = jours_Model;
    }

    public ArrayList<Semaine_Model> getProfilSemaine_model() {
        return profilSemaine_model;
    }

    public void setProfilSemaine_model(ArrayList<Semaine_Model> profilSemaine_model) {
        this.profilSemaine_model = profilSemaine_model;
    }

    protected Model(Parcel in) {
        if (in.readByte() == 0x01) {
            objet_model = new ArrayList<Objet_Model>();
            in.readList(objet_model, Objet_Model.class.getClassLoader());
        } else {
            objet_model = null;
        }
        if (in.readByte() == 0x01) {
            jours_Model = new ArrayList<Jours_Model>();
            in.readList(jours_Model, Jours_Model.class.getClassLoader());
        } else {
            jours_Model = null;
        }
        if (in.readByte() == 0x01) {
            profilSemaine_model = new ArrayList<Semaine_Model>();
            in.readList(profilSemaine_model, Semaine_Model.class.getClassLoader());
        } else {
            profilSemaine_model = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (objet_model == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(objet_model);
        }
        if (jours_Model == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(jours_Model);
        }
        if (profilSemaine_model == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(profilSemaine_model);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Model> CREATOR = new Parcelable.Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}