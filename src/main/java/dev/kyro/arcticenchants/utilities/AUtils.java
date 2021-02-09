package dev.kyro.arcticenchants.utilities;

import dev.kyro.arcticenchants.ArcticEnchants;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AUtils {

    public static String addColor(String message) {

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static ArrayList<String> addColor(ArrayList<String> messages) {

        ArrayList<String> coloredMessages = new ArrayList<>();

        for(String message : messages) {

            coloredMessages.add(ChatColor.translateAlternateColorCodes('&', message));
        }

        return coloredMessages;
    }

    public static boolean missingPermission(Player player, String permission) {

        boolean missingPermission = !player.hasPermission(permission);

        if(missingPermission) AOutput.error(player, ArcticEnchants.INSTANCE.getConfig().getString("messages.no-permission"));

        return missingPermission;
    }
}
