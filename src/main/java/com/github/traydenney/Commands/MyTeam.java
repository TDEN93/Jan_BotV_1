package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.util.logging.ExceptionLogger;


import java.util.List;

public class MyTeam implements CommandExecutor {

    private Server server;
    private User user;


    @Command(aliases = {"!myteam"}, description = "Shows players in team")
    public void onCommand(DiscordApi api, Message message) {
        server = message.getServer().get();
        user = message.getAuthor().asUser().get();

        if (hasCoachRole()) {
            try {
                SQLCommands sqlCall = new SQLCommands();

                List<String> playerArray = sqlCall.getTeam(user, server.getId());
                List<String> teamName = sqlCall.getTeamName(user, server.getId());

                if(playerArray.isEmpty()) {
                    message.getChannel().sendMessage("Looks like you don't have a team yet! Try adding them using !add <tag your player>");
                } else if (teamName.get(0) == null) {
                    message.getChannel().sendMessage("Your team consists of: " + playerArray);
                } else {
                    message.getChannel().sendMessage(teamName.get(0) + " consists of: " + playerArray);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            message.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
        }
    }

    private boolean hasCoachRole() {
        return server.getRoles(user).contains(server.getRolesByNameIgnoreCase("Coach").get(0));
    }
}

