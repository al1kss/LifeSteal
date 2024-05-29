package com.al1kss.ls.listeners;

import com.al1kss.ls.Main;
import com.al1kss.ls.commands.Commands;
import com.al1kss.ls.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Listeners implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        PersistentDataContainer data = p.getPersistentDataContainer();
        if (data.get(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER) > 2){
            Events.RemoveHeart(p);
        } else {
            data.set(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER, 2);
        }


        Player killer = p.getKiller();
        if (killer instanceof Player){
            if (data.get(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER) != 2){
                Events.AddHeart(killer);
            }

            Events.AddKills(killer);

            String killerName = killer.getName();
            String weapon = killer.getInventory().getItemInMainHand().getType().toString();

            // Create player head item
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
            meta.setOwningPlayer(p);
            meta.setDisplayName("§l§3"+p.getName() + "'s Head");
            meta.setLore(Arrays.asList("Killed by: " + killerName, "\nWith: " + weapon));
            playerHead.setItemMeta(meta);
            p.getWorld().dropItemNaturally(p.getLocation(), playerHead);
        }

        int deathCount = data.get(new NamespacedKey(Main.getPlugin(), "deathcount"), PersistentDataType.INTEGER);
        deathCount++;
        data.set(new NamespacedKey(Main.getPlugin(), "deathcount"), PersistentDataType.INTEGER, deathCount);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                Events.setHearts(p);
            }
        }, 6);


    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        if (!p.getPersistentDataContainer().has(new NamespacedKey(Main.getPlugin(), "deathcount"), PersistentDataType.INTEGER)) {
            p.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "deathcount"), PersistentDataType.INTEGER, 0);
            p.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER, 24);
            p.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "kills"), PersistentDataType.INTEGER, 0);
        }
        if (!p.getPersistentDataContainer().has(new NamespacedKey(Main.getPlugin(), "horn-use"), PersistentDataType.INTEGER)){
            p.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "horn-use"), PersistentDataType.INTEGER, 0);

        }

        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 10));

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                Events.setHearts(p);
            }
        }, 6);

    }
//    @EventHandler
//    public void onPlayerChat(PlayerChatEvent event) {
//        if (event.getMessage().equalsIgnoreCase("/lifesteal") ||
//                event.getMessage().equalsIgnoreCase("/godmode") ||
//                event.getMessage().equalsIgnoreCase("/stats") ||
//                event.getMessage().equalsIgnoreCase("/hearts") ||
//                event.getMessage().equalsIgnoreCase("/inventory")) {
//            event.setCancelled(true);
//            new Commands().onCommand(event.getPlayer(), null, event.getMessage().substring(1), new String[]{});
//        } else{
//            Player p = event.getPlayer();
//            for (Player player : Bukkit.getServer().getOnlinePlayers()){
//                player.sendMessage("<" + p.getName() + "> " + event.getMessage());
//            }
//        }
//    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){
        Player p = event.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();
        ItemStack item = event.getItem();
        if (event.getAction().toString().contains("RIGHT_CLICK") && item != null && item.getType() == Material.GOAT_HORN && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Horn of Life")){
            item.setAmount(item.getAmount()-1);
            Events.AddHeart(p);
            List<String> stringList = item.getItemMeta().getLore();
            if (!stringList.contains(p.getName()) || !stringList.contains(p.getDisplayName()) || stringList.size() == 1) {
                int hornUse= data.get(new NamespacedKey(Main.getPlugin(), "horn-use"), PersistentDataType.INTEGER);
                hornUse++;
                data.set(new NamespacedKey(Main.getPlugin(), "horn-use"), PersistentDataType.INTEGER, hornUse);
            }

        }
    }
}
