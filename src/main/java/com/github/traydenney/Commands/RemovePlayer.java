package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class RemovePlayer implements CommandExecutor {

    private Server server;
    private User user;


    @Command(aliases = {"!remove"}, description = "Removes tagged player to the team")
    public void onCommand(DiscordApi api, Message message) {
        server = message.getServer().get();
        user = message.getAuthor().asUser().get();

        SQLCommands sqlCall = new SQLCommands();

        if(hasCoachRole()){
            try {
                sqlCall.removeUser(user, message.getMentionedUsers().get(0));
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
