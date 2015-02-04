package com.example.arthur.l2gb.Model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pierrebaranger on 21/01/2015.
 */
public class Objet_Model implements Parcelable {

    private String name;
    private boolean connecte;
    private boolean inconnu;
    private String type;
    private Integer temperature_confort;
    private Integer temperature_economique;
    private Semaine_Model profilSemaine;
    private Integer instanceNum;
    private Integer deviceId;
    private Integer consommation;


    public Objet_Model(String name)
    {
        this.profilSemaine = new Semaine_Model();
        this.connecte = false;
        this.inconnu = false;
        this.instanceNum = 0;
        this.deviceId = 0;
        this.name = name;
        this.type = Constante.TYPE_CHAUFFAGE;
        this.temperature_confort = Constante.TEMP_CONFORT;
        this.temperature_economique = Constante.TEMP_ECO;
        this.consommation=0;
    }


    public Integer getInstanceNum() {
        return instanceNum;
    }

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

    public static Creator<Objet_Model> getCreator() {
        return CREATOR;
    }

    public boolean isInconnu() {
        return inconnu;
    }

    public void setInconnu(boolean inconnu) {
        this.inconnu = inconnu;
    }


    public Objet_Model(String name, Semaine_Model profilSemaine)
    {
        this.profilSemaine = profilSemaine;
        this.connecte = false;
        this.name = name;
        this.type = Constante.TYPE_CHAUFFAGE;
        this.temperature_confort = Constante.TEMP_CONFORT;
        this.temperature_economique = Constante.TEMP_ECO;
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