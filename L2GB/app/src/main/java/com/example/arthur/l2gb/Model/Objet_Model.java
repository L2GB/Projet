package com.example.arthur.l2gb.Model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pierrebaranger on 21/01/2015.
 */
public class Objet_Model implements Parcelable {

    private String name;
    private boolean connecte;
    private String type;
    private Integer temperature_confort;
    private Integer temperature_economique;
    private Semaine_Model profilSemaine;

    public Objet_Model(String name)
    {
        this.profilSemaine = new Semaine_Model();
        this.connecte = false;
        this.name = name;
        this.type = Constante.TYPE_CHAUFFAGE;
        this.temperature_confort = Constante.TEMP_CONFORT;
        this.temperature_economique = Constante.TEMP_ECO;
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
        type = in.readString();
        temperature_confort = in.readByte() == 0x00 ? null : in.readInt();
        temperature_economique = in.readByte() == 0x00 ? null : in.readInt();
        profilSemaine = (Semaine_Model) in.readValue(Semaine_Model.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (connecte ? 0x01 : 0x00));
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