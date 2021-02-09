package dev.kyro.arcticenchants.vanilla;

import dev.kyro.arcticenchants.ArcticEnchants;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Set;

public class VanillaEnchantManager {

    public static ArrayList<VanillaEnchant> vanillaEnchants = new ArrayList<>();

    public static void registerEnchants() {

        Set<String> keys = ArcticEnchants.INSTANCE.getConfig().getConfigurationSection("vanilla-enchants").getKeys(false);

        for(String key : keys) {

            Enchantment enchantment = Enchantment.getByName(ArcticEnchants.INSTANCE.getConfig().getString("vanilla-enchants." + key + ".enchant"));

            if(enchantment == null) {
                System.out.println("malformed config");
                continue;
            }

            int data = ArcticEnchants.INSTANCE.getConfig().getInt("vanilla-enchants." + key + ".data");
            ItemStack item = new ItemStack(Material.getMaterial(ArcticEnchants.INSTANCE.getConfig().getString("vanilla-enchants." + key + ".item"))
                    , 1, (byte) data);

            int cost = ArcticEnchants.INSTANCE.getConfig().getInt("vanilla-enchants." + key + ".cost");
            int maxLevel = ArcticEnchants.INSTANCE.getConfig().getInt("vanilla-enchants." + key + ".max-level");

            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ArcticEnchants.INSTANCE.getConfig().getString("vanilla-enchants." + key + ".name"));
            item.setItemMeta(itemMeta);

            VanillaEnchant vanillaEnchant = new VanillaEnchant(enchantment, item, cost, maxLevel);
            vanillaEnchants.add(vanillaEnchant);
        }
    }
}