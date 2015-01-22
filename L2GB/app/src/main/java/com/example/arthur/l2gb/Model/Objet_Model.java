package com.example.arthur.l2gb.Model;

<<<<<<< .mine
import android.os.Parcel;
import android.os.Parcelable;

=======
import com.l2gb.applipfe.model.ProfilSemaine;

import java.lang.Integer;
import java.lang.String;

>>>>>>> .r56
/**
 * Created by arthur on 15/01/2015.
 */
public class Objet_Model implements Parcelable {

    private String name;
    private boolean connecte;
    private String type;
    private Integer temperature_confort;
    private Integer temperature_economique;
    private ProfilSemaine profilSemaine;


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

    public ProfilSemaine getProfilSemaine() {
        return profilSemaine;
    }

    public void setProfilSemaine(ProfilSemaine profilSemaine) {
        this.profilSemaine = profilSemaine;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
<<<<<<< .mine


    protected Objet_Model(Parcel in) {
        name = in.readString();
        id = in.readInt();
        piece = in.readString();
        estConnecte = in.readByte() != 0x00;
        semaine_model = (Semaine_Model) in.readValue(Semaine_Model.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(piece);
        dest.writeByte((byte) (estConnecte ? 0x01 : 0x00));
        dest.writeValue(semaine_model);
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
}=======

    public boolean getConnecte() {
        return this.connecte;
    }

    public void setConnecte(boolean connecte) {
        this.connecte = connecte;
    }


}
>>>>>>> .r56
