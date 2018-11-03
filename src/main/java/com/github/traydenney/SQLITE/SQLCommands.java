package com.github.traydenney.SQLITE;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCommands {

    private static Connection connectToDatabase = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement PreparedSQLStatement = null;
    private String[] playerArray;



    public String[] players(String coach, MessageCreateEvent event) throws ClassNotFoundException, SQLException {

        List<String> players = new ArrayList<String>();


        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:jan_db.db");


        try {

            PreparedSQLStatement = connectToDatabase.prepareStatement("SELECT * from team WHERE coach=?;");
            PreparedSQLStatement.setString(1, coach);

            resultSet = PreparedSQLStatement.executeQuery();

            while (resultSet.next()) {
                int databaseID = resultSet.getInt("id");
                String coachName = resultSet.getString("coach");
                String playerName = resultSet.getString("player");
                String playerID = resultSet.getString("player_id");

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



    public void addUser(MessageAuthor author, User user) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:jan_db.db");

        // TODO: Sort in alphabetical order

        try {

            System.out.println(connectToDatabase);

            PreparedSQLStatement = connectToDatabase.prepareStatement("INSERT INTO team(coach, player, player_id) VALUES(?, ?, ?);");


            PreparedSQLStatement.setString(1, author.getName());
            PreparedSQLStatement.setString(2, user.getNicknameMentionTag());
            PreparedSQLStatement.setString(3, user.getIdAsString());
            PreparedSQLStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

    }


    public void removeUser(String coachName, String playerName) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:jan_db.db");

        try {

            PreparedSQLStatement = connectToDatabase.prepareStatement("DELETE from team WHERE player = ?;");

            PreparedSQLStatement.setString(1, playerName);

            PreparedSQLStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

    }

    public List<String> team(String coach, MessageCreateEvent event) throws ClassNotFoundException, SQLException {
        List<String> players = new ArrayList<>();


        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:jan_db.db");


        try {

            PreparedSQLStatement = connectToDatabase.prepareStatement("SELECT player from team WHERE coach=?;");
            PreparedSQLStatement.setString(1, coach);

            resultSet = PreparedSQLStatement.executeQuery();

            while (resultSet.next()) {
                String playerName = resultSet.getString("player");


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