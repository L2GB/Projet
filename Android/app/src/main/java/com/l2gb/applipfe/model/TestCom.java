package com.l2gb.applipfe.model;

/**
 * Created by pierrebaranger1 on 21/01/2015.
 */
public class TestCom {
    private ProfilJour profilJour;
    private JsonUtil jsonUtil;
    private String chaine;

    public String getChaine() {
        return chaine;
    }

    public TestCom()
    {
        Creneau creneau = new Creneau(8,10);
        this.profilJour = new ProfilJour();
        this.profilJour.setName("Lundi");
        this.profilJour.getCreneauList().add(0,creneau);
        this.chaine=jsonUtil.profilJourToString(this.profilJour);

    }


}
