package com.github.traydenney.Commands;

import com.github.traydenney.SQLITE.SQLCommands;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;

public class AddCoach implements CommandExecutor {

    private Server server;
    private User user;
    private Role role;


    @Command(aliases = {"!coach"}, description = "Gives tagged user the coach role")
    public void onCommand(DiscordApi api, Message message) {
        server = message.getServer().get();
        user = message.getAuthor().asUser().get();

        if(message.getAuthor().isServerAdmin()) {
            if(server.getRolesByNameIgnoreCase("Coach").isEmpty()) {
                RoleBuilder roleBuilder = new RoleBuilder(server);
                roleBuilder.setName("Coach");
                roleBuilder.setColor(Color.CYAN);
                roleBuilder.create();

            } else {
                role = server.getRolesByNameIgnoreCase("Coach").get(0);
                server.addRoleToUser(message.getMentionedUsers().get(0),role);
            }



//            if(!server.getRolesByNameIgnoreCase("Coach").isEmpty()) {
//                role = server.getRolesByNameIgnoreCase("Coach").get(0);
//                server.addRoleToUser(message.getMentionedUsers().get(0),role);
//
//            } else {
//                RoleBuilder rb = new RoleBuilder(server);
//                rb.setName("Coach");
//                rb.setColor(Color.CYAN);
//                rb.create();
//            }
        } else {
            message.getChannel().sendMessage("You do not have permission to use that command. Please contact Admin");
        }
    }

    private boolean hasCoachRole() {
        return server.getRoles(user).contains(server.getRolesByNameIgnoreCase("Coach").get(0));
    }
}
