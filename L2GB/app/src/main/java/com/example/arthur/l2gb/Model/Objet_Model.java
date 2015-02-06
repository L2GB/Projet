package com.example.arthur.l2gb.Model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @file Objet_Model
 * @brief Class Objet pour définir tout type d'objet connecté
 * @date Created on 21/01/2015.
 * @author pierreBaranger1
 */
public class Objet_Model implements Parcelable {

    /**
     * name
     * le nom de l'objet
     */
    private String name;

    /**
     * connecte
     * indique si l'objet est connecté ou non
     */
    private boolean connecte;

    /**
     * inconnu
     * indique si l'objet est inconnue ou non
     */
    private boolean inconnu;

    /**
     * type
     * indique le type d'objet
     */
    private String type;

    /**
     * temperature_confort
     * indique la temperature de confort souhaitée pour cet objet
     */
    private Integer temperature_confort;

    /**
     * temperature_economique
     * indique la temperature economique souhaitée pour cet objet
     */
    private Integer temperature_economique;

    /**
     * profilSemaine
     * Le planning de la semaine d'un objet
     */
    private Semaine_Model profilSemaine;

    /**
     * instanceNum
     * le numéro d'instance de l'objet
     */
    private Integer instanceNum;

    /**
     * deviceId
     * l'identifiant de l'objet
     */
    private Integer deviceId;

    /**
     * consommation
     * la consommation electrique de l'objet
     */
    private Integer consommation;

    /**
     * allume
     * indique si l'objet est allume ou eteint
     */
    private boolean allume;

    /**
     * @brief Constructeur de la class
     * @param name
     */
    public Objet_Model(String name)
    {
        this.profilSemaine = new Semaine_Model();
        this.connecte = false;
        this.inconnu = true;
        this.instanceNum = 0;
        this.deviceId = 0;
        this.name = name;
        this.type = Constante.TYPE_CHAUFFAGE;
        this.temperature_confort = Constante.TEMP_CONFORT;
        this.temperature_economique = Constante.TEMP_ECO;
        this.consommation=0;
        this.allume=false;
    }

    /**
     * @brief Constructeur de la class
     * @param name
     * @param profilSemaine
     */
    public Objet_Model(String name, Semaine_Model profilSemaine)
    {
        this.profilSemaine = profilSemaine;
        this.connecte = false;
        this.name = name;
        this.type = Constante.TYPE_CHAUFFAGE;
        this.temperature_confort = Constante.TEMP_CONFORT;
        this.temperature_economique = Constante.TEMP_ECO;
        this.allume=false;
    }


    /**
     * @brief Permet de copier un objet dans un autre objet
     * @param objetModel
     */
    public void setObjet(Objet_Model objetModel){
        this.profilSemaine = objetModel.profilSemaine;
        this.connecte =   objetModel.connecte;
        this.inconnu = objetModel.inconnu;
        this.instanceNum = objetModel.instanceNum;
        this.deviceId = objetModel.deviceId;
        this.name = objetModel.name;
        this.type = objetModel.type;
        this.temperature_confort = objetModel.temperature_confort;
        this.temperature_economique = objetModel.temperature_economique;
        this.consommation=objetModel.consommation;
        this.allume=objetModel.allume;
    }


    /*****************************************************************
     * Getter et setter
     *****************************************************************/

    public boolean isAllume() {
        return allume;
    }

    public void setAllume(boolean allume) {
        this.allume = allume;
    }

    public Integer getInstanceNum() {
        return instanceNum;
    }

    public void setInstanceNum(Integer instanceNum) {
        this.instanceNum = instanceNum;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isInconnu() {
        return inconnu;
    }

    public void setInconnu(boolean inconnu) {
        this.inconnu = inconnu;
    }

    public Integer getConsommation() {
        return consommation;
    }

    public void setConsommation(Integer consommation) {
        this.consommation = consommation;
    }

    public boolean isConnecte() {
        return connecte;
    }

    public Integer getTemperature_economique() {
        return temperature_economique;
    }

    public void setTemperature_economique(Integer temperature_economique) {
        this.temperature_economique = temperature_economique;
    }

    public Integer getTemperature_confort() {
        return temperature_confort;
    }

    public void setTemperature_confort(Integer temperature_confort) {
        this.temperature_confort = temperature_confort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Semaine_Model getProfilSemaine() {
        return profilSemaine;
    }

    public void setProfilSemaine(Semaine_Model profilSemaine) {
        this.profilSemaine = profilSemaine;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getConnecte() {
        return this.connecte;
    }

    public void setConnecte(boolean connecte) {
        this.connecte = connecte;
    }




    /*****************************************************************
     * Methodes utilisées pour la serialisation des objets du package model
     *****************************************************************/
    protected Objet_Model(Parcel in) {
        name = in.readString();
        connecte = in.readByte() != 0x00;
        inconnu = in.readByte() != 0x00;
        type = in.readString();
        temperature_confort = in.readByte() == 0x00 ? null : in.readInt();
        temperature_economique = in.readByte() == 0x00 ? null : in.readInt();
        profilSemaine = (Semaine_Model) in.readValue(Semaine_Model.class.getClassLoader());
        instanceNum = in.readByte() == 0x00 ? null : in.readInt();
        deviceId = in.readByte() == 0x00 ? null : in.readInt();
        consommation = in.readByte() == 0x00 ? null : in.readInt();
        allume = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (connecte ? 0x01 : 0x00));
        dest.writeByte((byte) (inconnu ? 0x01 : 0x00));
        dest.writeString(type);
        if (temperature_confort == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(temperature_confort);
        }
        if (temperature_economique == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(temperature_economique);
        }
        dest.writeValue(profilSemaine);
        if (instanceNum == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(instanceNum);
        }
        if (deviceId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(deviceId);
        }
        if (consommation == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(consommation);
        }
        dest.writeByte((byte) (allume ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Objet_Model> CREATOR = new Parcelable.Creator<Objet_Model>() {
        @Override
        public Objet_Model createFromParcel(Parcel in) {
            return new Objet_Model(in);
        }

        @Override
        public Objet_Model[] newArray(int size) {
            return new Objet_Model[size];
        }
    };
}