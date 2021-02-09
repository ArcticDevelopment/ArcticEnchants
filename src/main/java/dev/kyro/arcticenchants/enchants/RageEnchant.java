package dev.kyro.arcticenchants.enchants;

import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.controllers.CustomEnchantManager;
import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class RageEnchant extends CustomEnchant {

    public HashMap<UUID, Map.Entry<UUID, Double>> rage = new HashMap<>();

    public RageEnchant(String description) {
        super(description);
    }

    @EventHandler(ignoreCancelled = false)
    public void onAttacked(EntityDamageByEntityEvent event) {

        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

        rage.remove(target.getUniqueId());

        if(!CustomEnchantManager.getActiveEnchants(damager).containsKey(this)) return;

        if(!rage.containsKey(damager.getUniqueId()) || rage.get(damager.getUniqueId()).getKey() != target.getUniqueId()) {

            rage.put(damager.getUniqueId(), new AbstractMap.SimpleEntry(target.getUniqueId(), 0D));
            return;
        }

        int enchantLvL = CustomEnchantManager.getActiveEnchants(damager).get(this);

        if(rage.get(damager.getUniqueId()).getValue() < 4)
                rage.get(damager.getUniqueId()).setValue(rage.get(damager.getUniqueId()).getValue() + 1D);
        event.setDamage(event.getDamage() * (1 + (enchantLvL * 0.05) * rage.get(damager.getUniqueId()).getValue()));
        CustomEnchant.playSound(damager, "random.orb", 1000, (float) (0.6 + 0.1 * rage.get(damager.getUniqueId()).getValue()));
    }

    @Override
    public void enableEnchant(int level, Player player, Object... args) { }

    @Override
    public void disableEnchant(Player player, Object... args) { }

    @Override
    public String getName() {

        return ChatColor.AQUA + "Rage";
    }

    @Override
    public String getReferenceName() {

        return "rage";
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

        return EnchantApplyType.SWORD;
    }

    @Override
    public int getMaxLevel() {

        return 3;
    }

    @Override
    public PotionEffectType getPotionEffect() {

        return null;
    }
}
