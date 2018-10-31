package com.github.traydenney.Commands;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.MessageEvent;
import org.javacord.api.event.server.role.UserRoleAddEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class CoachCommands {


    public void addCoachRole(User user, Server server, MessageEvent event) {

        long coachRoleID = Long.parseLong("506171081309028364");



        if(!server.getRolesByNameIgnoreCase("Coach").isEmpty()) {
           Role role = server.getRolesByNameIgnoreCase("Coach").get(0);
           server.addRoleToUser(user,role);

        } else {
            RoleBuilder rb = new RoleBuilder(server);
            rb.setName("Coach");
            rb.setColor(Color.CYAN);
            rb.create();

            event.getChannel().sendMessage("Role has been created, please type !coach again");
        }
    }

    public void scheduleReminder() {

    }

    public void setNotifcation() {

    }
}
