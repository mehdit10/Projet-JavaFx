package com.example.tageeddinemehdi.Controlleurs;

import com.example.tageeddinemehdi.Entites.Client;
import com.example.tageeddinemehdi.Entites.Ventes;
import com.example.tageeddinemehdi.utilites.Outils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VenteControlleur implements Initializable {

    @FXML
    private Button btnmaj;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnsearch;

    @FXML
    private Button btnsup;

    @FXML
    private TableColumn<Ventes,String> datecol;

    @FXML
    private TableColumn<Ventes,String> idcol;

    @FXML
    private TableColumn<Ventes,String> montcol;

    @FXML
    private TableView<Ventes> table;

    @FXML
    private DatePicker txtdate;

    @FXML
    private TextField txtmont;

    @FXML
    private TextField txtsearch;

    @FXML
    void Maj(ActionEvent event) {

        String montant, date;

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getIdve()));
        montant = txtmont.getText();
        date = String.valueOf(txtdate.getValue());

        try
        {
            pst = con.prepareStatement("update vente set montant = ?, date = ? where id = ? ");
            pst.setString(1,montant);
            pst.setString(2, date);
            pst.setInt(3, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MaJ");

            alert.setHeaderText("Mise a Jour");
            alert.setContentText("Votre vente : "+id+" a ete mis a jours.");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(VenteControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnSearchAll(ActionEvent event) {

        connect();
        try {
            String query = "SELECT id, montant, date FROM vente";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            ObservableList<Ventes> ventes = FXCollections.observableArrayList();

            while (rs.next())
            {
                Ventes vente = new Ventes(rs.getString("id"),
                        rs.getString("montant"),
                        rs.getString("date"));

                vente.setIdve(rs.getString("id"));
                vente.setMontant(rs.getString("montant"));
                vente.setDate(rs.getString("date"));


                ventes.add(vente);
            }

            table.setItems(ventes);
            idcol.setCellValueFactory(f->f.getValue().idveProperty());
            montcol.setCellValueFactory(f -> f.getValue().montantProperty());
            datecol.setCellValueFactory(f -> f.getValue().dateProperty());
            table.setItems(ventes);

        } catch (SQLException e) {
            Logger.getLogger(VenteControlleur.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    @FXML
    void btnWindowBack(ActionEvent event) throws IOException {
        // Recuperation de l'utilisateur Connecter
        String role = ConnexionControlleur.getRole();

        if(role.equals("Administrateur")) {
            Outils.load(event,"Gestionnaire des Voitures","/com/example/tageeddinemehdi/accueil.fxml");
        } else {
            Outils.load(event,"Gestionnaire des Voitures","/com/example/tageeddinemehdi/accueil_user.fxml");
        }
    }

    @FXML
    void save(ActionEvent event) {

        String montantVe, dateVe;
        montantVe=txtmont.getText();
        dateVe = String.valueOf(txtdate.getValue());

        try{
            pst=con.prepareStatement("insert into vente (montant, date) values(?,?)");
            pst.setString(1,montantVe);
            pst.setString(2,dateVe);

            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enregistrement");
            alert.setHeaderText("Enregistrement Vente");
            alert.setContentText("Vente "+id+" a ete Enregistrer");
            alert.showAndWait();

            table();
            txtmont.setText("");
            txtdate.setDayCellFactory(null);
            txtmont.requestFocus();

        } catch (SQLException e) {
            Logger.getLogger(VenteControlleur.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    @FXML
    void search(ActionEvent event) {

        connect();
        String venteId = txtsearch.getText();
        try {
            String query = "SELECT * FROM vente WHERE id = ?" ;
            pst = con.prepareStatement(query);
            pst.setString(1, venteId);
            rs = pst.executeQuery();
            ObservableList<Ventes> ventes = FXCollections.observableArrayList();

            while (rs.next())
            {
                Ventes vente = new Ventes(rs.getString("id"),
                        rs.getString("montant"),
                        rs.getString("date"));

                vente.setIdve(rs.getString("id"));
                vente.setMontant(rs.getString("montant"));
                vente.setDate(rs.getString("date"));


                ventes.add(vente);
            }

            table.setItems(ventes);
            idcol.setCellValueFactory(f->f.getValue().idveProperty());
            montcol.setCellValueFactory(f -> f.getValue().montantProperty());
            datecol.setCellValueFactory(f -> f.getValue().dateProperty());
            table.setItems(ventes);


        } catch (SQLException e) {
            Logger.getLogger(VenteControlleur.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    @FXML
    void supp(ActionEvent event) {

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getIdve()));


        try
        {
            pst = con.prepareStatement("delete from vente where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression");

            alert.setHeaderText("Supression");
            alert.setContentText("Vente " +id+" a ete Supprimer");

            alert.showAndWait();
            table();

        }
        catch (SQLException ex)
        {
            Logger.getLogger(VenteControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // CONNECTION A LA BASE DE DONNEE
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    int myIndex;
    int id;
    public void connect(){
        String databaseName="gestion_fx";
        String databaseUser="root";
        String databasePassword="";
        String url="jdbc:mysql://localhost/" +databaseName;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void table()
    {
        connect();
        ObservableList<Ventes> ventes = FXCollections.observableArrayList();
        try
        {
            pst = con.prepareStatement("select id, montant, date  from vente");
            rs = pst.executeQuery();

            while (rs.next())
            {
                Ventes vente = new Ventes(rs.getString("id"),
                        rs.getString("montant"),
                        rs.getString("date"));

                vente.setIdve(rs.getString("id"));
                vente.setMontant(rs.getString("montant"));
                vente.setDate(rs.getString("date"));


                ventes.add(vente);
            }

            table.setItems(ventes);
            idcol.setCellValueFactory(f->f.getValue().idveProperty());
            montcol.setCellValueFactory(f -> f.getValue().montantProperty());
            datecol.setCellValueFactory(f -> f.getValue().dateProperty());


        }

        catch (SQLException ex)
        {
            Logger.getLogger(VenteControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory( tv -> {
            TableRow<Ventes> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  table.getSelectionModel().getSelectedIndex();
                    id=Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getIdve()));

                    txtmont.setText(String.valueOf(table.getItems().get(myIndex).getMontant()));
                    txtdate.setValue(LocalDate.parse(table.getItems().get(myIndex).getDate()));



                }
            });
            return myRow;
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect();
        table();
    }

}
