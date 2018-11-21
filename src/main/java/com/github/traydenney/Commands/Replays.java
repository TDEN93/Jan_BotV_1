package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;

public class Replays implements CommandExecutor {
    private Server server;
    private User user;

    private String replayURL;


    @Command(aliases = {"!replays"}, description = "Gets replays from link")
    public void onCommand(DiscordApi api, Message message) {
        server = message.getServer().get();
        user = message.getAuthor().asUser().get();

        try {
            SQLCommands sqlCall = new SQLCommands();

            String[] playerArray = sqlCall.getPlayers(user, server.getId());

            if(hasCoachRole()) {

                if(playerArray.length == 0) {
                    message.getChannel().sendMessage("Looks like you don't have a team yet! Try adding them using !add <tag your player>");
                } else if(playerArray.length == 1) {
                    replayURL = "https://ballchasing.com/?player-name=" + server.getMemberById(playerArray[0]).get().getName() + "&sort-by=date&sort-dir=desc";
                } else if(playerArray.length == 2) {
                    replayURL = "https://ballchasing.com/?player-name=" + server.getMemberById(playerArray[0]).get().getName() + "&player-name=" + server.getMemberById(playerArray[1]).get().getName() + "&sort-by=date&sort-dir=desc";
                } else {
                    replayURL = "https://ballchasing.com/?player-name=" + server.getMemberById(playerArray[0]).get().getName() + "&player-name=" + server.getMemberById(playerArray[1]).get().getName() + "&player-name=" + server.getMemberById(playerArray[2]).get().getName() + "&sort-by=date&sort-dir=desc";
                }
                message.getChannel().sendMessage(replayURL);
            } else {
                message.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean hasCoachRole() {
        return server.getRoles(user).contains(server.getRolesByNameIgnoreCase("Coach").get(0));
    }
}
