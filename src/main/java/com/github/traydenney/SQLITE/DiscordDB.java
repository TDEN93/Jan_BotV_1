package com.github.traydenney.SQLITE;

import java.sql.*;

public class DiscordDB {

    private static String token;


    public String getDiscordAuthToken() {

        try {

            Connection connectToDatabase;

            Class.forName("org.sqlite.JDBC");
            connectToDatabase = DriverManager.getConnection("jdbc:sqlite:jan_db.db");
            connectToDatabase.setAutoCommit(false);

            Statement sqlStatement;

            sqlStatement = connectToDatabase.createStatement();

            ResultSet resultSet = sqlStatement.executeQuery( "SELECT * FROM discord;" );

            while ( resultSet.next() ) {
                token = resultSet.getString("token");
            }



            resultSet.close();
            sqlStatement.close();
            connectToDatabase.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        System.out.println("Token has been sent");
        return token;

    }


}
