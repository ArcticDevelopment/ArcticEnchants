package dev.kyro.arcticenchants.commands;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.inventories.VanillaEnchantInventory;
import dev.kyro.arcticenchants.utilities.AInventoryBuilder;
import dev.kyro.arcticenchants.utilities.AOutput;
import dev.kyro.arcticenchants.utilities.AUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanillaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {

            System.out.println(ArcticEnchants.INSTANCE.getConfig().get("messages.player-only"));
            return false;
        }

        Player player = (Player) sender;

        if(AUtils.missingPermission(player, "aenchants.vguicommand")) return false;

        boolean enabled = ArcticEnchants.INSTANCE.getConfig().getBoolean("settings.vanilla-enchants.enabled");
        if(!enabled) {

            AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.vanilla-enchants-disabled"));
            return false;
        }

        AInventoryBuilder vanillaEnchantGUI = VanillaEnchantInventory.create(player);
        player.openInventory(vanillaEnchantGUI.inventory);
        AOutput.playSound(player, "mob.bat.takeoff", 1000, 1);

        return false;
    }
}
