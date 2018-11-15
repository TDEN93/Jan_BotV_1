package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;

public class NotifyPlayers implements CommandExecutor {
    private Server server;
    private User user;


    @Command(aliases = {"!notify"}, description = "Sends custom message to all players in your team")
    public void onCommand(DiscordApi api, Message message) {
        server = message.getServer().get();
        user = message.getAuthor().asUser().get();


        if (hasCoachRole()) {
            try {
                SQLCommands sqlCall = new SQLCommands();

                String[] playerArray = sqlCall.players(user);

                for(int i = 0; i < playerArray.length; i++) {
                    server.getMemberById(playerArray[i]).get().sendMessage("Hey " + user.getName() + "! Janice here! Got a message from your coach." + "\n" + "```" + message.getContent().substring(message.getContent().indexOf(' ')) + "```");
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
