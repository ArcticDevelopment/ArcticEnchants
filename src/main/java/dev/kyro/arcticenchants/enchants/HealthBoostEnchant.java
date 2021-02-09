package dev.kyro.arcticenchants.enchants;

import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class HealthBoostEnchant extends CustomEnchant {

    public HealthBoostEnchant(String description) {
        super(description);
    }

    @Override
    public void enableEnchant(int level, Player player, Object... args) {

//        CustomEnchant.applyPotionEffect(player, this, level);
        if(player.getMaxHealth() == 20 + (2 * level)) return;
        player.setMaxHealth(20 + (2 * level));
    }

    @Override
    public void disableEnchant(Player player, Object... args) {

//        CustomEnchant.removePotionEffect(player, this);
        if(player.getMaxHealth() == 20) return;
        player.setMaxHealth(20);
    }

    @Override
    public String getName() {

        return ChatColor.AQUA + "Health Boost";
    }

    @Override
    public String getReferenceName() {

        return "healthboost";
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

        return EnchantApplyType.CHESTPLATE;
    }

    @Override
    public int getMaxLevel() {

        return 2;
    }

    @Override
    public PotionEffectType getPotionEffect() {

        return PotionEffectType.HEALTH_BOOST;
    }
}
