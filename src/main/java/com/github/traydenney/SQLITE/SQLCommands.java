package com.github.traydenney.SQLITE;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCommands {

    //TODO: Move DB connection to separate method. Don't reconnect each time.
    private static Connection connectToDatabase = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement PreparedSQLStatement = null;
    private String[] playerArray;


    public String[] getPlayers(User author, Long server_id) throws ClassNotFoundException, SQLException {

        List<String> players = new ArrayList<String>();

        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:JaniceDataBase.db");

        try {

            PreparedSQLStatement = connectToDatabase.prepareStatement("SELECT * from Teams WHERE server_id=? AND coach=?;");
            PreparedSQLStatement.setLong(1, server_id);
            PreparedSQLStatement.setString(2, author.getIdAsString());

            resultSet = PreparedSQLStatement.executeQuery();

            while (resultSet.next()) {
//                int databaseID = resultSet.getInt("id");
//                String coachName = resultSet.getString("coach");
//                String playerName = resultSet.getString("player");
                String playerID = resultSet.getString("player_id");

                players.add(playerID);
            }

            playerArray = players.toArray(new String[0]);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        //TODO: Return list instead of array to save time
        return playerArray;
    }

    public void addUser(User author, User user, Long server_id) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:JaniceDataBase.db");

        // TODO: Sort in alphabetical order

        try {

            PreparedSQLStatement = connectToDatabase.prepareStatement("INSERT INTO Teams(coach, player, player_id, server_id) VALUES(?, ?, ?, ?);");

            PreparedSQLStatement.setString(1, author.getIdAsString());
            PreparedSQLStatement.setString(2, user.getNicknameMentionTag());
            PreparedSQLStatement.setString(3, user.getIdAsString());
            PreparedSQLStatement.setLong(4, server_id);
            PreparedSQLStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void removeUser(User author, User user, Long server_id) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:JaniceDataBase.db");

        try {

            PreparedSQLStatement = connectToDatabase.prepareStatement("DELETE from Teams WHERE server_id=? AND player=?;");
            PreparedSQLStatement.setLong(1, server_id);
            PreparedSQLStatement.setString(2, user.getNicknameMentionTag());

            PreparedSQLStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public List<String> getTeam(User author, Long server_id) throws ClassNotFoundException, SQLException {
        List<String> players = new ArrayList<>();

        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:JaniceDataBase.db");

        try {

            PreparedSQLStatement = connectToDatabase.prepareStatement("SELECT player from Teams WHERE server_id=? AND coach=?;");
            PreparedSQLStatement.setLong(1, server_id);
            PreparedSQLStatement.setString(2, author.getIdAsString());

            resultSet = PreparedSQLStatement.executeQuery();

            while (resultSet.next()) {
                String playerName = resultSet.getString("player");

                players.add(playerName);
            }

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return players;
    }

    public void setTeamName(User author, String teamName, Long server_id) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:JaniceDataBase.db");

        try {

            PreparedSQLStatement = connectToDatabase.prepareStatement("UPDATE Teams SET team_name=? WHERE server_id=? AND coach=?;");
            PreparedSQLStatement.setString(1, teamName);
            PreparedSQLStatement.setLong(2, server_id);
            PreparedSQLStatement.setString(3, author.getIdAsString());
            PreparedSQLStatement.executeUpdate();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public List<String> getTeamName(User author, Long server_id) throws ClassNotFoundException, SQLException {
        List<String> team_name = new ArrayList<>();

        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:JaniceDataBase.db");

        try {
            PreparedSQLStatement = connectToDatabase.prepareStatement("SELECT team_name from Teams WHERE server_id=? AND coach=?;");
            PreparedSQLStatement.setLong(1, server_id);
            PreparedSQLStatement.setString(2, author.getIdAsString());

            resultSet = PreparedSQLStatement.executeQuery();

            while (resultSet.next()) {
                String tn = resultSet.getString("team_name");

                team_name.add(tn);
            }

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return team_name;
    }
}