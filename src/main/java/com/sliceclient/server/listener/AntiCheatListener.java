package com.sliceclient.server.listener;

import com.sliceclient.server.AntiCheatServer;
import com.sliceclient.server.anticheat.AntiCheat;
import com.sliceclient.server.manager.AntiCheatManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.util.Objects;

public class AntiCheatListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        AntiCheatServer.getInstance().getPlayerManager().add(e.getPlayer());
        AntiCheatServer.getInstance().getPlayerManager().get(e.getPlayer()).antiCheat(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        AntiCheatServer.getInstance().getPlayerManager().remove(e.getPlayer());
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player)) return;

        Player p = (Player) e.getWhoClicked();
        String gui = e.getView().getTitle();
        AntiCheatManager manager = AntiCheatServer.getInstance().getPlayerManager().get(p);
        AntiCheat antiCheat = manager.getAntiCheat(e.getSlot());

        if(gui.equalsIgnoreCase(ChatColor.GOLD + "Please select an AntiCheat") && antiCheat != null) {
            AntiCheat lastAntiCheat = manager.getSelectedAntiCheat();

            manager.setSelectedAntiCheat(antiCheat);
            manager.antiCheat(lastAntiCheat);
            manager.openGui();

            e.setCancelled(true);
            p.sendMessage(ChatColor.GREEN + "You have selected " + ChatColor.GOLD + antiCheat.getName());
        }

    }
}
