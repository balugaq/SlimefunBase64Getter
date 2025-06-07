package com.balugaq.sbg.implementation;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunItemRegistryFinalizedEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SlimefunBase64Getter extends JavaPlugin implements Listener {
    @Getter
    private static SlimefunBase64Getter instance;
    @Getter
    private final @NotNull String username;
    @Getter
    private final @NotNull String repo;
    @Getter
    private final @NotNull String branch;
    private ConfigManager configManager;

    public SlimefunBase64Getter() {
        this.username = "balugaq";
        this.repo = "SlimefunBase64Getter";
        this.branch = "master";
    }

    public static ConfigManager getConfigManager() {
        return getInstance().configManager;
    }

    /**
     * Initializes the plugin and sets up all necessary components.
     */
    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("正在加载配置文件...");
        saveDefaultConfig();
        this.configManager = new ConfigManager(this);

        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("成功启用 " + getName());
    }

    @Override
    public void onDisable() {
        this.configManager = null;

        getLogger().info("成功禁用 " + getName());

        // Clear instance
        instance = null;
    }

    /**
     * Returns the bug tracker URL for the plugin.
     *
     * @return the bug tracker URL
     */
    @NotNull
    public String getBugTrackerURL() {
        return MessageFormat.format("https://github.com/{0}/{1}/issues/", this.username, this.repo);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRegister(SlimefunItemRegistryFinalizedEvent event) {
        try {
            List<String> textures = new ArrayList<>();
            List<SlimefunItem> items = Slimefun.getRegistry().getAllSlimefunItems();
            for (SlimefunItem item : items) {
                if (item.getItem() instanceof SlimefunItemStack sfis) {
                    sfis.getSkullTexture().ifPresent(textures::add);
                }
            }

            getConfig().set("eys", textures);
            getConfig().save(new File(getDataFolder(), "config.yml"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
