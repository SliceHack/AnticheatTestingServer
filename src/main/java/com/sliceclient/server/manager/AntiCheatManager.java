package com.sliceclient.server.manager;

import com.sliceclient.server.AntiCheatServer;
import com.sliceclient.server.anticheat.AntiCheat;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The AntiCheatManager class
 *
 * @author Nick
 * */
@Getter @Setter

public class AntiCheatManager {

    /** The list of AntiCheat instances */
    private final List<AntiCheat> antiCheats = new ArrayList<>();

    /** the selected AntiCheat */
    private AntiCheat selectedAntiCheat;

    /**
     * Constructor
     *
     * @param player the player to initialize the AntiCheatManager for
     * */
    public AntiCheatManager(Player player) {
        register(new AntiCheat("Vanilla", null, null, "No AntiCheat", Material.GRASS_BLOCK, player));

        FileConfiguration config = AntiCheatServer.getInstance().getYamlConfig();

        for(String key : config.getKeys(false)) {
            String name = config.getString(key + ".name"),
                    description = config.getString(key + ".description"),
                    bypassPermission = config.getString(key + ".bypassPermission"),
                    notifyPermission = config.getString(key + ".notifyPermission"),
                    material = config.getString(key + ".material");

            Material mat = Material.getMaterial(Objects.requireNonNull(material));

            if(name == null || description == null || bypassPermission == null || notifyPermission == null) throw new IllegalArgumentException("Invalid AntiCheat configuration");


            register(new AntiCheat(name, bypassPermission, notifyPermission, description, mat, player));
        }

        selectedAntiCheat = antiCheats.get(0);
    }

    /**
     * Adds an AntiCheat instance to the list of AntiCheats
     *
     * @param antiCheat The AntiCheat instance to add
     */
    public void register(AntiCheat antiCheat) {
        if(getAntiCheat(antiCheat.getName()) != null) return;

        antiCheats.add(antiCheat);
    }

    /**
     * Opens a gui for the player
     * */
    public void openGui() {
        Player player = AntiCheatServer.getInstance().getPlayerManager().get(this);
        Inventory inventory = Bukkit.createInventory(null, getGuiSize(), ChatColor.GOLD + "Please select an AntiCheat");

        for(AntiCheat antiCheat : antiCheats) {
            ItemStack itemStack = new ItemStack(antiCheat.getMaterial());
            ItemMeta itemMeta = itemStack.getItemMeta();

            if(itemMeta == null) throw new NullPointerException("ItemMeta is null");

            itemMeta.setDisplayName(ChatColor.RESET + antiCheat.getName());

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + antiCheat.getDescription());
            itemMeta.setLore(lore);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            if (antiCheat == selectedAntiCheat) itemMeta.addEnchant(Enchantment.LUCK, 1, true);

            itemMeta.setDisplayName(ChatColor.RESET + antiCheat.getName());

            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
        }

        player.openInventory(inventory);
    }


    /**
     * Set's up the anti cheat the player selects
     * */
    public void antiCheat(AntiCheat lastAntiCheat) {
        if(lastAntiCheat == selectedAntiCheat) return;

        antiCheats.forEach((antiCheat) -> antiCheat.setBypass(false));
        selectedAntiCheat.setBypass(true);
    }

    /**
     * Gets the size of the gui
     *
     * @return the size of the gui
     * */
    public AntiCheat getAntiCheat(int slot) {
        try {
            return antiCheats.get(slot);
        } catch (Exception ignored) {}
        return null;
    }

    /**
     * Gets the size for the gui
     *
     * @return the size for the gui
     * */
    public int getGuiSize() {
        int count = 0, size = 9;
        for(AntiCheat ignored : antiCheats) {
            count++;
            if(count % 9 == 0) size += 9;
        }
        return size;
    }

    /**
     * Gets an AntiCheat by name
     *
     * @return the selected AntiCheat
     * */
    public AntiCheat getAntiCheat(String name) {
        for(AntiCheat antiCheat : antiCheats) {
            if(antiCheat.getName().equals(name)) {
                return antiCheat;
            }
        }
        return null;
    }
}
