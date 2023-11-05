package com.example.tageeddinemehdi.Controlleurs;

import com.example.tageeddinemehdi.Entites.User;
import com.example.tageeddinemehdi.utilites.Outils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class userControlleur implements Initializable {

    @FXML
    private Button btnmaj;
    @FXML
    private Button btnErase;


    @FXML
    private Button btnsave;

    @FXML
    private Button btnsearch;

    @FXML
    private Button btnsup;

    @FXML
    private TableColumn<User,String> idcol;

    @FXML
    private TableColumn<User,String> logcol;

    @FXML
    private TableColumn<User,String> nomcol;

    @FXML
    private TableColumn<User,String> passcol;

    @FXML
    private TableColumn<User,String> prenomcol;

    @FXML
    private TableColumn<User,String> rolecol;

    @FXML
    private TableColumn<User,String> sexecol;

    @FXML
    private TableView<User> table;

    @FXML
    private TextField txtlog;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtpass;

    @FXML
    private TextField txtprenom;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private TextField txtsearch;

    @FXML
    private ComboBox<String> sexeBox;

    public  static String s_defaultValue = "Genre";

    public  static String r_defaultValue = "Fonction";

    @FXML
    void Maj(ActionEvent event) {
        String prenom,nom,login,password,sexe,role;

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
        prenom = txtprenom.getText();
        nom = txtnom.getText();
        login=txtlog.getText();
        password = txtpass.getText();
        sexe=sexeBox.getValue();
        role= roleBox.getValue();
        try
        {
            pst = con.prepareStatement("update user set prenom = ?,nom = ? ,login = ? , password = ? ,sexe = ?, role = ? where id = ? ");
            pst.setString(1, prenom);
            pst.setString(2, nom);
            pst.setString(3,login);
            pst.setString(4, password);
            pst.setString(5, sexe);
            pst.setString(6, role);
            pst.setInt(7, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MaJ");

            alert.setHeaderText("Mise a Jour");
            alert.setContentText("Votre Utilisateur : "+id+" a ete mis a jours.");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(userControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnSearchAll(ActionEvent event) {
        connect();
        try {
            String query = "SELECT id, prenom, nom, login, password, sexe, role FROM user";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            ObservableList<User> users = FXCollections.observableArrayList();

            while (rs.next())
            {
                User user = new User(rs.getString("id"),
                        rs.getString("prenom"),
                        rs.getString("nom"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("sexe"),
                        rs.getString("role"));

                user.setId(rs.getString("id"));
                user.setPrenom(rs.getString("prenom"));
                user.setNom(rs.getString("nom"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setSexe(rs.getString("sexe"));
                user.setRole(rs.getString("role"));


                users.add(user);
            }

            table.setItems(users);
            idcol.setCellValueFactory(f->f.getValue().idProperty());
            prenomcol.setCellValueFactory(f -> f.getValue().prenomProperty());
            nomcol.setCellValueFactory(f -> f.getValue().nomProperty());
            logcol.setCellValueFactory(f -> f.getValue().loginProperty());
            passcol.setCellValueFactory(f -> f.getValue().passwordProperty());
            sexecol.setCellValueFactory(f -> f.getValue().sexeProperty());
            rolecol.setCellValueFactory(f -> f.getValue().roleProperty());

            table.setItems(users);

        } catch (SQLException e) {
            Logger.getLogger(userControlleur.class.getName()).log(Level.SEVERE,null,e);
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

        String prenom,nom,login,password, sexe, role;
        prenom=txtprenom.getText();
        nom=txtnom.getText();
        login=txtlog.getText();
        password=txtpass.getText();
        sexe=sexeBox.getValue();
        role= roleBox.getValue();
        try{
            pst=con.prepareStatement("insert into user (prenom,nom,login,password,sexe, role) values(?,?,?,?,?,?)");
            pst.setString(1,prenom);
            pst.setString(2,nom);
            pst.setString(3,login);
            pst.setString(4,password);
            pst.setString(5,sexe);
            pst.setString(6,role);

            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enregistrement");
            alert.setHeaderText("Enregistrement Utilisateur");
            alert.setContentText("Utilisateur "+id+" a ete Enregistrer");
            alert.showAndWait();

            table();
            txtprenom.setText("");
            txtnom.setText("");
            txtlog.setText("");
            txtpass.setText("");
            sexeBox.setValue("");
            roleBox.setValue("");
            txtlog.requestFocus();

        } catch (SQLException e) {
            Logger.getLogger(userControlleur.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    @FXML
    void search(ActionEvent event) {

        connect();
        String UserId = txtsearch.getText();
        try {
            String query = "SELECT * FROM user WHERE id = ?" ;
            pst = con.prepareStatement(query);
            pst.setString(1, UserId);
            rs = pst.executeQuery();
            ObservableList<User> users = FXCollections.observableArrayList();

            while (rs.next())
            {
                User user = new User(rs.getString("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("sexe"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("role"));

                user.setId(rs.getString("id"));
                user.setPrenom(rs.getString("prenom"));
                user.setNom(rs.getString("nom"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setSexe(rs.getString("sexe"));
                user.setRole(rs.getString("role"));

                users.add(user);
            }

            table.setItems(users);
            idcol.setCellValueFactory(f->f.getValue().idProperty());
            prenomcol.setCellValueFactory(f -> f.getValue().prenomProperty());
            nomcol.setCellValueFactory(f -> f.getValue().nomProperty());
            logcol.setCellValueFactory(f -> f.getValue().loginProperty());
            passcol.setCellValueFactory(f -> f.getValue().passwordProperty());
            sexecol.setCellValueFactory(f -> f.getValue().sexeProperty());
            rolecol.setCellValueFactory(f -> f.getValue().roleProperty());


        } catch (SQLException e) {
            Logger.getLogger(userControlleur.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    @FXML
    void supp(ActionEvent event) {

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));


        try
        {
            pst = con.prepareStatement("delete from user where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression");

            alert.setHeaderText("Supression");
            alert.setContentText("Utilisateur (" +id+") a ete Supprimer");

            alert.showAndWait();
            table();

        }
        catch (SQLException ex)
        {
            Logger.getLogger(userControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void erase(ActionEvent event)
    {
        txtpass.clear();
        txtlog.clear();
        txtprenom.clear();
        txtnom.clear();
        sexeBox.setValue(s_defaultValue);
        roleBox.setValue(r_defaultValue);
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
        ObservableList<User> users = FXCollections.observableArrayList();

        try
        {
            pst = con.prepareStatement("select id, prenom, nom, login, password, sexe, role  from user");
            rs = pst.executeQuery();

            while (rs.next())
            {
                User user = new User(rs.getString("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("sexe"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("role"));

                user.setId(rs.getString("id"));
                user.setPrenom(rs.getString("prenom"));
                user.setNom(rs.getString("nom"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setSexe(rs.getString("sexe"));
                user.setRole(rs.getString("role"));

                users.add(user);
            }

            table.setItems(users);
            idcol.setCellValueFactory(f->f.getValue().idProperty());
            prenomcol.setCellValueFactory(f -> f.getValue().prenomProperty());
            nomcol.setCellValueFactory(f -> f.getValue().nomProperty());
            logcol.setCellValueFactory(f -> f.getValue().loginProperty());
            passcol.setCellValueFactory(f -> f.getValue().passwordProperty());
            sexecol.setCellValueFactory(f -> f.getValue().sexeProperty());
            rolecol.setCellValueFactory(f -> f.getValue().roleProperty());

        }

        catch (SQLException ex)
        {
            Logger.getLogger(userControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory( tv -> {
            TableRow<User> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  table.getSelectionModel().getSelectedIndex();
                    id=Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

                    txtprenom.setText(table.getItems().get(myIndex).getPrenom());
                    txtnom.setText(table.getItems().get(myIndex).getNom());
                    txtlog.setText(String.valueOf(table.getItems().get(myIndex).getLogin()));
                    txtpass.setText(table.getItems().get(myIndex).getPassword());
                    sexeBox.setValue(table.getItems().get(myIndex).getSexe());
                    roleBox.setValue(table.getItems().get(myIndex).getRole());


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
        sexeBox.setItems(sexes);

        ObservableList<String> role = FXCollections.observableArrayList("Administrateur", "Utilisateur");
        roleBox.setItems(role);
    }

}