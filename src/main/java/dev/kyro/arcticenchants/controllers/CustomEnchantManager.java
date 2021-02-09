package dev.kyro.arcticenchants.controllers;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.enums.EnchantType;
import dev.kyro.arcticenchants.utilities.AOutput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomEnchantManager {

    public static final ArrayList<CustomEnchant> enchants = new ArrayList<>();

    public static final ArrayList<CustomEnchant> weaponEnchants = new ArrayList<>();
    public static final ArrayList<CustomEnchant> armorEnchants = new ArrayList<>();
    public static final ArrayList<CustomEnchant> toolEnchants = new ArrayList<>();

//    Registers an enchant
    public static void registerEnchant(CustomEnchant enchant) {

        Bukkit.getServer().getPluginManager().registerEvents(enchant, ArcticEnchants.INSTANCE);
        enchants.add(enchant);

        switch(enchant.getEnchantType()) {

            case WEAPON:
                weaponEnchants.add(enchant);
                break;
            case ARMOR:
                armorEnchants.add(enchant);
                break;
            case TOOL:
                toolEnchants.add(enchant);
                break;
        }
    }

//    Enchants on a player
    public static HashMap<CustomEnchant, Integer> getActiveEnchants(Player player) {
        HashMap<CustomEnchant, Integer> activeEnchants = new HashMap<>();

        ArrayList<ItemStack> activeItems = new ArrayList<>();

        activeItems.add(player.getInventory().getHelmet());
        activeItems.add(player.getInventory().getChestplate());
        activeItems.add(player.getInventory().getLeggings());
        activeItems.add(player.getInventory().getBoots());


        for(ItemStack item : activeItems) {
            for(CustomEnchant enchant : enchants) {

                if(itemHasEnchant(item, enchant)) {

                    int level = getEnchantLevelFromItem(item, enchant);

                    activeEnchants.put(enchant, level);
                }
            }
        }

        for(CustomEnchant enchant : enchants) {

            if(enchant.getEnchantType() == EnchantType.ARMOR) continue;

            if(itemHasEnchant(player.getItemInHand(), enchant)) {

                int level = getEnchantLevelFromItem(player.getItemInHand(), enchant);

                activeEnchants.put(enchant, level);
            }
        }

        return activeEnchants;
    }

//    Gets the level of an enchant on an item
    public static int getEnchantLevelFromItem(ItemStack item, CustomEnchant enchant) {

        List<String> lore = item.getItemMeta().getLore();

        for(String loreItem : lore) {

            if(loreItem.contains(enchant.getName())) return Integer.parseInt(loreItem.split(enchant.getName() + " ")[1]);
        }

        return -1;
    }

//    Checks if a given item has an enchant
    public static boolean itemHasEnchant(ItemStack item, CustomEnchant enchant) {

        if(item == null || item.getItemMeta() == null) return false;
        ItemMeta itemMeta = item.getItemMeta();

        if(itemMeta.getLore() == null) return false;
        List<String> lore = itemMeta.getLore();

        for(String loreItem : lore) {

            if(loreItem.contains(enchant.getName())) return true;
        }

        return false;
    }

//    gets all the enchants on an item
    public static ArrayList<CustomEnchant> itemEnchants(ItemStack item) {

        ArrayList<CustomEnchant> enchantsOnItem = new ArrayList<>();

        for(CustomEnchant enchant : enchants) {

            if(itemHasEnchant(item, enchant)) enchantsOnItem.add(enchant);
        }

        return enchantsOnItem;
    }

//    Applies an enchant to an item
    public static ItemMeta applyEnchant(Player player, ItemStack item, CustomEnchant enchant, int level) {

        if(itemHasEnchant(item, enchant) && getEnchantLevelFromItem(item, enchant) >= level) {

            AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.has-enchant"));
            return null;
        }

        ItemMeta itemMeta = new ItemStack(item.getType()).getItemMeta();
        if(item.getItemMeta() != null) itemMeta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        if(itemMeta.getLore() != null) lore = itemMeta.getLore();

        boolean overwrite = false;
        for(int i = 0; i < lore.size(); i++) {

            if(lore.get(i).contains(enchant.getName())) {

                lore.set(i, enchant.getName() + " " + level);
                overwrite = true;
            }
        }
        if(!overwrite) lore.add(enchant.getName() + " " + level);
        itemMeta.setLore(lore);

        return itemMeta;
    }

//    Gets a random enchant of an ENCHANT type, NOT an enchant apply type
    public static CustomEnchant getRandomEnchant(EnchantType enchantType) {

        switch(enchantType) {

            case WEAPON:
                if(weaponEnchants.size() == 0) return null;
                return weaponEnchants.get((int) (Math.random() * weaponEnchants.size()));
            case ARMOR:
                if(armorEnchants.size() == 0) return null;
                return armorEnchants.get((int) (Math.random() * armorEnchants.size()));
            case TOOL:
                if(toolEnchants.size() == 0) return null;
                return toolEnchants.get((int) (Math.random() * toolEnchants.size()));
        }

        return null;
    }
}
