package com.balugaq.changeme.implementation;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"unused"})
public class Debug {
    private static final String debugPrefix = "[Debug] ";
    private static JavaPlugin plugin;

    public static void debug(Object @NotNull ... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objects) {
            if (obj == null) {
                sb.append("null");
            } else {
                sb.append(obj);
            }
        }
        debug(sb.toString());
    }

    public static void debug(@NotNull Throwable e) {
        debug(e.getMessage());
        trace(e);
    }

    public static void debug(@NotNull Object object) {
        debug(object.toString());
    }

    public static void debug(String @NotNull ... messages) {
        for (String message : messages) {
            debug(message);
        }
    }

    public static void debug(String message) {
        if (Main.getConfigManager().isDebug()) {
            log(debugPrefix + message);
        }
    }

    public static void sendMessage(@NotNull Player player, Object @NotNull ... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objects) {
            if (obj == null) {
                sb.append("null");
            } else {
                sb.append(obj);
            }
        }
        sendMessage(player, sb.toString());
    }

    public static void sendMessage(@NotNull Player player, @Nullable Object object) {
        if (object == null) {
            sendMessage(player, "null");
            return;
        }
        sendMessage(player, object.toString());
    }

    public static void sendMessages(@NotNull Player player, String @NotNull ... messages) {
        for (String message : messages) {
            sendMessage(player, message);
        }
    }

    public static void sendMessage(@NotNull Player player, String message) {
        init();
        player.sendMessage("[" + plugin.getLogger().getName() + "]" + message);
    }

    public static void stackTraceManually() {
        try {
            throw new Error();
        } catch (Throwable e) {
            trace(e);
        }
    }

    public static void log(Object @NotNull ... object) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : object) {
            if (obj == null) {
                sb.append("null");
            } else {
                sb.append(obj);
            }
        }

        log(sb.toString());
    }

    public static void log(@NotNull Object object) {
        log(object.toString());
    }

    public static void log(String @NotNull ... messages) {
        for (String message : messages) {
            log(message);
        }
    }

    public static void log(@NotNull String message) {
        init();
        plugin.getServer().getConsoleSender().sendMessage("[" + Main.getInstance().getName() + "] " + ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void log(@NotNull Throwable e) {
        Debug.trace(e);
    }

    public static void log() {
        log("");
    }

    public static void init() {
        if (plugin == null) {
            plugin = Main.getInstance();
        }
    }

    public static void trace(@NotNull Throwable e) {
        trace(e, null);
    }

    public static void trace(@NotNull Throwable e, @Nullable String doing) {
        trace(e, doing, null);
    }

    public static void trace(@NotNull Throwable e, @Nullable String doing, @Nullable Integer code) {
        init();
        plugin.getLogger().severe("DO NOT REPORT THIS ERROR TO " + Main.getInstance().getName() + " DEVELOPERS!!! THIS IS NOT A " + Main.getInstance() + " BUG!");
        if (code != null) {
            plugin.getLogger().severe("Error code: " + code);
        }
        plugin.getLogger().severe("If you are sure that this is a " + Main.getInstance().getName() + " bug, please report to " + Main.getInstance().getBugTrackerURL());
        if (doing != null) {
            plugin.getLogger().severe("An unexpected error occurred while " + doing);
        } else {
            plugin.getLogger().severe("An unexpected error occurred.");
        }

        e.printStackTrace();
    }
}
