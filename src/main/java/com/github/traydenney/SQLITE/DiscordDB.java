package com.github.traydenney.SQLITE;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.util.logging.ExceptionLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscordDB {

    private static String token;


    public String getToken() {

        try {

            Connection c;

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:jan_db.db");
            c.setAutoCommit(false);

            Statement stmt;

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM discord;" );

            while ( rs.next() ) {
                token = rs.getString("token");
            }



            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        System.out.println("Token has been sent");
        return token;

    }


}
