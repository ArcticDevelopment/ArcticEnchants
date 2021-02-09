package dev.kyro.arcticenchants.commands;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.inventories.CustomEnchantInventory;
import dev.kyro.arcticenchants.utilities.AOutput;
import dev.kyro.arcticenchants.utilities.AUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnchanterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {

            System.out.println(ArcticEnchants.INSTANCE.getConfig().get("messages.player-only"));
            return false;
        }

        Player player = (Player) sender;

        if(AUtils.missingPermission(player, "aenchants.enchanter")) return false;

        boolean enabled = ArcticEnchants.INSTANCE.getConfig().getBoolean("settings.custom-enchants.enabled");
        if(!enabled) {

            AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.custom-enchants-disabled"));
            return false;
        }

        player.openInventory(CustomEnchantInventory.create().inventory);
        AOutput.playSound(player, "mob.bat.takeoff", 1000, 1);

        return false;
    }
}
