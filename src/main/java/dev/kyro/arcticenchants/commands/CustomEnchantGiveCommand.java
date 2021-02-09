package dev.kyro.arcticenchants.commands;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.controllers.CustomEnchantItem;
import dev.kyro.arcticenchants.controllers.CustomEnchantManager;
import dev.kyro.arcticenchants.utilities.AOutput;
import dev.kyro.arcticenchants.utilities.AUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class CustomEnchantGiveCommand implements CommandExecutor {

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {

            System.out.println(ArcticEnchants.INSTANCE.getConfig().get("messages.player-only"));
            return false;
        }

        Player player = (Player) sender;

        if(AUtils.missingPermission(player, "aenchants.admin")) return false;

        if(args.length < 2) {

            AOutput.error(player, "Usage: /cegive <refname> <level>");
            return false;
        }

        String enchantName = args[0];
        int level = Integer.parseInt(args[1]);

        for(CustomEnchant enchant : CustomEnchantManager.enchants) {

            if(!enchant.getReferenceName().equals(enchantName)) continue;

            player.getInventory().addItem(CustomEnchantItem.createCustomEnchantItem(enchant, level));
            return false;
        }

        return false;
    }
}
