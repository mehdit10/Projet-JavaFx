package com.example.tageeddinemehdi.Entites;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Voiture
{

    private final StringProperty idv;
    private final StringProperty marque;
    private final StringProperty model;
    private final StringProperty km;
    private final StringProperty annee;

    public Voiture(String idv, String marque, String model, String km, String annee) {
        this.idv = new SimpleStringProperty(idv);
        this.marque = new SimpleStringProperty(marque);
        this.model = new SimpleStringProperty(model);
        this.km = new SimpleStringProperty(km);
        this.annee = new SimpleStringProperty(annee);
    }

    public String getIdv() {
        return idv.get();
    }

    public StringProperty idvProperty() {
        return idv;
    }

    public void setIdv(String idv) {
        this.idv.set(idv);
    }

    public String getMarque() {
        return marque.get();
    }

    public StringProperty marqueProperty() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque.set(marque);
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getKm() {
        return km.get();
    }

    public StringProperty kmProperty() {
        return km;
    }

    public void setKm(String km) {
        this.km.set(km);
    }

    public String getAnnee() {
        return annee.get();
    }

    public StringProperty anneeProperty() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee.set(annee);
    }
}
