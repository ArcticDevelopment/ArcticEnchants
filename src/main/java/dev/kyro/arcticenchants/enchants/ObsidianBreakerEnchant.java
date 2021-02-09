package dev.kyro.arcticenchants.enchants;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import dev.kyro.arcticenchants.controllers.CustomEnchant;
import dev.kyro.arcticenchants.controllers.CustomEnchantManager;
import dev.kyro.arcticenchants.enums.EnchantApplyType;
import dev.kyro.arcticenchants.enums.EnchantType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class ObsidianBreakerEnchant extends CustomEnchant {

    public ObsidianBreakerEnchant(String description) {
        super(description);
    }

    @EventHandler
    public void onObsidianMine(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if(event.getAction() != Action.LEFT_CLICK_BLOCK || event.getClickedBlock() == null
                || !CustomEnchantManager.getActiveEnchants(player).containsKey(this)) return;

        Block block = event.getClickedBlock();

        if(block.getType() != Material.OBSIDIAN) return;

        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);

        if(!fPlayer.isInOwnTerritory()) return;

        block.setType(Material.AIR);
    }

    @Override
    public void enableEnchant(int level, Player player, Object... args) { }

    @Override
    public void disableEnchant(Player player, Object... args) { }

    @Override
    public String getName() {

        return ChatColor.AQUA + "Obsidian Breaker";
    }

    @Override
    public String getReferenceName() {

        return "obsidianbreaker";
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

        return EnchantApplyType.PICKAXE;
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
