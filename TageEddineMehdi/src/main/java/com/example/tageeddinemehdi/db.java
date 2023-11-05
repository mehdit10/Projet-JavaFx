package com.example.tageeddinemehdi;

import java.sql.*;

public class db {
    public Connection connection;

    public Connection getConnection() throws SQLException {
        String databaseName="gestion_fx";
        String databaseUser="root";
        String databasePassword="";
        String url="jdbc:mysql://localhost/" +databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,databaseUser,databasePassword);

            if (connection != null )
            {
                System.out.println("Conenxion reussi !!");
            } else {
                System.err.println("La connexion à la base de données est nulle.");
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return connection;
    }
}
