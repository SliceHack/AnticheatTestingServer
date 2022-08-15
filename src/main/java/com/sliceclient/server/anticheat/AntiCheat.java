package com.sliceclient.server.anticheat;

import com.sliceclient.server.AntiCheatServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.checkerframework.checker.units.qual.A;

import java.security.Permission;

/**
 * AntiCheat class used to manage the user's selected AntiCheat.
 *
 * @author Nick
 * */
@Getter @Setter
public class AntiCheat {

    /** Info */
    private String name, bypassPermission, notifyPermission, description;
    private Material material;
    private ItemStack itemStack;

    /** player */
    public Player player;

    /**
     * Constructor
     *
     * @param name the name of the AntiCheat
     * @param bypassPermission the permission to bypass the AntiCheat
     * @param notifyPermission the permission to notify the player of the AntiCheat
     * @param description the description of the AntiCheat
     * @param material the material of the AntiCheat
     * @param player the player to initialize the AntiCheat for
     * */
    public AntiCheat(String name, String bypassPermission, String notifyPermission, String description, Material material, Player player) {
        this.name = name;
        this.bypassPermission = bypassPermission;
        this.notifyPermission = notifyPermission;
        this.description = description;
        this.material = material;
        this.player = player;
    }

    /**
     * Allows the user to bypass the AntiCheat.
     *
     * @param bypass True if the user should be allowed to bypass the AntiCheat.
     * */
    public void setBypass(boolean bypass) {
        if(name.equalsIgnoreCase("Vanilla")) return;

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + bypassPermission + " " + !bypass);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + notifyPermission + " " + bypass);
    }
}
