package dev.kyro.arcticenchants.commands;

import dev.kyro.arcticenchants.utilities.AUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class SoundCommand implements CommandExecutor {

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player) || args.length < 1) return false;

        Player player = (Player) sender;

        if(AUtils.missingPermission(player, "aenchants.admin")) return false;

        float pitch = 1;
        if(args.length > 1) pitch = Float.parseFloat(args[1]);

        player.playSound(player.getLocation(), args[0], 1000, pitch);

        return false;
    }
}
