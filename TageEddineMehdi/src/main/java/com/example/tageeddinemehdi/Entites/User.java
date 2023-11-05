package com.example.tageeddinemehdi.Entites;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User
{

    private final  StringProperty id;
    private  final  StringProperty prenom;
    private  final  StringProperty nom;
    private  final  StringProperty login;
    private final  StringProperty password;
    private  final  StringProperty sexe;
    private  final  StringProperty role;

    public User(String id, String prenom, String nom, String login, String password, String sexe, String role) {
        this.id = new SimpleStringProperty(id);
        this.prenom = new SimpleStringProperty(prenom);
        this.nom = new SimpleStringProperty(nom);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.sexe = new SimpleStringProperty(sexe);
        this.role = new SimpleStringProperty(role);

    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getSexe() {
        return sexe.get();
    }

    public StringProperty sexeProperty() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe.set(sexe);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }
}
