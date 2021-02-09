package dev.kyro.arcticenchants.enchants;

import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class SpeedEnchant extends CustomEnchant {

    public SpeedEnchant(String description) {
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

        return ChatColor.AQUA + "Speed";
    }

    @Override
    public String getReferenceName() {

        return "speed";
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

        return EnchantApplyType.BOOTS;
    }

    @Override
    public int getMaxLevel() {

        return 2;
    }

    @Override
    public PotionEffectType getPotionEffect() {

        return PotionEffectType.SPEED;
    }
}
