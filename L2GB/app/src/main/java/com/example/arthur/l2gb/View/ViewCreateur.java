package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.Model.Semaine_Model;
import com.example.arthur.l2gb.R;

public class ViewCreateur {

    Model model = null;

    private Consommation_View consommationView = null;

    private JoursConfiguration_View joursConfigurationView = null;

    private JoursList_View joursListView = null;

    private  Planning_View planningView = null;

    private SemaineConfiguration_View semaineConfigurationView = null;

    private SemaineList_View semaineListView = null;


    public ViewCreateur(Model model) {
        this.model = model;
        this.consommationView = new Consommation_View(this.model);
        this.joursConfigurationView = new JoursConfiguration_View(this.model,this.);
    }

}
