package dev.kyro.arcticenchants;

import com.massivecraft.factions.FactionsPlugin;
import dev.kyro.arcticenchants.commands.*;
import dev.kyro.arcticenchants.controllers.*;
import dev.kyro.arcticenchants.enchants.ObsidianBreakerEnchant;
import dev.kyro.arcticenchants.vanilla.EnchantingTableListener;
import dev.kyro.arcticenchants.vanilla.VanillaEnchantManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.Set;

public class ArcticEnchants extends JavaPlugin {

    public static ArcticEnchants INSTANCE;
    public static FactionsPlugin factions;

    @Override
    public void onEnable() {

        INSTANCE = this;

        loadConfig();

        VanillaEnchantManager.registerEnchants();

        try {
            String description = ArcticEnchants.INSTANCE.getConfig().getString("custom-enchants.obsidian-breaker.description");
            boolean enabled = ArcticEnchants.INSTANCE.getConfig().getBoolean("custom-enchants.obsidian-breaker.enabled");

            if(!enabled) return;

            factions = (FactionsPlugin) Bukkit.getPluginManager().getPlugin("Factions");

            CustomEnchantManager.registerEnchant(new ObsidianBreakerEnchant(description));
            System.out.println("Registered the custom enchant: dev.kyro.arcticenchants.enchants.ObsidianBreakerEnchant");

        } catch (Exception exception) {

            System.out.println("Failed to register the custom enchant: dev.kyro.arcticenchants.enchants.ObsidianBreakerEnchant");
            exception.printStackTrace();
        }

        registerCommands();
        registerListeners();
        registerEnchants();
    }

    @Override
    public void onDisable() {


    }

    private void loadConfig() {

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void registerCommands() {

        getCommand("enchanter").setExecutor(new EnchanterCommand());
        getCommand("soundtoggle").setExecutor(new SoundToggleCommand());

        getCommand("enchantgui").setExecutor(new VanillaCommand());
        getCommand("cegive").setExecutor(new CustomEnchantGiveCommand());
        getCommand("sound").setExecutor(new SoundCommand());
    }

    private void registerListeners() {

        getServer().getPluginManager().registerEvents(new InventoryManager(), this);
        getServer().getPluginManager().registerEvents(new EffectManager(), this);
        getServer().getPluginManager().registerEvents(new CustomEnchantItem(), this);
        getServer().getPluginManager().registerEvents(new PvPManager(), this);
        getServer().getPluginManager().registerEvents(new EnchantingTableListener(), this);
    }

    @SuppressWarnings("all")
    private void registerEnchants() {

        Set<String> keys = ArcticEnchants.INSTANCE.getConfig().getConfigurationSection("custom-enchants").getKeys(false);

        for(String key : keys) {

            String className = ArcticEnchants.INSTANCE.getConfig().getString("custom-enchants." + key + ".name");
            String description = ArcticEnchants.INSTANCE.getConfig().getString("custom-enchants." + key + ".description");
            boolean enabled = ArcticEnchants.INSTANCE.getConfig().getBoolean("custom-enchants." + key + ".enabled");
            if(!enabled || className.equals("ObsidianBreakerEnchant")) continue;

            try {
                className = "dev.kyro.arcticenchants.enchants." + className;
                Class customEnchantClass = Class.forName(className);
                Class[] types = { String.class };
                Constructor constructor = customEnchantClass.getConstructor(types);
                Object[] parameters = { description };
                Object customEnchant = constructor.newInstance(parameters);

                CustomEnchantManager.registerEnchant((CustomEnchant) customEnchant);
                System.out.println("Registered the custom enchant: " + className);
            } catch(Exception exception) {

                System.out.println("Failed to register the custom enchant: " + className);
                exception.printStackTrace();
            }
        }
    }
}
