package com.sliceclient.server.manager;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * The PlayerManager class
 *
 * @author Nick
 */
@Getter
public class PlayerManager {

    /** The list of players */
    private final HashMap<Player, AntiCheatManager> playerManagers = new HashMap<>();


    /**
     * Registers a player with the AntiCheatManager
     *
     * @param player The player to register
     * */
    public void add(Player player) {
        playerManagers.put(player, new AntiCheatManager(player));
    }

    /**
     * Unregisters a player from the AntiCheatManager
     *
     * @param player The player to unregister
     * */
    public void remove(Player player) {
        playerManagers.remove(player);
    }

    /**
     * Gets the AntiCheatManager for the player
     *
     * @param player The player to get the AntiCheatManager for
     * @return The AntiCheatManager for the player
     * */
    public AntiCheatManager get(Player player) {
        return playerManagers.get(player);
    }

    /**
     * Gets a player from an AntiCheat manager
     *
     * @param antiCheatManager the AntiCheatManager to get the player from
     * @return the player
     * */
    public Player get(AntiCheatManager antiCheatManager) {
        return playerManagers.entrySet().stream().filter(entry -> entry.getValue().equals(antiCheatManager)).findFirst().get().getKey();
    }
}
