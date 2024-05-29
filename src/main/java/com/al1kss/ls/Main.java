package com.al1kss.ls;

import com.al1kss.ls.commands.Commands;
import com.al1kss.ls.events.Events;
import com.al1kss.ls.events.Recipes;
import com.al1kss.ls.listeners.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Main extends JavaPlugin {

    private static String plugin;
    public static Main instance;

    public static String getPlugin() {
        return plugin;
    }

    public static void setPlugin(String plugin) {
        Main.plugin = plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getCommand("lifesteal").setExecutor(new Commands());
        getCommand("godmode").setExecutor(new Commands());
        getCommand("stats").setExecutor(new Commands());
        getCommand("heart").setExecutor(new Commands());
        getCommand("inventory").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);

        Recipes.createRecipes();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if(player.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                        player.setPlayerListName(ChatColor.RED + player.getName());
                        return;
                    } else{
                        player.setPlayerListName(player.getName());
                    }
                }
            }
        }.runTaskTimer(instance, 0, 20);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
