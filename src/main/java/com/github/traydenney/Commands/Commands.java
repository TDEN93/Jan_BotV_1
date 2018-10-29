package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.sql.SQLException;

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
        if(cmd[0].equalsIgnoreCase("!addplayer")) team.addPlayerToDB(event, author);
        // Remove Player
        if(cmd[0].equalsIgnoreCase("!removeplayer")) team.removePlayerFromDB(event, author);

        if(cmd[0].equalsIgnoreCase("!notify")) team.notifyTeam(event, author);


        // Return Members Of TeamCommands
        if(cmd[0].equalsIgnoreCase("!myteam")) {

            try {
                SQLCommands test = new SQLCommands();
                test.players(author.getName(), event);


            } catch(Exception e) {
                e.printStackTrace();
            }


        }

        if (cmd[0].equalsIgnoreCase("!coach")) {
            CoachCommands cc = new CoachCommands();
            cc.addCoachRole(user, server);
        }



    }
}
