package dev.kyro.arcticenchants.enchants;

import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class WaterBreathingEnchant extends CustomEnchant {

    public WaterBreathingEnchant(String description) {
        super(description);
    }

    @Override
    public void enableEnchant(int level, Player player, Object... args) {

        CustomEnchant.applyPotionEffect(player, this, level);
    }

    @Override
    public void disableEnchant(Player player, Object... args) {

        CustomEnchant.removePotionEffect(player, this);
    }

    @Override
    public String getName() {

        return ChatColor.AQUA + "Water Breathing";
    }

    @Override
    public String getReferenceName() {

        return "waterbreathing";
    }

    @Override
    public ArrayList<String> getItemLore(int level) {

        return createDefaultLore(this);
    }

    @Override
    public EnchantType getEnchantType() {

        return CustomEnchant.getEnchantType(getEnchantApplyType());
    }

    @Override
    public EnchantApplyType getEnchantApplyType() {

        return EnchantApplyType.HELMET;
    }

    @Override
    public int getMaxLevel() {

        return 1;
    }

    @Override
    public PotionEffectType getPotionEffect() {

        return PotionEffectType.WATER_BREATHING;
    }
}
