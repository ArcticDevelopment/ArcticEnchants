package dev.kyro.arcticenchants.controllers;

import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import dev.kyro.arcticenchants.utilities.AUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public abstract class CustomEnchant implements Listener {

    public static ArrayList<UUID> soundPlayers = new ArrayList<>();
    public String description;

    public abstract void enableEnchant(int level, Player player, Object... args);
    public abstract void disableEnchant(Player player, Object... args);

    public abstract String getName();
    public abstract String getReferenceName();
    public abstract ArrayList<String> getItemLore(int level);
    public abstract EnchantType getEnchantType();
    public abstract EnchantApplyType getEnchantApplyType();
    public abstract int getMaxLevel();
    public abstract PotionEffectType getPotionEffect();

    public CustomEnchant(String description) {

        this.description = description;
    }

    public ArrayList<String> createDefaultLore(CustomEnchant customEnchant) {

        ArrayList<String> lore = new ArrayList<>();

        lore.add("&8&m---------------------------------------");
        lore.add("&7 " + description);
        lore.add("");
        lore.add("&b&l Applies To:");
        lore.add("&f -&7 " + EnchantApplyTypeToString(customEnchant.getEnchantApplyType()));
        lore.add("");
        lore.add("&7 Obtained from &b/enchants");
        lore.add("&8&m---------------------------------------");

        lore = AUtils.addColor(lore);

        return lore;
    }

    public static void playSound(Player player, String sound, float vol, float pitch) {

        if(!soundPlayers.contains(player.getUniqueId())) return;

        player.playSound(player.getLocation(), sound, vol, pitch);
    }

    public static void applyPotionEffect(Player player, CustomEnchant customEnchant, int level) {

        Collection<PotionEffect> potionEffects = player.getActivePotionEffects();

        for(PotionEffect potionEffect : potionEffects) {

            if(potionEffect.getType() != customEnchant.getPotionEffect()) continue;

            if(potionEffect.getAmplifier() < level - 1) {

                player.removePotionEffect(customEnchant.getPotionEffect());
            }
        }

        PotionEffect potionEffect = new PotionEffect(customEnchant.getPotionEffect(),
                1000000, level - 1, true, false);

        player.addPotionEffect(potionEffect);

    }

    public static void removePotionEffect(Player player, CustomEnchant customEnchant) {

        Collection<PotionEffect> potionEffects = player.getActivePotionEffects();

        for(PotionEffect potionEffect : potionEffects) {

            if(!potionEffect.getType().equals(customEnchant.getPotionEffect())) continue;

            if(potionEffect.getDuration() > 10000) {

                player.removePotionEffect(customEnchant.getPotionEffect());
            }
        }
    }

    public static EnchantType getEnchantType(EnchantApplyType enchantApplyType) {

        switch(enchantApplyType) {

            case SWORD:
            case AXE:
            case BOW:
                return EnchantType.WEAPON;
            case HELMET:
            case CHESTPLATE:
            case LEGGINGS:
            case BOOTS:
                return EnchantType.ARMOR;
            case PICKAXE:
                return EnchantType.TOOL;
        }

        return null;
    }

    public static String EnchantApplyTypeToString(EnchantApplyType enchantApplyType) {

        switch(enchantApplyType) {

            case SWORD:
                return "Swords";
            case AXE:
                return "Axes";
            case HELMET:
                return "Helmets";
            case CHESTPLATE:
                return "Chestplates";
            case LEGGINGS:
                return "Leggings";
            case BOOTS:
                return "Boots";
            case PICKAXE:
                return "Pickaxes";
            case BOW:
                return "Bows";
        }

        return null;
    }
}
