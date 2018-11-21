package com.github.traydenney;

import com.github.traydenney.Commands.*;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import com.github.traydenney.SQLITE.DiscordDB;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.UserStatus;

public class Main {

    private static DiscordDB discordTableInDataBase = new DiscordDB();
    private static String token = discordTableInDataBase.getDiscordAuthToken();

    public static void main(String[] args) {

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        CommandHandler cmdHandler = new JavacordHandler(api);

        api.updateActivity(ActivityType.LISTENING, "You!");
        api.updateStatus(UserStatus.ONLINE);

        System.out.println("Bot has successfully started");
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

        cmdHandler.registerCommand(new MyTeam());
        cmdHandler.registerCommand(new AddPlayer());
        cmdHandler.registerCommand(new RemovePlayer());
        cmdHandler.registerCommand(new NotifyPlayers());
        cmdHandler.registerCommand(new AddCoach());
        cmdHandler.registerCommand(new SetTeamName());
        cmdHandler.registerCommand(new Replays());
        cmdHandler.registerCommand(new RemoveTeam());

    }
}
