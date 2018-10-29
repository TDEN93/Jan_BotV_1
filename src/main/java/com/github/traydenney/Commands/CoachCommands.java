package com.github.traydenney.Commands;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.server.role.UserRoleAddEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class CoachCommands {

    public void addCoachRole(User user, Server server) {

        long coachRoleID = Long.parseLong("506171081309028364");

        server.addRoleToUser(user,server.getRoleById(coachRoleID).get());

    }
}
