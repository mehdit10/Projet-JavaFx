package com.example.tageeddinemehdi.Entites;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ventes
{
    private final StringProperty  idve;
    private final StringProperty dateve;
    private final StringProperty montantve;

    public Ventes(String id, String date, String montant) {
        idve= new SimpleStringProperty(this, "id", id);
        dateve = new SimpleStringProperty(this, "date", date);
        montantve = new SimpleStringProperty(this, "montant", montant);
    }

    public String getIdve() {
        return idve.get();
    }

    public StringProperty idveProperty() {
        return idve;
    }

    public void setIdve(String idve) {
        this.idve.set(idve);
    }

    public String getDate() {
        return dateve.get();
    }

    public StringProperty dateProperty() {
        return dateve;
    }

    public void setDate(String date) {
        this.dateve.set(date);
    }

    public String getMontant() {
        return montantve.get();
    }

    public StringProperty montantProperty() {
        return montantve;
    }

    public void setMontant(String montant) {
        this.montantve.set(montant);
    }
}
