package com.al1kss.ls.commands;

import com.al1kss.ls.events.Events;
import com.al1kss.ls.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class Commands implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("lifesteal")){
            if (args[0].equalsIgnoreCase("hearts")){
                if (args[1].equalsIgnoreCase("set") && args.length == 4){
                    Player p = Bukkit.getPlayer(args[2]);
                    if (p != null){
                        PersistentDataContainer data = p.getPersistentDataContainer();
                        data.set(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER, Integer.valueOf(args[3]));
                        Events.setHearts(p);
                    } else{
                        sender.sendMessage(ChatColor.RED + "Игрок не в сети");
                    }
                } else if (args[1].equalsIgnoreCase("add") && args.length == 3){
                    Player p = Bukkit.getPlayer(args[2]);
                    Events.AddHeart(p);
                } else if (args[1].equalsIgnoreCase("remove")){
                    Player p = Bukkit.getPlayer(args[2]);
                    Events.RemoveHeart(p);
                }
            }
            return true;
        }
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (command.getName().equalsIgnoreCase("godmode")){
                if (!p.isInvulnerable()){
                    p.setInvulnerable(true);
                    p.sendMessage(ChatColor.GOLD+ "You are in GODMODE!");
                } else{
                    p.setInvulnerable(false);
                    p.sendMessage(ChatColor.AQUA + "You are no longer in godmode");
                }
            } else if (command.getName().equalsIgnoreCase("stats") && sender.hasPermission("foo.bar")){
                if (args[0].equalsIgnoreCase("death")){
                    PersistentDataContainer data = p.getPersistentDataContainer();
                    int deathcount = data.get(new NamespacedKey(Main.getPlugin(), "deathcount"), PersistentDataType.INTEGER);
                    p.sendMessage(ChatColor.GREEN+ "Вы умерли "+deathcount+" раз");
                } else if(args[0].equalsIgnoreCase("kills")){
                    PersistentDataContainer data = p.getPersistentDataContainer();
                    int kills = data.get(new NamespacedKey(Main.getPlugin(), "kills"), PersistentDataType.INTEGER);
                    p.sendMessage(ChatColor.GREEN+ "Вы убили "+kills+" раз");
                } else if(args[0].equalsIgnoreCase("reset") && args.length == 1){
                    PersistentDataContainer data = p.getPersistentDataContainer();
                    data.set(new NamespacedKey(Main.getPlugin(), "kills"), PersistentDataType.INTEGER, 0);
                    data.set(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER, 24);
                    data.set(new NamespacedKey(Main.getPlugin(), "deathcount"), PersistentDataType.INTEGER, 0);
                    Events.setHearts(p);
                } else if(args[0].equalsIgnoreCase("reset") && args.length == 2 && p.hasPermission("permission.node")){
                    Player a = Bukkit.getPlayer(args[1]);
                    PersistentDataContainer data = a.getPersistentDataContainer();
                    data.set(new NamespacedKey(Main.getPlugin(), "kills"), PersistentDataType.INTEGER, 0);
                    data.set(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER, 24);
                    data.set(new NamespacedKey(Main.getPlugin(), "deathcount"), PersistentDataType.INTEGER, 0);
                    Events.setHearts(a);
                } else if(args[0].equalsIgnoreCase("all") && args.length == 1){
                    PersistentDataContainer data = p.getPersistentDataContainer();
                    int kills = data.get(new NamespacedKey(Main.getPlugin(), "kills"), PersistentDataType.INTEGER);
                    int hearts = data.get(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER);
                    int deaths = data.get(new NamespacedKey(Main.getPlugin(), "deathcount"), PersistentDataType.INTEGER);
                    p.sendMessage(ChatColor.GREEN + "У вас "+ kills+ " убийств!");
                    p.sendMessage(ChatColor.GREEN + "У вас "+ hearts/2 + " сердец!");
                    p.sendMessage(ChatColor.GREEN + "У вас "+ deaths+ " смертей!");
                } else if(args[0].equalsIgnoreCase("all") && args.length == 2 && p.hasPermission("permission.node")){
                    Player pl = Bukkit.getPlayer(args[1]);
                    PersistentDataContainer data = pl.getPersistentDataContainer();
                    int kills = data.get(new NamespacedKey(Main.getPlugin(), "kills"), PersistentDataType.INTEGER);
                    int hearts = data.get(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER);
                    int deaths = data.get(new NamespacedKey(Main.getPlugin(), "deathcount"), PersistentDataType.INTEGER);
                    int hornUse = data.get(new NamespacedKey(Main.getPlugin(), "horn-use"), PersistentDataType.INTEGER);
                    p.sendMessage(ChatColor.GREEN + "У "+pl.getName() +" "+ kills+ " убийств!");
                    p.sendMessage(ChatColor.GREEN + "У " +pl.getName()+" "+ hearts/2 + " сердец!");
                    p.sendMessage(ChatColor.GREEN + "У "+pl.getName()+" "+ deaths+ " смертей!");
                    p.sendMessage(ChatColor.GREEN + "У "+pl.getName()+" "+ hornUse+ " использований horn!");
                }
            } else if (command.getName().equalsIgnoreCase("heart") && sender.hasPermission("foo.bar")){
                PersistentDataContainer data = p.getPersistentDataContainer();
                int hearts = data.get(new NamespacedKey(Main.getPlugin(), "hearts"), PersistentDataType.INTEGER);
                if (hearts > 2){
                    Events.RemoveHeart(p);

                    ItemStack horn = new ItemStack(Material.GOAT_HORN, 1);
                    ItemMeta hornm = horn.getItemMeta();
                    hornm.setDisplayName("§6Horn of Life");
                    hornm.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
                    hornm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    hornm.setLore(Arrays.asList("Ходят слухи что если подуть, то тебе даруются вечная жизнь", p.getName()));
                    horn.setItemMeta(hornm);

                    p.getInventory().addItem(horn);

                } else {
                    p.sendMessage(ChatColor.RED+ "Нельзя забрать своё последнее сердце");
                }
            } else if (command.getName().equalsIgnoreCase("inventory")) {
                //
                p.sendMessage("Вы просматриваете инвентарь игрока");
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("lifesteal")) {
            if (args.length == 1) {
                return Arrays.asList("hearts");
            }
            if (args.length == 2) {
                return Arrays.asList("set", "add", "remove");
            }
            if (args.length == 3) {
                return null;
            }
        }else if (command.getName().equalsIgnoreCase("stats")){
            if (args.length == 1){
                return Arrays.asList("kills", "death", "all");
            }
        }
        return null;
    }
}
