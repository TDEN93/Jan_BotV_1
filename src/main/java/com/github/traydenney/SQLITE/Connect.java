package com.github.traydenney.SQLITE;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.util.logging.ExceptionLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connect {

    private static Connection c;
    private static Statement stmt = null;
    private static boolean hasData = false;


    public void getTeam(String coach, MessageCreateEvent event) throws ClassNotFoundException, SQLException {

        List<String> players = new ArrayList<String>();

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SQLiteTest1.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM team;" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  coachName = rs.getString("coach");
                String  playerName = rs.getString("player");

                players.add(playerName);
            }

            String [] playerArray = players.toArray( new String[ players.size() ] );


            // TODO: Make this more efficient

            if(playerArray.length == 4) {
                event.getChannel().sendMessage("Your team consists of " + playerArray[0] + ", " + playerArray[1] + ", " + playerArray[2] + ", " + playerArray[3])
                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } else if(playerArray.length == 3) {
                event.getChannel().sendMessage("Your team consists of " + playerArray[0] + ", " + playerArray[1] + ", " + playerArray[2])
                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } else if(playerArray.length == 2) {
                event.getChannel().sendMessage("Your team consists of " + playerArray[0] + ", " + playerArray[1])
                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } else if(playerArray.length == 1) {
                event.getChannel().sendMessage("Your team consists of " + playerArray[0])
                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } else {
                event.getChannel().sendMessage("You don't have any players")
                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            }



            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

    }



    public void addUser(String coachName, String playerName) throws ClassNotFoundException, SQLException {

        if(c == null) {
            try {
                // db parameters
                String url = "jdbc:sqlite:SQLiteTest1.db";
                // create a connection to the database
                c = DriverManager.getConnection(url);

                System.out.println("Connection to SQLite has been established.");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        try {

            PreparedStatement prepStmt = c.prepareStatement("INSERT INTO team(coach, player) VALUES(?, ?);");


            prepStmt.setString(1, coachName);
            prepStmt.setString(2, playerName);
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }


    }

    // TODO: Create a coach/manager table with the values of id, coach, player, date

    // TODO: Create more useful methods to populate DB

}
