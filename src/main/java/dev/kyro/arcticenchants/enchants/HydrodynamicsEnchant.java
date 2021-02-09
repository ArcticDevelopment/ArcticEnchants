package dev.kyro.arcticenchants.enchants;

import dev.kyro.arcticenchants.ArcticEnchants;
import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.controllers.CustomEnchantManager;
import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HydrodynamicsEnchant extends CustomEnchant {

    private final HashMap<Arrow, Integer> arrowTasks = new HashMap<>();

    public HydrodynamicsEnchant(String description) {
        super(description);
    }

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent event) {

        if (!(event.getEntity() instanceof Player) || !(event.getProjectile() instanceof Arrow)) return;

        Player player = (Player) event.getEntity();
        Arrow arrow = (Arrow) event.getProjectile();

        if(!CustomEnchantManager.getActiveEnchants(player).containsKey(this)) return;

        double enchantLvL = CustomEnchantManager.getActiveEnchants(player).get(this);

        int runnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(ArcticEnchants.INSTANCE, () -> {

            Block block = arrow.getLocation().getBlock();

            if(block != null && block.getType() != Material.WATER && block.getType() != Material.STATIONARY_WATER) return;

            Vector arrowVector = arrow.getVelocity();

//            AOutput.send(player, arrowVector.getY() + "     " + arrow.getLocation().getY());

            arrow.setVelocity(arrowVector.multiply(1.3 + enchantLvL * 0.1));
        }, 0L, 1L);

        arrowTasks.put(arrow, runnable);
    }

    @EventHandler
    public void onArrowLand(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow removal = null;

            for (Map.Entry<Arrow, Integer> entry : arrowTasks.entrySet()) {
                if (entry.getKey() == event.getEntity()) {
                    Arrow arrow = (Arrow) event.getEntity();

                    System.out.println("removal");

                    removal = arrow;
                    Bukkit.getServer().getScheduler().cancelTask(arrowTasks.get(arrow));
                }
            }

            if (removal != null)
                arrowTasks.remove(removal);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            Arrow removal = null;

            for (Map.Entry<Arrow, Integer> entry : arrowTasks.entrySet()) {
                if (entry.getKey() == event.getDamager()) {
                    Arrow arrow = (Arrow) event.getDamager();

                    removal = arrow;
                    Bukkit.getServer().getScheduler().cancelTask(arrowTasks.get(arrow));
                }
            }

            if (removal != null)
                arrowTasks.remove(removal);
        }
    }

    @Override
    public void enableEnchant(int level, Player player, Object... args) { }

    @Override
    public void disableEnchant(Player player, Object... args) { }

    @Override
    public String getName() {

        return ChatColor.AQUA + "Hydrodynamics";
    }

    @Override
    public String getReferenceName() {

        return "hydrodynamics";
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
