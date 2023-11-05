package com.example.tageeddinemehdi.Controlleurs;

import com.example.tageeddinemehdi.db;
import com.example.tageeddinemehdi.utilites.Outils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnexionControlleur implements Initializable {

    @FXML
    private TextField txtlog;

    @FXML
    private PasswordField txtpass;

    @FXML
    private ComboBox<String> typeuser;

    @FXML
    private Label wlcmtxt;

    private static String role;

    public static String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @FXML
    void OnConnect(ActionEvent event) throws SQLException {
        db connectNow = new db();
        Connection connectDb = connectNow.getConnection();
        PreparedStatement pstm;
        ResultSet rs;
        String sql ="select * from user where login =? AND password=? AND role=?";


        try{
            pstm = connectDb.prepareStatement(sql);
            pstm.setString(1, txtlog.getText().toString());

            pstm.setString(2, txtpass.getText().toString());
            pstm.setString(3,typeuser.getValue().toString());
            rs=pstm.executeQuery();

            /*Statement statement = connectDb.createStatement();
             rs = statement.executeQuery(sql);*/

            if (rs.next()){
                role = typeuser.getValue().toString();
                if(typeuser.getValue().equals("Administrateur")){
                    JOptionPane.showMessageDialog(null,"Connexion réuissie .Merci de cliquer sur ok pour aller dans votre espace");
                    Outils.load(event,"Bienvenu dans votre espace","/com/example/tageeddinemehdi/accueil.fxml");
                }else if(typeuser.getValue().equals("Utilisateur")){
                    JOptionPane.showMessageDialog(null,"Connexion réuissie .Merci de cliquer sur ok pour aller dans votre espace");
                    Outils.load(event,"Bienvenu dans votre espace","/com/example/tageeddinemehdi/accueil_user.fxml");
                    //lbletat.setText("Non conneté");
                }
            }else {
                wlcmtxt.setText("Non conneté");
                JOptionPane.showMessageDialog(null,"Merci de vérifier votre login et/ou mot de pass");
            }
            //lbletat.setText("conneté");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Administrateur","Utilisateur");
        typeuser.setItems(list);
    }

}
