package dev.kyro.arcticenchants.enchants;

import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.controllers.CustomEnchantManager;
import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class QuickDrawEnchant extends CustomEnchant {

    public QuickDrawEnchant(String description) {
        super(description);
    }

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent event) {

        if (!(event.getEntity() instanceof Player) || !(event.getProjectile() instanceof Arrow)) return;

        Player player = (Player) event.getEntity();
        Arrow arrow = (Arrow) event.getProjectile();

        if(!CustomEnchantManager.getActiveEnchants(player).containsKey(this)) return;

        arrow.setCritical(true);
        arrow.setVelocity(player.getLocation().getDirection().multiply(2.90));
    }

    @Override
    public void enableEnchant(int level, Player player, Object... args) { }

    @Override
    public void disableEnchant(Player player, Object... args) { }

    @Override
    public String getName() {

        return ChatColor.AQUA + "Quick Draw";
    }

    @Override
    public String getReferenceName() {

        return "quickdraw";
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

        return EnchantApplyType.BOW;
    }

    @Override
    public int getMaxLevel() {

        return 1;
    }

    @Override
    public PotionEffectType getPotionEffect() {

        return null;
    }
}
