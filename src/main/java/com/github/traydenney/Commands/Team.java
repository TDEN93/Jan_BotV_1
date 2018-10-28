package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.Connect;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;
import org.javacord.api.event.user.UserEvent;

import java.util.ArrayList;
import java.util.List;



public class Team implements MessageCreateListener {

    String msg = "";
    String user = "";
    List<User> tagged = new ArrayList<User> ();
    String[] a;


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
                Connect test = new Connect();
                test.getTeam(author.getName(), event);


            } catch(Exception e) {
                e.printStackTrace();
            }


        }
    }

    public void addMember(User user, MessageAuthor author) {

        user.sendMessage("You have been added to the team by " + author.getDiscriminatedName());

    }

    private void addPlayerToDB(MessageCreateEvent event, MessageAuthor author) {
        User user = event.getMessage().getMentionedUsers().get(0);
        addMember(user, author);

        // TODO: Send mentioned player and author to sqlite class to populate DB

        try {
            Connect test = new Connect();
            test.addUser(author.getName(), user.getNicknameMentionTag());


        } catch(Exception e) {
            e.printStackTrace();
        }



        event.getChannel().sendMessage(user.getNicknameMentionTag()+ " has been added to the team")
                .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));

        System.out.println(user.getNicknameMentionTag());
    }

    // ADD CHECK TO SEE IF THE USER IS A COACH OR NOT, RETURN TRUE OR FALSE
}

