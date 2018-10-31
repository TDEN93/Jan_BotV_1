package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.permission.Role;

import org.javacord.api.entity.server.ServerUpdater;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;
import org.javacord.core.entity.user.UserImpl;

import java.sql.SQLException;
import java.util.Collection;



public class TeamCommands {

    private SQLCommands cr = new SQLCommands();

    private void addMember(User user, MessageAuthor author) {

        user.sendMessage("Hey " + user.getName() + "!" + " You were added to " + author.getName() + "'s team! Nice!");

    }

    public void addPlayerToDB(MessageCreateEvent event, MessageAuthor author) {
        User user = event.getMessage().getMentionedUsers().get(0);
        addMember(user, author);

        // TODO: Send mentioned player and author to sqlite class to populate DB
        // TODO: Get all mentioned users
        // TODO: Make it so that you cannot add yourself or a player that is already in team

        try {

            cr.addUser(author.getName(), user.getNicknameMentionTag(), user.getIdAsString());


        } catch(Exception e) {
            e.printStackTrace();
        }



        event.getChannel().sendMessage(user.getNicknameMentionTag()+ " has been added to the team")
                .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));

    }


    public void removePlayerFromDB(MessageCreateEvent event, MessageAuthor author) {
        User user = event.getMessage().getMentionedUsers().get(0);


        try {
            cr.removeUser(author.getName(), user.getNicknameMentionTag());


        } catch(Exception e) {
            e.printStackTrace();
        }

        event.getChannel().sendMessage(user.getNicknameMentionTag()+ " has been removed from your team")
                .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));

    }


    public void notifyTeam(MessageCreateEvent event, MessageAuthor author) {
        // TODO: Make this work
        try {
            String[] players = cr.players(author.getName(), event);
            String msg = event.getMessageContent();


            for(int i = 0; i < players.length; i++ ) {
                User user = event.getServer().get().getMemberById(players[i]).get();
                user.sendMessage("Hey " + user.getName() + "! Janice here! Got a message from your coach." + "\n" + "```" + msg.substring(msg.indexOf(' ')) + "```");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        user.sendMessage("You have been added to the team by " + author.getDiscriminatedName());


    }
    // ADD CHECK TO SEE IF THE USER IS A COACH OR NOT, RETURN TRUE OR FALSE
}

