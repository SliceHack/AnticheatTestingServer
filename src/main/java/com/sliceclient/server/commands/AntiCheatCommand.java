package com.sliceclient.server.commands;

import com.sliceclient.server.AntiCheatServer;
import com.sliceclient.server.manager.AntiCheatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AntiCheatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player player = (Player) sender;
        AntiCheatManager antiCheatManager = AntiCheatServer.getInstance().getPlayerManager().get(player);

        if(antiCheatManager == null) {
            AntiCheatServer.getInstance().getPlayerManager().add(player);
            antiCheatManager = AntiCheatServer.getInstance().getPlayerManager().get(player);
        }

        antiCheatManager.openGui();
        return true;
    }
}
