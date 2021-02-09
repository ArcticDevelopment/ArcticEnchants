package dev.kyro.arcticenchants.controllers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PvPManager implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void protectionFix(EntityDamageByEntityEvent event) {

        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;

        Player damager = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

//        System.out.println("magic damage (initial): " + event.getDamage(EntityDamageEvent.DamageModifier.MAGIC) + " " + event.getDamage());

        double protLvl = 0;
        for(ItemStack item : target.getInventory().getArmorContents()) {

            if(item == null) continue;

            int enchantLvl = item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            protLvl += enchantLvl;
        }

        event.setDamage(EntityDamageEvent.DamageModifier.MAGIC, 0);
        event.setDamage(EntityDamageEvent.DamageModifier.MAGIC, - ((event.getFinalDamage() * protLvl * 0.08) / (protLvl / 20 + 1)));

//        System.out.println("magic damage (final): " + event.getDamage(EntityDamageEvent.DamageModifier.MAGIC));
//        AOutput.send(damager, "final: " + event.getFinalDamage());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAttack(EntityDamageByEntityEvent event) {

        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof LivingEntity)) return;

        Player damager = (Player) event.getDamager();

//        AOutput.send(damager, "base: " + event.getDamage());

//        Strength Fix
        double strengthLvl = -1;
        for(PotionEffect potionEffect : damager.getActivePotionEffects()) {

            if(potionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {

                strengthLvl = potionEffect.getAmplifier();
                double damageReduction = (1 + (strengthLvl + 1) * 0.5) / (1 + (strengthLvl + 1) * 1.3);
//

                event.setDamage(event.getDamage() * damageReduction);
            }
        }

//        Sharpness Fix
        int sharpLvl = damager.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
//        AOutput.send(damager, "strength: " + event.getDamage());
        event.setDamage(event.getDamage() + sharpLvl * 1.25 * (strengthLvl + 1) * 0.5);

//        AOutput.send(damager, "sharp: " + event.getDamage());
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {

        if(event.getItem() == null || event.getPlayer() == null) return;

        ItemStack item = event.getItem();

        if(!item.getEnchantments().containsKey(Enchantment.DURABILITY) || event.getDamage() == 0) return;

        String material = item.getType().toString();
        double enchantLvl = item.getEnchantmentLevel(Enchantment.DURABILITY);

        if(!material.contains("HELMET") && !material.contains("CHESTPLATE") && !material.contains("LEGGINGS") && !material.contains("BOOTS")) return;

        int count = 0;
        for(int i = 0; i < event.getDamage(); i++) {

            if(Math.random() > 1 / (enchantLvl)) {

                count++;
            }
        }

        event.setDamage(event.getDamage() - count);
    }
}
