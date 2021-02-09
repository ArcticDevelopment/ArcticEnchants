package dev.kyro.arcticenchants.inventories;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.controllers.CustomEnchantItem;
import dev.kyro.arcticenchants.controllers.CustomEnchantManager;
import dev.kyro.arcticenchants.enums.EnchantType;
import dev.kyro.arcticenchants.utilities.AInventoryBuilder;
import dev.kyro.arcticenchants.utilities.AOutput;
import dev.kyro.arcticenchants.utilities.Experience;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomEnchantInventory {

    public static String inventoryName = "Enchant GUI";

    public static void click(InventoryClickEvent event, Player player, Inventory openInventory, ItemStack clickedItem) {

        event.setCancelled(true);

        switch(clickedItem.getType()) {

            case DIAMOND_SWORD:
                purchaseEnchant(player, EnchantType.WEAPON,  1000);
                break;
            case DIAMOND_CHESTPLATE:
                purchaseEnchant(player, EnchantType.ARMOR,  1000);
                break;
            case DIAMOND_PICKAXE:
                purchaseEnchant(player, EnchantType.TOOL,  1000);
                break;
        }
    }

    public static AInventoryBuilder create() {

        return new AInventoryBuilder(null, 45, inventoryName)
                .createBorder(Material.STAINED_GLASS_PANE, 3)
                .setSlot(Material.DIAMOND_SWORD, 0 , 20, "&bWeapon Enchants", null)
                .setSlot(Material.DIAMOND_CHESTPLATE, 0 , 22, "&bArmor Enchants", null)
                .setSlot(Material.DIAMOND_PICKAXE, 0 , 24, "&bTool Enchants", null)
                .addEnchantGlint(true, 20, 22, 24);
    }

    public static void purchaseEnchant(Player player, EnchantType enchantType, int requiredExp) {

        if(Experience.getExp(player) < requiredExp) {

            AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.not-enough-xp"));
            return;
        }

        if(player.getInventory().firstEmpty() == -1) {

            AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.inventory-full"));
            return;
        }

        CustomEnchant randomEnchant = CustomEnchantManager.getRandomEnchant(enchantType);

        if(randomEnchant == null) {

            AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.no-enchant-in-category"));
            return;
        }

        Experience.changeExp(player, -requiredExp);

        int level = (int) (Math.random() * randomEnchant.getMaxLevel()) + 1;
        ItemStack customEnchantItem = CustomEnchantItem.createCustomEnchantItem(randomEnchant, level);
        player.getInventory().addItem(customEnchantItem);
    }
}
