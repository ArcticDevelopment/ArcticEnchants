package dev.kyro.arcticenchants.enchants;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.controllers.CustomEnchantManager;
import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class GrappleEnchant extends CustomEnchant {

    public static ArrayList<UUID> playerCooldown = new ArrayList<>();

    public GrappleEnchant(String description) {
        super(description);
    }

    @EventHandler(ignoreCancelled = true)
    public void onHit(EntityDamageByEntityEvent event) {

         if (!(event.getEntity() instanceof LivingEntity) || !(event.getDamager() instanceof Arrow)
                || !(((Arrow) event.getDamager()).getShooter() instanceof Player)) return;

        LivingEntity attacked = (LivingEntity) event.getEntity();
        Arrow arrow = (Arrow) event.getDamager();
        Player damager = (Player) arrow.getShooter();

        if(!CustomEnchantManager.getActiveEnchants(damager).containsKey(this)) return;

        if(playerCooldown.contains(damager.getUniqueId())) return;

        playerCooldown.add(damager.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                playerCooldown.remove(damager.getUniqueId());
            }
        }.runTaskLater(ArcticEnchants.INSTANCE, 80L);

        int enchantLvl = CustomEnchantManager.getActiveEnchants(damager).get(this);

        Vector to = attacked.getLocation().toVector();
        Vector from = damager.getLocation().toVector();

        double distance = attacked.getLocation().distance(damager.getLocation());

        Vector grappleVector = to.subtract(from).setY(0).normalize()
                .add(new Vector(0, 0.15, 0)).multiply(0.5 + Math.sqrt(distance / (3 * (getMaxLevel() - enchantLvl + 1))));
        damager.setVelocity(grappleVector);
    }

    @Override
    public void enableEnchant(int level, Player player, Object... args) { }

    @Override
    public void disableEnchant(Player player, Object... args) { }

    @Override
    public String getName() {

        return ChatColor.AQUA + "Grapple";
    }

    @Override
    public String getReferenceName() {

        return "grapple";
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

        return 3;
    }

    @Override
    public PotionEffectType getPotionEffect() {

        return null;
    }
}
