package com.example.tageeddinemehdi.Controlleurs;

import com.example.tageeddinemehdi.Entites.Voiture;
import com.example.tageeddinemehdi.utilites.Outils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class voitureControlleurs implements Initializable {

    @FXML
    private TableColumn<Voiture,String> anneecol;

    @FXML
    private Button btnmaj;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnsearch;

    @FXML
    private Button btnsup;

    @FXML
    private TableColumn<Voiture, String> idcol;

    @FXML
    private TableColumn<Voiture, String> kmcol;

    @FXML
    private TableColumn<Voiture, String> marquecol;

    @FXML
    private TableColumn<Voiture, String> modelcol;

    @FXML
    private TableView<Voiture> table;

    @FXML
    private DatePicker txtannee;

    @FXML
    private TextField txtkilo;

    @FXML
    private TextField txtmarque;

    @FXML
    private TextField txtmodl;

    @FXML
    private TextField txtsearch;

    @FXML
    void Maj(ActionEvent event)
    {
        String marque,modele, km, annee;

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getIdv()));
        marque = txtmarque.getText();
        modele = txtmodl.getText();
        km = txtkilo.getText();
        annee =txtannee.getValue().toString();
        try
        {
            pst = con.prepareStatement("update voiture set marque = ?, modele = ?, km = ?, annee = ? where id = ? ");
            pst.setString(1, marque);
            pst.setString(2, modele);
            pst.setString(3, km);
            pst.setString(4, annee);
            pst.setInt(5, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MaJ");

            alert.setHeaderText("Mise a Jour");
            alert.setContentText("Votre Voiture : "+id+" a ete mis a jours.");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(voitureControlleurs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnSearchAll(ActionEvent event)
    {
        connect();
        try {
            String query = "SELECT id, marque, modele, km, annee FROM voiture";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            ObservableList<Voiture> voitures = FXCollections.observableArrayList();

            while (rs.next())
            {
                Voiture voiture = new Voiture(rs.getString("id"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getString("km"),
                        rs.getString("annee"));

                voiture.setIdv(rs.getString("id"));
                voiture.setMarque(rs.getString("marque"));
                voiture.setModel(rs.getString("modele"));
                voiture.setKm(rs.getString("km"));
                voiture.setAnnee(rs.getString("annee"));


                voitures.add(voiture);
            }

            table.setItems(voitures);
            idcol.setCellValueFactory(f-> f.getValue().idvProperty());
            marquecol.setCellValueFactory(f -> f.getValue().marqueProperty());
            modelcol.setCellValueFactory(f -> f.getValue().modelProperty());
            kmcol.setCellValueFactory(f -> f.getValue().kmProperty());
            anneecol.setCellValueFactory(f -> f.getValue().anneeProperty());

        } catch (SQLException e) {
            Logger.getLogger(voitureControlleurs.class.getName()).log(Level.SEVERE,null,e);
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
        String marqueVe, modeleVe, kmVe, anneeVe;
        marqueVe=txtmarque.getText();
        modeleVe = txtmodl.getText();
        kmVe = txtkilo.getText();
        anneeVe = String.valueOf(txtannee.getValue());

        try{
            pst=con.prepareStatement("insert into voiture (marque, modele, km, annee) values(?,?,?,?)");
            pst.setString(1,marqueVe);
            pst.setString(2,modeleVe);
            pst.setString(3,kmVe);
            pst.setString(4,anneeVe);

            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enregistrement");
            alert.setHeaderText("Enregistrement Voiture");
            alert.setContentText("Voiture ("+id+") a ete Enregistrer");
            alert.showAndWait();

            table();
            txtmarque.setText("");
            txtmodl.setText("");
            txtkilo.setText("");
            txtannee.setDayCellFactory(null);
            txtmarque.requestFocus();

        } catch (SQLException e) {
            Logger.getLogger(voitureControlleurs.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    @FXML
    void search(ActionEvent event) {
        connect();
        String voitureId = txtsearch.getText();
        try {
            String query = "SELECT * FROM voiture WHERE id = ?" ;
            pst = con.prepareStatement(query);
            pst.setString(1, voitureId);
            rs = pst.executeQuery();
            ObservableList<Voiture> voitures = FXCollections.observableArrayList();

            while (rs.next())
            {
                Voiture voiture = new Voiture(rs.getString("id"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getString("km"),
                        rs.getString("annee"));

                voiture.setIdv(rs.getString("id"));
                voiture.setMarque(rs.getString("marque"));
                voiture.setModel(rs.getString("modele"));
                voiture.setKm(rs.getString("km"));
                voiture.setAnnee(rs.getString("annee"));


                voitures.add(voiture);
            }

            table.setItems(voitures);
            idcol.setCellValueFactory(f-> f.getValue().idvProperty());
            marquecol.setCellValueFactory(f -> f.getValue().marqueProperty());
            modelcol.setCellValueFactory(f -> f.getValue().modelProperty());
            kmcol.setCellValueFactory(f -> f.getValue().kmProperty());
            anneecol.setCellValueFactory(f -> f.getValue().anneeProperty());


        } catch (SQLException e) {
            Logger.getLogger(voitureControlleurs.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    @FXML
    void supp(ActionEvent event) {
        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getIdv()));


        try
        {
            pst = con.prepareStatement("delete from voiture where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression");

            alert.setHeaderText("Supression");
            alert.setContentText("Voiture id No : " +id+" a ete Supprimer");

            alert.showAndWait();
            table();

        }
        catch (SQLException ex)
        {
            Logger.getLogger(voitureControlleurs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void erase(ActionEvent event)
    {
        txtmarque.clear();
        txtkilo.clear();
        txtmodl.clear();
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
        ObservableList<Voiture> voiture = FXCollections.observableArrayList();
        try
        {
            pst = con.prepareStatement("select id,marque, modele, km, annee  from voiture");
            rs = pst.executeQuery();

            while (rs.next())
            {
                Voiture voitures = new Voiture(rs.getString("id"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getString("km"),
                        rs.getString("annee"));

                voitures.setIdv(rs.getString("id"));
                voitures.setMarque(rs.getString("marque"));
                voitures.setModel(rs.getString("modele"));
                voitures.setKm(rs.getString("km"));
                voitures.setAnnee(rs.getString("annee"));


                voiture.add(voitures);
            }

            table.setItems(voiture);
            idcol.setCellValueFactory(f-> f.getValue().idvProperty());
            marquecol.setCellValueFactory(f -> f.getValue().marqueProperty());
            modelcol.setCellValueFactory(f -> f.getValue().modelProperty());
            kmcol.setCellValueFactory(f -> f.getValue().kmProperty());
            anneecol.setCellValueFactory(f -> f.getValue().anneeProperty());


        }

        catch (SQLException ex)
        {
            Logger.getLogger(voitureControlleurs.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory( tv -> {
            TableRow<Voiture> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  table.getSelectionModel().getSelectedIndex();
                    id=Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getIdv()));

                    txtmarque.setText(String.valueOf(table.getItems().get(myIndex).getMarque()));
                    txtmodl.setText(String.valueOf(table.getItems().get(myIndex).getModel()));
                    txtkilo.setText(String.valueOf(table.getItems().get(myIndex).getKm()));
                    txtannee.setValue(LocalDate.parse(table.getItems().get(myIndex).getAnnee()));




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
