package dev.kyro.arcticenchants.utilities;

import dev.kyro.arcticenchants.ArcticEnchants;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AOutput {

    public static String prefix = ArcticEnchants.INSTANCE.getConfig().getString("prefix");
    public static String errorPrefix = ArcticEnchants.INSTANCE.getConfig().getString("error-prefix");

    public static void send(Player player, String message) {

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public static void error(Player player, String message) {

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', errorPrefix + message));
    }

    public static void color(Player player, String message) {

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void raw(Player player, String message) {

        player.sendMessage(message);
    }

    public static void playSound(Player player, String sound, float vol, float pitch) {

        playSound(player, player.getLocation(), sound, vol, pitch);
    }

    public static void playSound(Player player, Location loc, String sound, float vol, float pitch) {

        player.playSound(loc, sound, vol, pitch);
    }
}
