package dev.kyro.arcticenchants.inventories;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.utilities.AInventoryBuilder;
import dev.kyro.arcticenchants.utilities.AOutput;
import dev.kyro.arcticenchants.utilities.Experience;
import dev.kyro.arcticenchants.vanilla.VanillaEnchant;
import dev.kyro.arcticenchants.vanilla.VanillaEnchantManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class VanillaEnchantInventory {

    public static String inventoryName = "Vanilla Enchant GUI";
    public static int[] enchantSlots = new int[]{ 20, 21, 22, 23, 24, 29, 30, 31, 32, 33 };

    public static void click(InventoryClickEvent event, Player player, Inventory openInventory, ItemStack clickedItem) {

        event.setCancelled(true);

//        Add item to enchant gui
        if(event.getClickedInventory().getType() == InventoryType.PLAYER && clickedItem.getType() != Material.AIR && openInventory.getItem(49) == null) {

            updateEnchants(player, openInventory, clickedItem);

            openInventory.setItem(49, clickedItem.clone());
            player.getInventory().setItem(event.getSlot(), null);
        }

        if(event.getClickedInventory().getType() == InventoryType.CHEST && event.getSlot() == 49) {

            updateEnchants(openInventory);

            player.getInventory().addItem(clickedItem.clone());
            openInventory.setItem(49, null);
        }

        if(event.getClickedInventory().getType() == InventoryType.CHEST && openInventory.getItem(49) != null) {

            for(VanillaEnchant vanillaEnchant : VanillaEnchantManager.vanillaEnchants) {

                if(clickedItem.getType() == vanillaEnchant.displayItem.getType()) {

                    ItemStack enchantItem = openInventory.getItem(49);

                    int currentLvl = enchantItem.getEnchantmentLevel(vanillaEnchant.enchantment);

                    if(currentLvl >= vanillaEnchant.maxLvl) {

                        AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.too-high-level"));
                        return;
                    }

                    if(Experience.getExp(player) < vanillaEnchant.cost) {

                        AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.not-enough-xp"));
                        return;
                    }

                    Experience.changeExp(player, -vanillaEnchant.cost);
                    enchantItem.addUnsafeEnchantment(vanillaEnchant.enchantment, ++currentLvl);
                    updateEnchants(player, openInventory, enchantItem);
                    AOutput.playSound(player, "random.levelup", 1000, 1);
                    return;
                }
            }
        }
    }

    public static void close(InventoryCloseEvent event, Player player, Inventory openInventory) {

        if(openInventory.getItem(49) == null) return;

        player.getInventory().addItem(openInventory.getItem(49).clone());
    }

    public static AInventoryBuilder create(Player player) {

        return new AInventoryBuilder(null, 54, inventoryName)
                .setSlots(Material.STAINED_GLASS_PANE, 0, 2, 3, 5, 6, 18, 26, 27, 35, 47, 48, 50, 51)
                .setSlots(Material.STAINED_GLASS_PANE, 3, 1, 7, 9, 17, 36, 44, 46, 52)
                .setSlots(Material.STAINED_GLASS_PANE, 11, 0, 8, 45, 53)
                .setSlot(Material.EXP_BOTTLE, 0, 4, ChatColor.BLUE + "Current XP: " + ChatColor.AQUA + Experience.getExp(player), null);
    }

    public static void updateEnchants(Inventory inventory) {

        AInventoryBuilder inventoryBuilder = new AInventoryBuilder(inventory);

        for(int slot : enchantSlots) {

            inventoryBuilder.setSlots(Material.AIR, 0, slot);
        }
    }

    public static void updateEnchants(Player player, Inventory inventory, ItemStack itemStack) {

        AInventoryBuilder inventoryBuilder = new AInventoryBuilder(inventory);

        inventoryBuilder.setSlot(Material.EXP_BOTTLE, 0, 4, ChatColor.BLUE + "XP: " + ChatColor.AQUA + player.getTotalExperience(), null);

        int slot = 0;
        for(VanillaEnchant vanillaEnchant : VanillaEnchantManager.vanillaEnchants) {

            if(!vanillaEnchant.enchantment.canEnchantItem(itemStack)) continue;

            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + "Cost: " + vanillaEnchant.cost);
            lore.add(ChatColor.WHITE + "Level: " + itemStack.getEnchantmentLevel(vanillaEnchant.enchantment) + "/" + vanillaEnchant.maxLvl);

            inventoryBuilder.setSlot(vanillaEnchant.displayItem.getType(), vanillaEnchant.displayItem.getDurability(), enchantSlots[slot++],
                    vanillaEnchant.displayItem.getItemMeta().getDisplayName(), lore);
        }
    }
}
