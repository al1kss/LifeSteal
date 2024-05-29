package com.al1kss.ls.events;

import com.al1kss.ls.Main;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Events {
    public static void RemoveHeart(Player p){
        PersistentDataContainer data = p.getPersistentDataContainer();
        int hearts = data.get(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER);

        if (hearts == 2 || hearts < 2){
            hearts = 4;
        }

        hearts--;
        hearts--;
        data.set(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER, hearts);
        p.sendMessage(ChatColor.RED + "У вас теперь "+ ChatColor.GOLD + hearts/2 + ChatColor.RED+ " сердец!");

        setHearts(p);
    }

    public static void AddHeart(Player p){
        PersistentDataContainer data = p.getPersistentDataContainer();
        int hearts = data.get(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER);
        hearts++;
        hearts++;
        data.set(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER, hearts);
        p.sendMessage(ChatColor.GREEN + "У вас теперь "+ ChatColor.GOLD + hearts/2 + ChatColor.GREEN + " сердец!");
        setHearts(p);
    }

    public static void AddKills(Player p){
        PersistentDataContainer data = p.getPersistentDataContainer();
        int kills = data.get(new NamespacedKey(Main.getPlugin(), "kills"), PersistentDataType.INTEGER);
        kills++;
        data.set(new NamespacedKey(Main.getPlugin(), "kills"), PersistentDataType.INTEGER, kills);
        p.sendMessage(ChatColor.GREEN + "Вы убили "+ ChatColor.GOLD + kills + ChatColor.GREEN + " игроков!");
    }

    public static void setHearts(Player p){
        PersistentDataContainer data = p.getPersistentDataContainer();
        int hearts = data.get(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER);
        p.setMaxHealth(hearts);
        //p.sendMessage(String.valueOf(p.getMaxHealth()));
    }

}
