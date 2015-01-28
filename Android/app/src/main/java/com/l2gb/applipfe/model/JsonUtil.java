package com.l2gb.applipfe.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @file JsonUtil.java
 * @brief Cette classe permet de passer ou de recevoir des messages par le biais d'objet JSonObject
 * @version [0.1]
 * @date de création [19/01/2015]
 * @author(s) [Pierre Baranger]
 */
public class JsonUtil {

    private Map mapRequest;

    public JsonUtil()
    {
        this.mapRequest =  new HashMap();
        this.mapRequest.put(0,"GET_PROFIL_JOUR");
        this.mapRequest.put(1,"GET_PROFIL_SEMAINE");
        this.mapRequest.put(2,"GET_OBJETS");
    }

    public Map getMapRequest() {
        return this.mapRequest;
    }

    /**
     * @brief Permet de transfomer un objet Jours_Model en chaine de charactère.
     * @func joursModelToString(ProfilJour pj)
     * @param pj le Jours_Model à envoyé
     * @return root une chaine de caractère
     */
    public static String joursModelToString(Jours_Model pj){
        try {
            JSONObject root = new JSONObject();
            JSONObject request = new JSONObject();
            JSONObject data = new JSONObject();
            JSONArray creneauxList = new JSONArray();

            request.put("type", "SET_PROFIL_JOUR");

            data.put("nomProfil", pj.getName());

            /**
             * On met ca en commentaire pour l'instant, ces données là seront stockées dans
             * objet ou piece (voir avec perruchette).
             */
            //data.put("typeObjet", pj.getType());
            /** Si l'objet est un chauffage on veut les températures de confort et d'économie */
            /**if (pj.getType()==Constante.TYPE_CHAUFFAGE) {
                data.put("Tconfort", pj.getTemperature_confort());
                data.put("Teco", pj.getTemperature_economique());
            }**/

            /** Si la liste de créneaux n'est pas vide **/
            if(pj.getCreneauList().isEmpty()!=true) {
                for (int i = 0; i < pj.getCreneauList().size(); i++)
                {
                    JSONObject creneaux = new JSONObject();
                    JSONObject debut = new JSONObject();
                    JSONObject fin = new JSONObject();
                    creneaux.put("autorisation", pj.getCreneauList().get(i).getAutorisation());
                    debut.put("heure", pj.getCreneauList().get(i).gethDebut());
                    debut.put("minute", pj.getCreneauList().get(i).getmDebut());
                    creneaux.put("debut",debut);
                    fin.put("heure", pj.getCreneauList().get(i).gethFin());
                    fin.put("minute", pj.getCreneauList().get(i).getmFin());
                    creneaux.put("fin",fin);
                    creneauxList.put(creneaux);
                }
            }
            data.put("creneaux",creneauxList);
            request.put("data",data);
            root.put("request",request);
            return root.toString();
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @brief Permet de transfomer un objet Semaine_Model en chaine de charactère.
     * @func semaineModelToString(ProfilSemaine ps)
     * @param ps le Semaine_Model à envoyé
     * @return root une chaine de caractère
     */
    public String semaineModelToString(Semaine_Model ps)
    {
        try {
            JSONObject root = new JSONObject();
            JSONObject request = new JSONObject();
            JSONObject data = new JSONObject();
            JSONArray jourList = new JSONArray();
            request.put("type", "SET_PROFIL_JOUR");
            data.put("nomProfil", ps.getName());

            for (int i = 0; i < ps.getProfilJourList().size(); i++) {
                jourList.put(ps.getProfilJourList().get(i).getName());
            }

            data.put("jours",jourList);
            request.put("data",data);
            root.put("request",request);
            return root.toString();
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    /**
     * @brief Permet de transfomer une chaine de charactère en liste d'objet Jours_Model.
     * @func ArrayList<ProfilJour> stringToJoursModel(String chaine)
     * @param chaine la chaine de charactère à transformer
     * @return une liste de Jours_Model
     */
    public static ArrayList<Jours_Model> stringToJoursModel(String chaine)
    {
        ArrayList<Jours_Model> profilJourList = new ArrayList<Jours_Model>();
        try {
            JSONObject root = new JSONObject(chaine);
            JSONObject response = root.getJSONObject("response");
            JSONObject data = response.getJSONObject("data");

            JSONArray joursList = data.getJSONArray("jours");
            /** On parcours tous les jours de la chaine**/
            for(int j=0;j<joursList.length();j++) {
                Jours_Model pJour = new Jours_Model();
                JSONObject jours = joursList.getJSONObject(j);
                pJour.setName(jours.getString("nomProfil"));

                JSONArray creneauxList = jours.getJSONArray("creneaux");
                /** On parcours tous les créneaux contenues dans un jour **/
                for (int i = 0; i < creneauxList.length(); i++) {
                    JSONObject jCreneaux = creneauxList.getJSONObject(i);
                    JSONObject debut = jCreneaux.getJSONObject("debut");
                    JSONObject fin = jCreneaux.getJSONObject("fin");

                    /**Stock toutes les données d'un créneau**/
                    Creneaux_Model creneau = new Creneaux_Model();
                    creneau.setAutorisation(jCreneaux.getBoolean("autorisation"));
                    creneau.sethDebut(debut.getInt("heure"));
                    creneau.setmDebut(debut.getInt("minute"));
                    creneau.sethFin(fin.getInt("heure"));
                    creneau.setmFin(fin.getInt("minute"));

                    /** Ajoute le créneau horaire à la liste de créneau du profil jour**/
                    pJour.getCreneauList().add(i, creneau);
                }
                profilJourList.add(j,pJour);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return profilJourList;
    }

    /**
     * @brief Permet de transfomer une chaine de charactère en liste d'objet Semaine_Model.
     * @func ArrayList<ProfilSemaine> stringToSemaineModel(String chaine)
     * @param chaine la chaine de charactère à transformer
     * @return une liste de ProfilSemaine
     */
    public ArrayList<Semaine_Model> stringToSemaineModel(String chaine)
    {
        ArrayList<Semaine_Model> profilSemaineList = new ArrayList<Semaine_Model>();
        try{
            JSONObject root = new JSONObject(chaine);
            JSONObject response = root.getJSONObject("response");
            JSONObject data = response.getJSONObject("data");
            JSONArray semaineList = data.getJSONArray("semaines");

            /** On parcours toutes les semaines de la chaine**/
            for(int j=0;j<semaineList.length();j++) {
                JSONObject semaine = semaineList.getJSONObject(j);

                Semaine_Model profilSemaine = new Semaine_Model();
                profilSemaine.setName(semaine.getString("nomProfil"));

                JSONArray jourList = semaineList.getJSONArray(j);

                /** On ajoute tous les profilJour dans la semaine **/
                for(int i=0;i<jourList.length();i++)
                {
                    Jours_Model profilJour = new Jours_Model(jourList.get(i).toString());
                  /** on ajoute le profil jour dans la liste de jours **/
                    profilSemaine.getProfilJourList().add(i,profilJour);
                }
            profilSemaineList.add(j,profilSemaine);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return profilSemaineList;
    }



    /**
     * @brief Permet de transfomer une chaine de charactère en liste d'objet ProfilObjet.
     * @func ArrayList<ProfilObjet> stringToObjetModel(String chaine)
     * @param chaine :la chaine de charactère à transformer
     * @return une liste de ProfilObjet
     */
    public ArrayList<Objet_Model> stringToObjetModel(String chaine)
    {
        ArrayList<Objet_Model> profilObjetList = new ArrayList<Objet_Model>();
        try{
            JSONObject root = new JSONObject(chaine);
            JSONObject response = root.getJSONObject("response");
            JSONObject data = response.getJSONObject("data");
            JSONArray objetList = data.getJSONArray("objets");

            /** On parcours toutes les objets de la chaine**/
            for(int j=0;j<objetList.length();j++) {
                JSONObject objet = objetList.getJSONObject(j);
                JSONObject nomObjet = objet.getJSONObject("nomObjet");
                JSONObject planning = objet.getJSONObject("planning");

                Objet_Model profilObjet = new Objet_Model(nomObjet.getString("nomObjet"));
                profilObjet.getProfilSemaine().setName(planning.getString("planning"));
                profilObjetList.add(j,profilObjet);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return profilObjetList;
    }

    /**
     * @brief Permet de supprimer un profilJour
     * @func removeJoursModel(ProfilJour pJour)
     * @param pJour la profilJour à supprimer
     * @return none
     */
    public void removeJoursModel(Jours_Model pJour)
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("nomProfil",pJour.getName());
            request.put("type","RM_PROFIL_JOUR");
            request.put("data",data);
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Permet de supprimer un profilSemaine
     * @func removeSemaineModel(ProfilSemaine pSemaine)
     * @param pSemaine la profilSemaine à supprimer
     * @return none
     */
    public void removeSemaineModel(Semaine_Model pSemaine)
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("nomProfil",pSemaine.getName());
            request.put("type","RM_PROFIL_JOUR");
            request.put("data",data);
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief permet de demander à la centrale les objets ProfilSemaine enregistrés
     * @func getSemaineModel()
     * @return none
     */
    public String getSemaineModel()
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        try {
            request.put("type", "GET_PROFIL_SEMAINE");
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }

    /**
     * @brief permet de demander à la centrale les objets ProfilJour enregistrés
     * @func getJoursModel()
     * @return none
     */
    public String getJoursModel()
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        try {
            request.put("type", "GET_PROFIL_JOUR");
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }

    /**
     * @brief permet de demander à la centrale les objets ProfilObjet enregistrés
     * @func getObjetModel()
     * @return none
     */
    public String getObjetModel()
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        try {
            request.put("type", "GET_OBJETS");
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }

    public int readResponse(String chaine)
    {
        String requestType;
        try {
            JSONObject root = new JSONObject(chaine);
            JSONObject response = root.getJSONObject("response");
            JSONObject type  = response.getJSONObject("type");
            requestType = type.getString("type");

            /** Si la reponse est de type GET_PROFIL_JOUR**/
            if(requestType.equals(mapRequest.get(0))){
                //ArrayList<Jours_Model> joursList = new ArrayList<Jours_Model>();
                return 1;
            }

            /** Si la reponse est de type GET_PROFIL_SEMAINE**/
            else if(requestType.equals(mapRequest.get(1))){
                //ArrayList<Semaine_Model> semaineList = new ArrayList<Semaine_Model>();
                return 2;
            }

            /** Si la reponse est de type GET_OBJETS**/
            else if(requestType.equals(mapRequest.get(2))){
                //ArrayList<Objet_Model> objetList = new ArrayList<Objet_Model>();
                return 3;
            }


        }catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
