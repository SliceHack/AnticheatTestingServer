package com.sliceclient.server;

import com.sliceclient.server.commands.AntiCheatCommand;
import com.sliceclient.server.listener.AntiCheatListener;
import com.sliceclient.server.manager.PlayerManager;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

@Getter
public final class AntiCheatServer extends JavaPlugin {

    /** AntiCheat config */
    private File file;
    private FileConfiguration yamlConfig;


    /** managers */
    private PlayerManager playerManager;


    @Override @SuppressWarnings("all")
    public void onEnable() {
        playerManager = new PlayerManager();
        file = new File(getDataFolder(), "antiCheat.yml");
        createAndLoadFile();

        registerListener(new AntiCheatListener());
        registerCommand("anticheat", new AntiCheatCommand());
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void registerCommand(String name, CommandExecutor commandExecutor) {
        Objects.requireNonNull(getCommand(name)).setExecutor(commandExecutor);
    }

    @SuppressWarnings("all")
    public void createAndLoadFile() {
        try {
            if(!file.exists()) {
                if(!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            yamlConfig = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AntiCheatServer getInstance() {
        return JavaPlugin.getPlugin(AntiCheatServer.class);
    }

}
