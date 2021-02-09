package dev.kyro.arcticenchants.enchants;

import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.controllers.CustomEnchantManager;
import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class JellyLegsEnchant extends CustomEnchant {

    public JellyLegsEnchant(String description) {
        super(description);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onFall(EntityDamageEvent event) {

        if(!(event.getEntity() instanceof Player) || event.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        Player player = (Player) event.getEntity();

        if(!CustomEnchantManager.getActiveEnchants(player).containsKey(this)) return;

        event.setCancelled(true);
//        AOutput.send(player, getName() + "&f cancelled your fall damage (" + (int) event.getFinalDamage() + ")");
    }

    @Override
    public void enableEnchant(int level, Player player, Object... args) { }

    @Override
    public void disableEnchant(Player player, Object... args) { }

    @Override
    public String getName() {

        return ChatColor.AQUA + "Jelly Legs";
    }

    @Override
    public String getReferenceName() {

        return "jellylegs";
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

        return 1;
    }

    @Override
    public PotionEffectType getPotionEffect() {

        return null;
    }
}
