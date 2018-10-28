package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.CoachRequests;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.ArrayList;
import java.util.List;



public class Team implements MessageCreateListener {

    String msg = "";
    String user = "";
    List<User> tagged = new ArrayList<User> ();
    String[] a;

    CoachRequests cr = new CoachRequests();


    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        // Get user's message
        msg = event.getMessage().getContent();

        //Split message to get argument
        String[] cmd = msg.split("\\s+");
        MessageAuthor author = event.getMessage().getAuthor();


        if(cmd[0].equalsIgnoreCase("!addplayer")) addPlayerToDB(event, author);


        if(cmd[0].equalsIgnoreCase("!myteam")) {

            try {
                CoachRequests test = new CoachRequests();
                test.getTeam(author.getName(), event);


            } catch(Exception e) {
                e.printStackTrace();
            }


        }

        if(cmd[0].equalsIgnoreCase("!removeplayer")) removePlayerFromDB(event, author);
    }

    public void addMember(User user, MessageAuthor author) {

        user.sendMessage("You have been added to the team by " + author.getDiscriminatedName());

    }

    private void addPlayerToDB(MessageCreateEvent event, MessageAuthor author) {
        User user = event.getMessage().getMentionedUsers().get(0);
        addMember(user, author);

        // TODO: Send mentioned player and author to sqlite class to populate DB

        try {

            cr.addUser(author.getName(), user.getNicknameMentionTag());


        } catch(Exception e) {
            e.printStackTrace();
        }



        event.getChannel().sendMessage(user.getNicknameMentionTag()+ " has been added to the team")
                .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));

    }


    private void removePlayerFromDB(MessageCreateEvent event, MessageAuthor author) {
        User user = event.getMessage().getMentionedUsers().get(0);


        try {
            cr.removeUser(author.getName(), user.getNicknameMentionTag());


        } catch(Exception e) {
            e.printStackTrace();
        }



        event.getChannel().sendMessage(user.getNicknameMentionTag()+ " has been removed from your team")
                .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));

    }

    // ADD CHECK TO SEE IF THE USER IS A COACH OR NOT, RETURN TRUE OR FALSE
}

