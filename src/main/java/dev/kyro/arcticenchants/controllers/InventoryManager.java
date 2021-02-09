package dev.kyro.arcticenchants.controllers;

import dev.kyro.arcticenchants.inventories.CustomEnchantInventory;
import dev.kyro.arcticenchants.inventories.VanillaEnchantInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager implements Listener {

    public InventoryManager() {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if(event.getClickedInventory() == null) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        Inventory openInventory = event.getInventory();

        String inventoryName = openInventory.getName();

        if(inventoryName.equals(CustomEnchantInventory.inventoryName)) {

            CustomEnchantInventory.click(event, player, openInventory, clickedItem);
        }

        if(inventoryName.equals(VanillaEnchantInventory.inventoryName)) {

            VanillaEnchantInventory.click(event, player, openInventory, clickedItem);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        if(!(event.getPlayer() instanceof Player)) return;

        Player player = (Player) event.getPlayer();
        Inventory openInventory = event.getInventory();

        String inventoryName = openInventory.getName();

        if(inventoryName.equals(VanillaEnchantInventory.inventoryName)) {

            VanillaEnchantInventory.close(event, player, openInventory);
        }
    }
}
