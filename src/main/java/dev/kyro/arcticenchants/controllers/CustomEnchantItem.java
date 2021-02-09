package dev.kyro.arcticenchants.controllers;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.enums.EnchantType;
import dev.kyro.arcticenchants.utilities.AOutput;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CustomEnchantItem implements Listener {

    //    When a player applies and enchant to an item
    @EventHandler(priority = EventPriority.LOWEST)
    public static void onInventoryClick(InventoryClickEvent event) {

        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getInventory();
        if (inventory == null || event.getCurrentItem() == null || event.getCursor() == null) return;

        ItemStack customEnchant = event.getCursor();
        ItemStack toApply = event.getCurrentItem();

        if(customEnchant.getType() == Material.AIR
                || customEnchant.getItemMeta() == null || customEnchant.getItemMeta().getDisplayName() == null
                || toApply.getType() == Material.AIR) return;

        CustomEnchant foundEnchant = null;
        for(CustomEnchant enchant : CustomEnchantManager.enchants) {

            if(customEnchant.getItemMeta().getDisplayName().contains(enchant.getName())) foundEnchant = enchant;
        }
        if(foundEnchant == null) return;

        boolean enabled = ArcticEnchants.INSTANCE.getConfig().getBoolean("settings.custom-enchants.enabled");
        if(!enabled) {

            AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.custom-enchants-disabled"));
            return;
        } else if (player.getGameMode() == GameMode.CREATIVE && customEnchant.getAmount() > 1) {

            AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.unstack-enchant"));
            return;
        } else if(!canApplyToItem(foundEnchant, toApply)) {

            AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.incompatible-enchant"));
            return;
        }

        int enchantLvl = getEnchantLevel(foundEnchant, customEnchant);
        ItemMeta newMeta = CustomEnchantManager.applyEnchant(player, toApply, foundEnchant, enchantLvl);
        if(newMeta != null) {

            toApply.setItemMeta(newMeta);
            customEnchant.setAmount(customEnchant.getAmount() - 1);
            player.setItemOnCursor(customEnchant);
            AOutput.send(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.applied-enchant"));
        }

        event.setCancelled(true);
        player.updateInventory();
    }

    public static int getEnchantLevel(CustomEnchant enchant, ItemStack item) {

        String name = item.getItemMeta().getDisplayName();

        if(name.contains(enchant.getName())) return Integer.parseInt(name.split(enchant.getName() + " ")[1]);

        return -1;
    }

    public static ItemStack createCustomEnchantItem(CustomEnchant customEnchant, int level) {

        EnchantType enchantType = customEnchant.getEnchantType();

        ItemStack enchantItem = new ItemStack(Material.FIREBALL, 1);

        ItemMeta itemMeta = enchantItem.getItemMeta();

        ArrayList<String> lore;
        lore = customEnchant.getItemLore(level);
        itemMeta.setLore(lore);

        switch(enchantType) {

            case WEAPON:
                itemMeta.setDisplayName(ChatColor.AQUA + customEnchant.getName() + " " + level);
                break;
            case ARMOR:
                itemMeta.setDisplayName(ChatColor.GOLD + customEnchant.getName() + " " + level);
                break;
            case TOOL:
                itemMeta.setDisplayName(ChatColor.RED + customEnchant.getName() + " " + level);
                break;
        }

        enchantItem.setItemMeta(itemMeta);
        return enchantItem;
    }

    public static boolean canApplyToItem(CustomEnchant customEnchant, ItemStack item) {

        Material material = null;
        switch(customEnchant.getEnchantApplyType()) {

            case SWORD:
                material = Material.DIAMOND_SWORD;
                break;
            case AXE:
                material = Material.DIAMOND_AXE;
                break;
            case HELMET:
                material = Material.DIAMOND_HELMET;
                break;
            case CHESTPLATE:
                material = Material.DIAMOND_CHESTPLATE;
                break;
            case LEGGINGS:
                material = Material.DIAMOND_LEGGINGS;
                break;
            case BOOTS:
                material = Material.DIAMOND_BOOTS;
                break;
            case PICKAXE:
                material = Material.DIAMOND_PICKAXE;
                break;
            case BOW:
                material = Material.BOW;
                break;
        }

        return material == item.getType();
    }
}
