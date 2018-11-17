package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.sql.SQLException;
import java.util.List;

public class AddPlayer implements CommandExecutor {

    private Server server;
    private User user;


    @Command(aliases = {"!add"}, description = "Adds tagged player to the team")
    public void onCommand(DiscordApi api, Message message) {
        server = message.getServer().get();
        user = message.getAuthor().asUser().get();

        SQLCommands sqlCall = new SQLCommands();

        if(hasCoachRole()){
            try {
                sqlCall.addUser(user, message.getMentionedUsers().get(0), server.getId());
                message.getMentionedUsers().get(0).sendMessage("Hey " + message.getMentionedUsers().get(0).getName() + " ! You have been added to " +
                                                                    user.getName() + "'s team on the " + server.getName() + " server. Congratulations!");

            } catch (Exception e) {

            }
        } else {
            message.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
        }


    }

    private boolean hasCoachRole() {
        return server.getRoles(user).contains(server.getRolesByNameIgnoreCase("Coach").get(0));
    }

}
