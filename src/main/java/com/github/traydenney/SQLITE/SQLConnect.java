package com.github.traydenney.SQLITE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnect  {

    public Connection connectToDatabase = null;

    void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connectToDatabase = DriverManager.getConnection("jdbc:sqlite:JaniceDataBase.db");
    }
}
