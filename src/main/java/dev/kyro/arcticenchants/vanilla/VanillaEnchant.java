package dev.kyro.arcticenchants.vanilla;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class VanillaEnchant {

    public Enchantment enchantment;
    public ItemStack displayItem;
    public int cost;
    public int maxLvl;

    public VanillaEnchant(Enchantment enchantment, ItemStack displayItem, int cost, int maxLvl) {

        this.enchantment = enchantment;
        this.displayItem = displayItem;
        this.cost = cost;
        this.maxLvl = maxLvl;
    }
}
