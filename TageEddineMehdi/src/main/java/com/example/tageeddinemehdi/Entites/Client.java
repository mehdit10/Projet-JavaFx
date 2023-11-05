package com.example.tageeddinemehdi.Entites;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client
{
    private final StringProperty idcli;
    private final StringProperty prenomcli;
    private final StringProperty nomcli;
    private final StringProperty  adressecli;
    private final StringProperty  sexecli;
    private final StringProperty cnicli;

    public Client(String id, String nom, String prenom, String sexe, String adresse, String cni)
    {
        idcli= new SimpleStringProperty(this, "id", id);
        cnicli = new SimpleStringProperty(this, "cni", cni);
        prenomcli = new SimpleStringProperty(this, "prenom", prenom);
        nomcli = new SimpleStringProperty(this, "nom", nom);
        adressecli = new SimpleStringProperty(this, "adresse", adresse);
        sexecli = new SimpleStringProperty(this, "sexe", sexe);
    }

    public String getIdcli() {
        return idcli.get();
    }

    public StringProperty idcliProperty() {
        return idcli;
    }

    public void setIdcli(String idcli) {
        this.idcli.set(idcli);
    }

    public String getPrenomcli() {
        return prenomcli.get();
    }

    public StringProperty prenomcliProperty() {
        return prenomcli;
    }

    public void setPrenomcli(String prenomcli) {
        this.prenomcli.set(prenomcli);
    }

    public String getNomcli() {
        return nomcli.get();
    }

    public StringProperty nomcliProperty() {
        return nomcli;
    }

    public void setNomcli(String nomcli) {
        this.nomcli.set(nomcli);
    }

    public String getAdressecli() {
        return adressecli.get();
    }

    public StringProperty adressecliProperty() {
        return adressecli;
    }

    public void setAdressecli(String adressecli) {
        this.adressecli.set(adressecli);
    }

    public String getSexecli() {
        return sexecli.get();
    }

    public StringProperty sexecliProperty() {
        return sexecli;
    }

    public void setSexecli(String sexecli) {
        this.sexecli.set(sexecli);
    }

    public String getCni() {
        return cnicli.get();
    }

    public StringProperty cniProperty() {
        return cnicli;
    }

    public void setCni(String cni) {
        this.cnicli.set(cni);
    }
}
