package com.github.traydenney.Commands;

import com.github.traydenney.Smash.Smash;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

public class SmashCommand implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String data;
        // check if message contains command
        if (event.getMessage().getContent().equalsIgnoreCase("!smash")) {
            Smash smash = new Smash();
            try {
                data = smash.getSmashEvent("https://api.smash.gg/tournament/pulsar-premier-league");
                System.out.println("test");
                event.getChannel().sendMessage(data)
                        .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
            } catch(Exception e) {

            }

        }
    }


}
