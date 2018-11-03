package com.github.traydenney;

import com.github.traydenney.Commands.Commands;
import com.github.traydenney.Commands.SmashCommand;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import com.github.traydenney.Commands.ScheduleEvent;

import com.github.traydenney.SQLITE.DiscordDB;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.UserStatus;


public class Main {

    private static DiscordDB discordTableInDataBase = new DiscordDB();
    private static String token = discordTableInDataBase.getDiscordAuthToken();

    public static void main(String[] args) {

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.updateActivity(ActivityType.LISTENING, "You!");
        api.updateStatus(UserStatus.ONLINE);

        System.out.println("Bot has successfully started");

        api.addMessageCreateListener(new Commands());
        api.addMessageCreateListener(new ScheduleEvent());
        api.addMessageCreateListener(new SmashCommand());
    }
}
