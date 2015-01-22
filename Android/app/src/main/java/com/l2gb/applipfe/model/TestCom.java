package com.l2gb.applipfe.model;

/**
 * Created by pierrebaranger1 on 21/01/2015.
 */
public class TestCom {
    private Jours_Model profilJour;
    private JsonUtil jsonUtil;
    private String chaine;

    public String getChaine() {
        return chaine;
    }

    public TestCom()
    {
        Creneaux_Model creneau = new Creneaux_Model(8,10);
        this.profilJour = new Jours_Model();
        this.profilJour.setName("Lundi");
        this.profilJour.getCreneauList().add(0,creneau);
        this.chaine=jsonUtil.profilJourToString(this.profilJour);

    }


}
