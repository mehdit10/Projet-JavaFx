package com.example.tageeddinemehdi.Controlleurs;

import com.example.tageeddinemehdi.Entites.Client;
import com.example.tageeddinemehdi.Entites.User;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientControlleurs implements Initializable {
    @FXML
    private TableColumn<Client, String> cnicol;

    @FXML
    private TableColumn<Client, String> idcol;

    @FXML
    private TableColumn<Client, String> sexecol;

    @FXML
    private TableColumn<Client, String> nomcol;
    @FXML
    private TableColumn<Client, String> adressecol;

    @FXML
    private TableColumn<Client, String> prenomcol;
    @FXML
    private TableView<Client> table;

    @FXML
    private TextField txtadresse;

    @FXML
    private TextField txtcni;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprenom;

    @FXML
    private TextField txtsearch;
    @FXML
    private ComboBox<String> s_cmbobox;

    // Bouton pour METTRE A JOUR un client
    @FXML
    void Maj(ActionEvent event)
    {
        String cni,prenom,nom,adresse,sexe;

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getIdcli()));
        cni=txtcni.getText();
        prenom = txtprenom.getText();
        nom = txtnom.getText();
        adresse = txtadresse.getText();
        sexe=s_cmbobox.getValue();
        try
        {
            pst = con.prepareStatement("update client set cni=?,prenom = ?,nom = ? ,adresse = ? ,sexe = ? where id = ? ");
            pst.setString(1,cni);
            pst.setString(2, prenom);
            pst.setString(3, nom);
            pst.setString(4, adresse);
            pst.setString(5, sexe);
            pst.setInt(6, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MaJ");

            alert.setHeaderText("Mise a Jour");
            alert.setContentText("Votre client : "+id+" a ete mis a jours.");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClientControlleurs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Bouton pour ENREGISTRER un client
    @FXML
    void save(ActionEvent event) {
        String cniClient,nomClient,prenomClient,adresseClient,sexeClient;
        cniClient=txtcni.getText();
        prenomClient=txtprenom.getText();
        nomClient=txtnom.getText();
        adresseClient=txtadresse.getText();
        sexeClient=s_cmbobox.getValue();

        try{
            pst=con.prepareStatement("insert into client (cni,prenom,nom,adresse,sexe) values(?,?,?,?,?)");
            pst.setString(1,cniClient);
            pst.setString(2,prenomClient);
            pst.setString(3,nomClient);
            pst.setString(4,adresseClient);
            pst.setString(5,sexeClient);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enregistrement");
            alert.setHeaderText("Enregistrement Client");
            alert.setContentText("Client "+id+" a ete Enregistrer");
            alert.showAndWait();

            table();
            txtcni.setText("");
            txtprenom.setText("");
            txtnom.setText("");
            txtadresse.setText("");
            s_cmbobox.setValue("");
            txtcni.requestFocus();

        } catch (SQLException e) {
            Logger.getLogger(ClientControlleurs.class.getName()).log(Level.SEVERE,null,e);
        }

    }

    @FXML
    void search(ActionEvent event)
    {
        connect();
        String clientId = txtsearch.getText();
        try {
            String query = "SELECT * FROM client WHERE id = ?" ;
            pst = con.prepareStatement(query);
            pst.setString(1, clientId);
            rs = pst.executeQuery();
            ObservableList<Client> clients = FXCollections.observableArrayList();

                while (rs.next())
                {
                    Client client = new Client(rs.getString("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("sexe"),
                            rs.getString("adresse"),
                            rs.getString("cni"));

                    client.setIdcli(rs.getString("id"));
                    client.setCni(rs.getString("cni"));
                    client.setPrenomcli(rs.getString("prenom"));
                    client.setNomcli(rs.getString("nom"));
                    client.setAdressecli(rs.getString("adresse"));
                    client.setSexecli(rs.getString("sexe"));

                    clients.add(client);
                }

            table.setItems(clients);
            idcol.setCellValueFactory(f->f.getValue().idcliProperty());
            cnicol.setCellValueFactory(f -> f.getValue().cniProperty());
            prenomcol.setCellValueFactory(f -> f.getValue().prenomcliProperty());
            nomcol.setCellValueFactory(f -> f.getValue().nomcliProperty());
            adressecol.setCellValueFactory(f -> f.getValue().adressecliProperty());
            sexecol.setCellValueFactory(f -> f.getValue().sexecliProperty());
            table.setItems(clients);


        } catch (SQLException e) {
            Logger.getLogger(ClientControlleurs.class.getName()).log(Level.SEVERE,null,e);
        }
    }
        // Bouton pour SUPPRIMER un client
    @FXML
    void supp(ActionEvent event)
    {
        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getIdcli()));


        try
        {
            pst = con.prepareStatement("delete from client where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression");

            alert.setHeaderText("Supression");
            alert.setContentText("Client " +id+" a ete Supprimer");

            alert.showAndWait();
            table();

        }
        catch (SQLException ex)
        {
            Logger.getLogger(ClientControlleurs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void btnSearchAll(ActionEvent event)
    {
        connect();
        try {
            String query = "SELECT id, cni, nom, prenom, adresse, sexe FROM client";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            ObservableList<Client> clients = FXCollections.observableArrayList();

                while (rs.next())
                {
                    Client client = new Client(rs.getString("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("sexe"),
                            rs.getString("adresse"),
                            rs.getString("cni"));

                    client.setIdcli(rs.getString("id"));
                    client.setCni(rs.getString("cni"));
                    client.setPrenomcli(rs.getString("prenom"));
                    client.setNomcli(rs.getString("nom"));
                    client.setAdressecli(rs.getString("adresse"));
                    client.setSexecli(rs.getString("sexe"));

                    clients.add(client);
                }

            table.setItems(clients);
            idcol.setCellValueFactory(f->f.getValue().idcliProperty());
            cnicol.setCellValueFactory(f -> f.getValue().cniProperty());
            prenomcol.setCellValueFactory(f -> f.getValue().prenomcliProperty());
            nomcol.setCellValueFactory(f -> f.getValue().nomcliProperty());
            adressecol.setCellValueFactory(f -> f.getValue().adressecliProperty());
            sexecol.setCellValueFactory(f -> f.getValue().sexecliProperty());
            table.setItems(clients);

        } catch (SQLException e) {
            Logger.getLogger(ClientControlleurs.class.getName()).log(Level.SEVERE,null,e);
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
    void erase(ActionEvent event)
    {
        txtprenom.clear();
        txtnom.clear();
        txtcni.clear();
        txtadresse.clear();
        String sexeBox = "Genre";
        s_cmbobox.setValue(sexeBox);
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
        ObservableList<Client> clients = FXCollections.observableArrayList();
        try
        {
            pst = con.prepareStatement("select id,cni,prenom,nom,adresse,sexe  from client");
             rs = pst.executeQuery();

                while (rs.next())
                {
                    Client client = new Client(rs.getString("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("sexe"),
                            rs.getString("adresse"),
                            rs.getString("cni"));

                    client.setIdcli(rs.getString("id"));
                    client.setCni(rs.getString("cni"));
                    client.setPrenomcli(rs.getString("prenom"));
                    client.setNomcli(rs.getString("nom"));
                    client.setAdressecli(rs.getString("adresse"));
                    client.setSexecli(rs.getString("sexe"));

                    clients.add(client);
                }

            table.setItems(clients);
            idcol.setCellValueFactory(f->f.getValue().idcliProperty());
            cnicol.setCellValueFactory(f -> f.getValue().cniProperty());
            prenomcol.setCellValueFactory(f -> f.getValue().prenomcliProperty());
            nomcol.setCellValueFactory(f -> f.getValue().nomcliProperty());
            adressecol.setCellValueFactory(f -> f.getValue().adressecliProperty());
            sexecol.setCellValueFactory(f -> f.getValue().sexecliProperty());


        }

        catch (SQLException ex)
        {
            Logger.getLogger(ClientControlleurs.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory( tv -> {
            TableRow<Client> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  table.getSelectionModel().getSelectedIndex();
                    id=Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getIdcli()));

                    txtcni.setText(String.valueOf(table.getItems().get(myIndex).getCni()));
                    txtprenom.setText(table.getItems().get(myIndex).getPrenomcli());
                    txtnom.setText(table.getItems().get(myIndex).getNomcli());
                    txtadresse.setText(table.getItems().get(myIndex).getAdressecli());
                    s_cmbobox.setValue(table.getItems().get(myIndex).getSexecli());

                }
            });
            return myRow;
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect();
        table();
        ObservableList<String> sexes = FXCollections.observableArrayList("Homme", "Femme");
        s_cmbobox.setItems(sexes);
    }

}
