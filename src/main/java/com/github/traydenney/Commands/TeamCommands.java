package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.util.logging.ExceptionLogger;


public class TeamCommands {

    private SQLCommands callDatabaseTo = new SQLCommands();

    private void notifyUserAddedToTeam(User userToNotify, MessageAuthor authorOfMessage) {
        userToNotify.sendMessage("Hey " + userToNotify.getName() + "!" + " You were added to " + authorOfMessage.getName() + "'s team! Nice!");
    }

    public void addPlayerToDB(MessageCreateEvent event, MessageAuthor authorOfMessage) {
        User playerToAdd = event.getMessage().getMentionedUsers().get(0);
        notifyUserAddedToTeam(playerToAdd, authorOfMessage);

        // TODO: Get all mentioned users
        // TODO: Make it so that you cannot add yourself or a player that is already in team

        try {
            callDatabaseTo.addUser(authorOfMessage, playerToAdd);
        } catch(Exception e) {
            e.printStackTrace();
        }

        event.getChannel().sendMessage(playerToAdd.getNicknameMentionTag()+ " has been added to the team")
                .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
    }


    public void removePlayerFromDB(MessageCreateEvent event, MessageAuthor author) {
        User user = event.getMessage().getMentionedUsers().get(0);

        try {
            callDatabaseTo.removeUser(author.getName(), user.getNicknameMentionTag());
        } catch(Exception e) {
            e.printStackTrace();
        }

        event.getChannel().sendMessage(user.getNicknameMentionTag()+ " has been removed from your team")
                .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
    }

    public void notifyTeam(MessageCreateEvent event, MessageAuthor author) {
        // TODO: Make this work
        try {
            String[] players = callDatabaseTo.players(author.getName(), event);
            String msg = event.getMessageContent();

            for(int i = 0; i < players.length; i++ ) {
                User user = event.getServer().get().getMemberById(players[i]).get();
                user.sendMessage("Hey " + user.getName() + "! Janice here! Got a message from your coach." + "\n" + "```" + msg.substring(msg.indexOf(' ')) + "```");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

