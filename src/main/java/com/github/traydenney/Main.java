package com.github.traydenney;

import com.github.traydenney.Commands.Commands;
import com.github.traydenney.Commands.SmashCommand;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import com.github.traydenney.Commands.ScheduleEvent;

import com.github.traydenney.SQLITE.DiscordDB;



public class Main {

    public static void main(String[] args) {

        DiscordDB dc = new DiscordDB();

        String token = dc.getToken();

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();


//        api.updateActivity(ActivityType.LISTENING, "with Optimus Prime");
//        api.updateStatus(UserStatus.ONLINE);

        System.out.println("Bot has successfully started");

        api.addMessageCreateListener(new Commands());
        api.addMessageCreateListener(new ScheduleEvent());
        api.addMessageCreateListener(new SmashCommand());


    }


}
