package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import com.github.traydenney.Util.MessageEventUtil;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.util.logging.ExceptionLogger;
import com.github.traydenney.Util.RoleUtil;

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

                List<String> playerArray = sqlCall.team(user);

                message.getChannel().sendMessage("Your team consists of " + playerArray);

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

