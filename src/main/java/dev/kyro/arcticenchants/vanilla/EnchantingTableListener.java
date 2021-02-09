package dev.kyro.arcticenchants.vanilla;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.inventories.VanillaEnchantInventory;
import dev.kyro.arcticenchants.utilities.AInventoryBuilder;
import dev.kyro.arcticenchants.utilities.AOutput;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnchantingTableListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onClick(PlayerInteractEvent event) {

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        boolean enabled = ArcticEnchants.INSTANCE.getConfig().getBoolean("settings.vanilla-enchants.enabled");

        if(block.getType() != Material.ENCHANTMENT_TABLE || !enabled) return;

        AInventoryBuilder vanillaEnchantGUI = VanillaEnchantInventory.create(player);
        player.openInventory(vanillaEnchantGUI.inventory);
        AOutput.playSound(player, "mob.bat.takeoff", 1000, 1);

        event.setCancelled(true);
    }
}
