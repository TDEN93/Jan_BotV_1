package com.github.traydenney.SQLITE;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.util.logging.ExceptionLogger;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCommands {

    private static Connection c = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;
    private String[] playerArray;



    public String[] players(String coach, MessageCreateEvent event) throws ClassNotFoundException, SQLException {

        List<String> players = new ArrayList<String>();


        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:jan_db.db");


        try {

            ps = c.prepareStatement("SELECT * from team WHERE coach=?;");
            ps.setString(1, coach);

            rs  = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String coachName = rs.getString("coach");
                String playerName = rs.getString("player");
                String playerID = rs.getString("player_id");

                players.add(playerID);
            }

            playerArray = players.toArray(new String[0]);

//
//
//            // TODO: Make this more efficient
//
//            if(playerArray.length == 4) {
//                event.getChannel().sendMessage("Your team consists of " + playerArray[0] + ", " + playerArray[1] + ", " + playerArray[2] + ", " + playerArray[3])
//                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
//            } else if(playerArray.length == 3) {
//                event.getChannel().sendMessage("Your team consists of " + playerArray[0] + ", " + playerArray[1] + ", " + playerArray[2])
//                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
//            } else if(playerArray.length == 2) {
//                event.getChannel().sendMessage("Your team consists of " + playerArray[0] + ", " + playerArray[1])
//                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
//            } else if(playerArray.length == 1) {
//                event.getChannel().sendMessage("Your team consists of " + playerArray[0])
//                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
//            } else {
//                event.getChannel().sendMessage("You don't have any players")
//                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
//            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        //TODO: Return list instead of array to save time
        return playerArray;

    }



    public void addUser(String coachName, String playerName, String player_id) throws ClassNotFoundException, SQLException {
        System.out.println(c);

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:jan_db.db");

        // TODO: Sort in alphabetical order

        try {

            System.out.println(c);

            ps = c.prepareStatement("INSERT INTO team(coach, player, player_id) VALUES(?, ?, ?);");


            ps.setString(1, coachName);
            ps.setString(2, playerName);
            ps.setString(3, player_id);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

    }


    public void removeUser(String coachName, String playerName) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:jan_db.db");

        try {

            ps = c.prepareStatement("DELETE from team WHERE player = ?;");

            ps.setString(1, playerName);

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

    }

    public List<String> team(String coach, MessageCreateEvent event) throws ClassNotFoundException, SQLException {
        List<String> players = new ArrayList<>();


        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:jan_db.db");


        try {

            ps = c.prepareStatement("SELECT player from team WHERE coach=?;");
            ps.setString(1, coach);

            rs  = ps.executeQuery();

            while (rs.next()) {
                String playerName = rs.getString("player");


                players.add(playerName);
            }

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        //TODO: Return list instead of array to save time
        return players;
    }

    // TODO: Create a coach/manager table with the values of id, coach, player, date

    // TODO: Create more useful methods to populate DB

}