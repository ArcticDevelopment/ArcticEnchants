package dev.kyro.arcticenchants.commands;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.utilities.AOutput;
import dev.kyro.arcticenchants.utilities.AUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class SoundToggleCommand implements CommandExecutor {

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {

            System.out.println(ArcticEnchants.INSTANCE.getConfig().get("messages.player-only"));
            return false;
        }

        Player player = (Player) sender;

        if(AUtils.missingPermission(player, "aenchants.sounds")) return false;

        if(CustomEnchant.soundPlayers.contains(player.getUniqueId())) {

            CustomEnchant.soundPlayers.remove(player.getUniqueId());
            AOutput.send(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.sounds-off"));
        } else {

            CustomEnchant.soundPlayers.add(player.getUniqueId());
            AOutput.send(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.sounds-on"));
        }

        return false;
    }
}
