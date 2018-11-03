package com.github.traydenney.Commands;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;


import java.awt.*;

public class CoachCommands {

    private long coachRoleID = Long.parseLong("506171081309028364");
    private User user;
    private Role role;

    public void addCoachRole(Server server, MessageCreateEvent event) {
        user = event.getMessage().getMentionedUsers().get(0);

        if(!server.getRolesByNameIgnoreCase("Coach").isEmpty()) {
           role = server.getRolesByNameIgnoreCase("Coach").get(0);
           server.addRoleToUser(user,role);

        } else {
            RoleBuilder rb = new RoleBuilder(server);
            rb.setName("Coach");
            rb.setColor(Color.CYAN);
            rb.create();
        }
    }

    public void scheduleReminder() {

    }

    public void setNotifcation() {

    }
}
