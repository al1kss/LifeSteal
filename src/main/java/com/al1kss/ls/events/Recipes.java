package com.al1kss.ls.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Recipes {
    public static void createRecipes(){
        ItemStack horn = new ItemStack(Material.GOAT_HORN, 1);
        ItemMeta hornm = horn.getItemMeta();
        hornm.setDisplayName("§6Horn of Life");
        hornm.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
        hornm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        hornm.setLore(Arrays.asList("Ходят слухи что если подуть, то тебе даруются вечная жизнь"));
        horn.setItemMeta(hornm);
        ShapedRecipe heart = new ShapedRecipe(horn);
        heart.shape(new String[]{"@#@", "$*$", "@#@"});
        heart.setIngredient('*', Material.TRIDENT);
        heart.setIngredient('@', Material.REDSTONE_BLOCK);
        heart.setIngredient('$', Material.TOTEM_OF_UNDYING);
        heart.setIngredient('#', Material.NETHERITE_INGOT);
        Bukkit.getServer().addRecipe(heart);
    }
}
