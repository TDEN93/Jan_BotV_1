package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;


import java.util.List;

public class Commands implements MessageCreateListener {

    private User currentEventUser;
    private Server server;
    private String currentEventMessage;
    private MessageAuthor authorOfMessage;

    @Override
    public void onMessageCreate(MessageCreateEvent event)  {

        TeamCommands team = new TeamCommands();
        System.out.println("test2");
        // Get currentEventUser's message
        currentEventMessage = event.getMessage().getContent();
        // Get Message Author
        authorOfMessage = event.getMessage().getAuthor();

        //Split message to get argument
        String[] cmd = currentEventMessage.split("\\s+");

        if(event.getServer().isPresent()) {
            server = event.getServer().get();
        }

        if(event.getMessageAuthor().asUser().isPresent()) {
            currentEventUser = event.getMessageAuthor().asUser().get();
        }

        // Add Player
        if(cmd[0].equalsIgnoreCase("!addplayer")) {
            if(hasCoachRole(server, currentEventUser)) {
                team.addPlayerToDB(event, authorOfMessage);
            } else {
                event.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
            }
        }

        // Remove Player
        if(cmd[0].equalsIgnoreCase("!removeplayer")) {
            if(hasCoachRole(server, currentEventUser)) {
                team.removePlayerFromDB(event, authorOfMessage);
            } else {
                event.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
            }
        }

        if(cmd[0].equalsIgnoreCase("!notify")) {
            if(hasCoachRole(server, currentEventUser)) {
                team.notifyTeam(event, authorOfMessage);
            } else {
                event.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
            }

        }

        // Return Members Of TeamCommands
        if(cmd[0].equalsIgnoreCase("!myteam")) {
            if(hasCoachRole(server, currentEventUser)) {
                try {
                    SQLCommands test = new SQLCommands();

                    List<String> playerArray = test.team(authorOfMessage.getName(), event);

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


        if(hasAdminRole(server, currentEventUser)) {
            if (cmd[0].equalsIgnoreCase("!coach")) {
                CoachCommands cc = new CoachCommands();
                cc.addCoachRole(server, event);
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
