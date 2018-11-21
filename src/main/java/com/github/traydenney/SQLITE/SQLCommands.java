package com.github.traydenney.SQLITE;

import org.javacord.api.entity.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCommands {

    private static ResultSet resultSet = null;
    private static PreparedStatement PreparedSQLStatement = null;
    private String[] playerArray;
    private SQLConnect sqlConnect = new SQLConnect();

    public String[] getPlayers(User author, Long server_id) throws ClassNotFoundException, SQLException {

        List<String> players = new ArrayList<String>();

        sqlConnect.connect();

        try {

            PreparedSQLStatement = sqlConnect.connectToDatabase.prepareStatement("SELECT * from Teams WHERE server_id=? AND coach=?;");
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

        return playerArray;
    }

    public void addUser(User author, User user, Long server_id) throws ClassNotFoundException, SQLException {


        sqlConnect.connect();

        try {
            List<String> tn = getTeamName(author, server_id);

            PreparedSQLStatement = sqlConnect.connectToDatabase.prepareStatement("INSERT INTO Teams(coach, player, player_name, player_id, server_id, team_name) VALUES(?, ?, ?, ?, ?, ?);");

            PreparedSQLStatement.setString(1, author.getIdAsString());
            PreparedSQLStatement.setString(2, user.getNicknameMentionTag());
            PreparedSQLStatement.setString(3, user.getName());
            PreparedSQLStatement.setString(4, user.getIdAsString());
            PreparedSQLStatement.setLong(5, server_id);
            PreparedSQLStatement.setString(6, tn.get(0));
            PreparedSQLStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void removeUser(User author, User user, Long server_id) throws ClassNotFoundException, SQLException {

        sqlConnect.connect();

        try {

            PreparedSQLStatement = sqlConnect.connectToDatabase.prepareStatement("DELETE from Teams WHERE server_id=? AND player=?;");
            PreparedSQLStatement.setLong(1, server_id);
            PreparedSQLStatement.setString(2, user.getNicknameMentionTag());

            PreparedSQLStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public List<String> getTeam(User author, Long server_id) throws ClassNotFoundException, SQLException {
        List<String> players = new ArrayList<>();

        sqlConnect.connect();

        try {

            PreparedSQLStatement = sqlConnect.connectToDatabase.prepareStatement("SELECT player from Teams WHERE server_id=? AND coach=? ORDER BY player_name ASC;");
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
        sqlConnect.connect();

        try {

            PreparedSQLStatement = sqlConnect.connectToDatabase.prepareStatement("INSERT INTO Coaches(coach, team_name, server_id) VALUES(?, ?, ?);");
            PreparedSQLStatement.setString(1, author.getIdAsString());
            PreparedSQLStatement.setString(2, teamName);
            PreparedSQLStatement.setLong(3, server_id);
            PreparedSQLStatement.executeUpdate();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public List<String> getTeamName(User author, Long server_id) throws ClassNotFoundException, SQLException {
        List<String> team_name = new ArrayList<>();

        sqlConnect.connect();

        try {
            PreparedSQLStatement = sqlConnect.connectToDatabase.prepareStatement("SELECT team_name from Coaches WHERE server_id=? AND coach=?;");
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

    public void removeTeam(User author, Long server_id) throws ClassNotFoundException, SQLException {

        sqlConnect.connect();

        try {

            PreparedSQLStatement = sqlConnect.connectToDatabase.prepareStatement("DELETE FROM Teams WHERE server_id=? AND coach=?;");
            PreparedSQLStatement.setLong(1, server_id);
            PreparedSQLStatement.setString(2, author.getIdAsString());

            PreparedSQLStatement.executeUpdate();

            PreparedSQLStatement = sqlConnect.connectToDatabase.prepareStatement("DELETE FROM Coaches WHERE server_id=? AND coach=?;");
            PreparedSQLStatement.setLong(1, server_id);
            PreparedSQLStatement.setString(2, author.getIdAsString());

            PreparedSQLStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}