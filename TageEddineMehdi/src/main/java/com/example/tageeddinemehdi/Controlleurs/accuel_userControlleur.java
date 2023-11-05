package com.example.tageeddinemehdi.Controlleurs;

import com.example.tageeddinemehdi.utilites.Outils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class accuel_userControlleur {

    @FXML
    private Pane paneVe1;

    @FXML
    private Pane paneVe2;

    @FXML
    private Pane paneVe3;

    @FXML
    private Pane paneVe4;

    @FXML
    void btnDeconnect(ActionEvent event)
    {
        Connection conn = null;
        try {
            // Créer une connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_fx", "root", "");

            // Faire ce que vous devez faire avec la base de données ici...

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Fermer la connexion à la base de données
                if (conn != null) {
                    conn.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Deconnexion");
                    alert.setHeaderText("Deconnexion");
                    alert.setContentText("Vous allez etre Deconnecter");
                    alert.showAndWait();
                    Outils.load(event,"Gestionnaire des Clients","/com/example/tageeddinemehdi/connexion.fxml");

                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btn_cli(ActionEvent event) throws IOException {
        Outils.load(event,"Gestionnaire des Clients","/com/example/tageeddinemehdi/client.fxml");
    }

    @FXML
    void btn_userA(ActionEvent event) {

    }

    @FXML
    void btn_vente(ActionEvent event) throws IOException {
        Outils.load(event,"Gestionnaire des Clients","/com/example/tageeddinemehdi/vente.fxml");
    }

    @FXML
    void btn_voiture(ActionEvent event) throws IOException {
        Outils.load(event,"Gestionnaire des Clients","/com/example/tageeddinemehdi/voiture.fxml");
    }

    @FXML
    void entinfoveA1(MouseEvent event) {
        paneVe1.setVisible(true);

    }

    @FXML
    void entinfoveA2(MouseEvent event) {
        paneVe2.setVisible(true);

    }

    @FXML
    void entinfoveA3(MouseEvent event) {
        paneVe3.setVisible(true);

    }

    @FXML
    void entinfoveA4(MouseEvent event) {
        paneVe4.setVisible(true);

    }

    @FXML
    void exinfoveA1(MouseEvent event) {
        paneVe1.setVisible(false);

    }

    @FXML
    void exinfoveA2(MouseEvent event) {
        paneVe2.setVisible(false);

    }

    @FXML
    void exinfoveA3(MouseEvent event) {
        paneVe3.setVisible(false);

    }

    @FXML
    void exinfoveA4(MouseEvent event) {
        paneVe4.setVisible(false);

    }

}
