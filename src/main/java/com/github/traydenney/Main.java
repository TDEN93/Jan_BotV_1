package com.github.traydenney;

import com.github.traydenney.Commands.SmashCommand;
import com.github.traydenney.Commands.Team;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import com.github.traydenney.Commands.ScheduleEvent;

import com.github.traydenney.SQLITE.DiscordDB;

import java.io.IOException;
import java.net.MalformedURLException;

import java.sql.*;


public class Main {

    public static void main(String[] args)
            throws MalformedURLException, IOException, SQLException, ClassNotFoundException  {
        // Insert your bot's token here

        DiscordDB dc = new DiscordDB();

        String token = dc.getToken();

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        // Print the invite url of your bot
        System.out.println("Bot has successfully started");


//        DATABASE CONNECTION




        api.addMessageCreateListener(new Team());
        api.addMessageCreateListener(new ScheduleEvent());
        api.addMessageCreateListener(new SmashCommand());


    }


}
