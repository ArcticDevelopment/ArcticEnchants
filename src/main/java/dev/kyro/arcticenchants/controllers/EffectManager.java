package dev.kyro.arcticenchants.controllers;

import dev.kyro.arcticenchants.ArcticEnchants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class EffectManager implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {

        updateEffects(event.getPlayer());
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent event) {

        updateEffects(event.getPlayer());
    }

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent event) {

        new BukkitRunnable() {
            @Override
            public void run() {

                updateEffects(event.getPlayer());
            }
        }.runTaskLater(ArcticEnchants.INSTANCE, 1L);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        new BukkitRunnable() {
            @Override
            public void run() {

                updateEffects(event.getEntity());
            }
        }.runTaskLater(ArcticEnchants.INSTANCE, 1L);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        if(event.getPlayer() == null) return;
        Player player = (Player) event.getPlayer();

        updateEffects(player);
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {

        if(event.getWhoClicked() == null) return;
        Player player = (Player) event.getWhoClicked();

        new BukkitRunnable() {
            @Override
            public void run() {
                updateEffects(player);
            }
        }.runTaskLater(ArcticEnchants.INSTANCE, 1L);
    }

    public void updateEffects(Player player) {

        if(player == null) return;

        HashMap<CustomEnchant, Integer> activeEnchants = CustomEnchantManager.getActiveEnchants(player);

        for(Map.Entry<CustomEnchant, Integer> map : activeEnchants.entrySet()) {

            map.getKey().enableEnchant(map.getValue(), player);
        }

        for(CustomEnchant enchant : CustomEnchantManager.enchants) {

            if(activeEnchants.containsKey(enchant)) {

                enchant.enableEnchant(activeEnchants.get(enchant), player);
            } else {

                enchant.disableEnchant(player);
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {

        new BukkitRunnable() {
            @Override
            public void run() {


            }
        }.runTaskLater(ArcticEnchants.INSTANCE, 10L);
    }
}
