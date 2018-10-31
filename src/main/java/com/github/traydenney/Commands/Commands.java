package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.sql.SQLException;
import java.util.List;

public class Commands implements MessageCreateListener {

    private User user;
    private Server server;

    @Override
    public void onMessageCreate(MessageCreateEvent event)  {

        TeamCommands team = new TeamCommands();
        String msg;

        // Get user's message
        msg = event.getMessage().getContent();

        //Split message to get argument
        String[] cmd = msg.split("\\s+");

        // Get Message Author
        MessageAuthor author = event.getMessage().getAuthor();

        if(event.getServer().isPresent()) {
            server = event.getServer().get();
        }

        if(event.getMessageAuthor().asUser().isPresent()) {
            user = event.getMessageAuthor().asUser().get();
        }


        // Add Player
        if(cmd[0].equalsIgnoreCase("!addplayer")) {
            if(hasCoachRole(server, user)) {
                team.addPlayerToDB(event, author);
            } else {
                event.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
            }

        }

        // Remove Player
        if(cmd[0].equalsIgnoreCase("!removeplayer")) {
            if(hasCoachRole(server, user)) {
                team.removePlayerFromDB(event, author);
            } else {
                event.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
            }

        }

        if(cmd[0].equalsIgnoreCase("!notify")) {
            if(hasCoachRole(server, user)) {
                team.notifyTeam(event, author);
            } else {
                event.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
            }

        }


        // Return Members Of TeamCommands
        if(cmd[0].equalsIgnoreCase("!myteam")) {
            if(hasCoachRole(server, user)) {
                try {
                    SQLCommands test = new SQLCommands();

                    List<String> playerArray = test.team(author.getName(), event);

                    //TODO: Clean up code
                    event.getChannel().sendMessage("Your team consists of " + playerArray)
                            .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));

                } catch(Exception e) {
                    e.printStackTrace();
                }
            } else {
                event.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
            }
        }


        if(hasAdminRole(server, user)) {
            if (cmd[0].equalsIgnoreCase("!coach")) {
                CoachCommands cc = new CoachCommands();
                cc.addCoachRole(user, server, event);
            }
        } else {
            event.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
        }
    }

    private static boolean hasCoachRole(Server server, User user) {
        return server.getRoles(user).contains(server.getRolesByNameIgnoreCase("Coach").get(0));
    }

    private static boolean hasAdminRole(Server server, User user) {
        return server.isAdmin(user);
    }
}
