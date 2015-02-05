package com.example.arthur.l2gb.Model;


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
        this.mapRequest.put(3,"NEW_OBJET");
        this.mapRequest.put(4,"GET_CONSOMMATION");
    }

    public Map getMapRequest() {
        return this.mapRequest;
    }

    /**
     * @brief permet de demander à la centrale les objets ProfilJour enregistrés
     * @func getJoursModel()
     * @return root
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
     * @brief Permet de transfomer une chaine de charactère en liste d'objet Jours_Model.
     * @func ArrayList<ProfilJour> stringToJoursModel(String chaine)
     * @param chaine la chaine de charactère à transformer
     */
    public static void stringToJoursModel(String chaine, ArrayList<Jours_Model> profilJourList)
    {
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
     * @brief Permet de supprimer un profilJour
     * @func removeJoursModel(ProfilJour pJour)
     * @param pJour le profilJour à supprimer
     * @return root
     */
    public String removeJoursModel(Jours_Model pJour)
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
        return root.toString();
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
     * @brief Permet de transfomer une chaine de charactère en liste d'objet Semaine_Model.
     * @func ArrayList<ProfilSemaine> stringToSemaineModel(String chaine)
     * @param chaine la chaine de charactère à transformer
     * @return une liste de ProfilSemaine
     */
    public void stringToSemaineModel(String chaine, ArrayList<Semaine_Model> profilSemaineList )
    {

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

                JSONArray jours = semaine.getJSONArray("jours");

                /** On ajoute tous les profilJour dans la semaine **/
                for(int i=0;i<jours.length();i++)
                {
                    Jours_Model profilJour = new Jours_Model(jours.get(i).toString());
                    /** on ajoute le profil jour dans la liste de jours **/
                    profilSemaine.getProfilJourList().add(i,profilJour);
                }
                profilSemaineList.add(j,profilSemaine);
                //System.out.println("JsonUtil "+profilSemaineList.get(j).getName());
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

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
            request.put("type", "SET_PROFIL_SEMAINE");
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
     * @brief Permet de supprimer un profilSemaine
     * @func removeSemaineModel(ProfilSemaine pSemaine)
     * @param pSemaine la profilSemaine à supprimer
     * @return root
     */
    public String removeSemaineModel(Semaine_Model pSemaine)
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
        return root.toString();
    }


    /**
     * @brief permet de demander à la centrale les objets ProfilObjet enregistrés
     * @func getObjetModel()
     * @return root
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

    /**
     * @brief Permet de transfomer une chaine de charactère en liste d'objet ProfilObjet.
     * @func ArrayList<ProfilObjet> stringToObjetModel(String chaine)
     * @param chaine :la chaine de charactère à transformer
     * @return une liste de ProfilObjet
     */
    public void stringToObjetModel(String chaine,ArrayList<Objet_Model> profilObjetList)
    {
        try{
            JSONObject root = new JSONObject(chaine);
            JSONObject response = root.getJSONObject("response");
            JSONObject data = response.getJSONObject("data");
            JSONArray objetList = data.getJSONArray("objets");

            /** On parcours toutes les objets de la chaine**/
            for(int j=0;j<objetList.length();j++) {
                JSONObject objet = objetList.getJSONObject(j);

                Objet_Model profilObjet = new Objet_Model(objet.getString("nomObjet"));
                profilObjet.getProfilSemaine().setName(objet.getString("planning"));
                profilObjet.setType(objet.getString("typeObjet"));
                profilObjet.setInconnu(objet.getBoolean("inconnu"));
                profilObjet.setConnecte(objet.getBoolean("connecte"));

                profilObjet.setInstanceNum(objet.getInt("instanceNum"));
                profilObjet.setDeviceId(objet.getInt("deviceId"));
                profilObjet.setType(objet.getString("typeObjet"));
                /** Si l'objet est de type chauffage on regarde les températures **/
                if (objet.getString("typeObjet").equals(Constante.TYPE_CHAUFFAGE)) {
                    profilObjet.setTemperature_confort(objet.getInt("Tconfort"));
                    profilObjet.setTemperature_economique(objet.getInt("Teco"));
                }
                else if (objet.getString("typeObjet").equals(Constante.TYPE_PRISE)) {
                    profilObjet.setAllume(objet.getBoolean("allume"));
                }
                System.out.println("TEUUUUUUB");
                profilObjetList.add(j,profilObjet);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief requête de la centrale pour indiquer la présence d'un nouvel objet
     * @func newObjet(Objet_Model objet)
     * @return root
     */
    public void stringToNewObjet(String chaine,ArrayList<Objet_Model> objet_modelArrayList)
    {
        try {
            JSONObject root = new JSONObject(chaine);
            JSONObject request = root.getJSONObject("request");
            JSONObject data = request.getJSONObject("data");
            Integer instanceNum = data.getInt("instanceNum");

            Objet_Model objet = new Objet_Model(instanceNum.toString());
            objet.setType(data.getString("typeObjet"));
            objet.setInstanceNum(data.getInt("instanceNum"));
            objet.setDeviceId(data.getInt("deviceId"));

            objet_modelArrayList.add(objet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * @brief Permet de transfomer un Objet en chaine de charactère.
     * @func objetModelToString(Objet_Model objet)
     * @param objet l'Objet_Model à envoyé
     * @return root une chaine de caractère
     */
    public String objetModelToString(Objet_Model objet)
    {
        try {
            JSONObject root = new JSONObject();
            JSONObject request = new JSONObject();
            JSONObject data = new JSONObject();
            request.put("type", "SET_OBJET");

            data.put("nomObjet", objet.getName());
            data.put("planning", objet.getProfilSemaine().getName());
            data.put("typeObjet", objet.getType());
            data.put("inconnu", objet.isInconnu());
            data.put("connecte", objet.isConnecte());
            data.put("instanceNum",objet.getInstanceNum());
            data.put("devideId",objet.getDeviceId());
            if(objet.getType().equals(Constante.TYPE_CHAUFFAGE)){
                data.put("Tconfort",objet.getTemperature_confort());
                data.put("Teco", objet.getTemperature_economique());
            }
            else if(objet.getType().equals(Constante.TYPE_PRISE)){
                data.put("allume",objet.isAllume());
            }
           
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
     * @brief Permet de supprimer un profilSemaine
     * @func removeObjetModel(Objet_Model objet)
     * @param objet l'objet à supprimer
     * @return root
     */
    public String removeObjetModel(Objet_Model objet)
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("nomObjet",objet.getName());
            data.put("instanceNum",objet.getInstanceNum());
            data.put("deviceId",objet.getDeviceId());
            request.put("type","RM_OBJET");
            request.put("data",data);
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }



    /**
     * @brief permet de demander à la centrale d'éteindre l'objet spécifié
     * @func prisePowerOff(Objet_Model objet)
     * @prama objet l'objet que l'on veut éteindre
     * @return root
     */
    public String prisePowerOff(Objet_Model objet)
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            request.put("type", "POWEROFF_PRISE");
            data.put("nomObjet",objet.getName());
            data.put("instanceNum",objet.getInstanceNum());
            data.put("deviceId",objet.getDeviceId());
            request.put("data", data);
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }

    /**
     * @brief permet de demander à la centrale d'allumer l'objet spécifié
     * @func prisePowerOn(Objet_Model objet)
     * @param objet l'objet qu'on veut allumer
     * @return root
     */
    public String prisePowerOn(Objet_Model objet)
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            request.put("type", "POWERON_PRISE");
            data.put("nomObjet",objet.getName());
            data.put("instanceNum",objet.getInstanceNum());
            data.put("deviceId",objet.getDeviceId());
            request.put("data", data);
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }

    /**
     * @brief permet de demander à la centrale la consommation d'un objet spécifiée par l'argument name
     * @func getConsommation(Objet_Model objet)
     * @param objet
     * @return root
     */
    public String getConsommation(Objet_Model objet)
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            request.put("type", "GET_CONSOMMATION");
            data.put("nomObjet", objet.getName());
            data.put("instanceNum",objet.getInstanceNum());
            data.put("deviceId",objet.getDeviceId());
            request.put("data",data);
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }

    /**
     * @brief permet de changer la consommation d'un objet
     * @func stringToConsommation(String chaine, ArrayList<Objet_Model> ObjetList)
     * @param chaine la cchaine reçue par la centrale
     * @param objetList la liste d'objet du model
     */
    public void stringToConsommation(String chaine, ArrayList<Objet_Model> objetList)
    {
        try{
            JSONObject root = new JSONObject(chaine);
            JSONObject response = root.getJSONObject("response");
            JSONObject data = response.getJSONObject("data");
            JSONObject nomObjet =  data.getJSONObject("nomObjet");
            JSONObject consommation = data.getJSONObject("consommation");

            /** On regarde si on a le nom de l'objet dans notre liste **/
            for (int i=0; i<objetList.size(); i++) {
                if (nomObjet.getString("nomObjet").equals(objetList.get(i).getName())){
                    objetList.get(i).setConsommation(consommation.getInt("consommation"));
                }
                else System.out.println("Le nom de prise n'existe pas\n");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief indique à la centrale le passage en mode inclusion (pour enregistrer un nouvel objet)
     * @func inclusionMode()
     * @return root
     */
    public String inclusionMode()
    {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        try {
            request.put("type", "MODE_INCLUSION");
            root.put("request",request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }


    public int readResponse(String chaine)
    {
        String type = "";
        try {
            JSONObject root = new JSONObject(chaine);
            JSONObject response = root.getJSONObject("response");
            type  = response.getString("type");

            System.out.println("JsonUtil readResponse de type: "+type);
            /** Si la reponse est de type GET_PROFIL_JOUR**/
            if(type.equals("GET_PROFIL_JOUR")){
                return 1;
            }

            /** Si la reponse est de type GET_PROFIL_SEMAINE**/
            else if(type.equals(mapRequest.get(1))){
                return 2;
            }

            /** Si la reponse est de type GET_OBJETS**/
            else if(type.equals(mapRequest.get(2))){
                return 3;
            }

            /** Si la reponse est de type NEW_OBJET**/
            else if(type.equals(mapRequest.get(3))){
                return 4;
            }

            /** Si la reponse est de type GET_CONSOMMATION**/
            else if(type.equals(mapRequest.get(4))){
                return 5;
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }






}
