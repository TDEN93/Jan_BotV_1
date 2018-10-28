package com.github.traydenney.Commands;

import org.javacord.api.entity.activity.Activity;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.awt.*;

public class ScheduleEvent implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        // check if message contains command
        if (event.getMessage().getContent().equalsIgnoreCase("!events")) {

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Upcoming Event")
                    .addField("Event Name", "ESL", true)
                    .addField("Date", "100 years from now", true)
                    .addField("Prize Pool", "$100", true)
                    .setColor(Color.BLACK)
                    .setImage("https://cdn.eslgaming.com/misc/media/cm/logo/esl.logo.2014/ESLPlay_Logotype_light.png")
                    .setThumbnail("https://cdn.eslgaming.com/misc/media/cm/logo/esl.logo.2014/ESLPlay_Logotype_light.png");

            // Keep in mind that a message author can either be a webhook or a normal user
//            author.asUser().ifPresent(user -> {
//                embed.addField("Registered", "False", true);
//                // The User#getActivity() method returns an Optional
////                embed.addField("Activity", user.getActivity().map(Activity::getName).orElse("none"), true);
//            });
//            // Keep in mind that messages can also be sent as private messages
//            event.getMessage().getServer()
//                    .ifPresent(server -> embed.addField("Team Manager", "God", true));
            // Send the embed. It logs every exception, besides missing permissions (you are not allowed to send message in the channel)
            event.getChannel().sendMessage(embed)
                    .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
        }
    }

}
