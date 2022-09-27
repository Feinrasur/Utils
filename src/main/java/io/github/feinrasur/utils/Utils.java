package io.github.feinrasur.utils;

import io.github.feinrasur.utils.chat.Format;
import io.github.feinrasur.utils.gui.GUIListener;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public class Utils {

    protected static JavaPlugin plugin;

    private static String deniedCLickMessage = "&cThis ClickType is not allowed. Please use another ClickType.";

    public static void init(JavaPlugin plugin) {
        Utils.plugin = plugin;
        new GUIListener(plugin);
    }

    public static Component deniedClick() {
        return Component.text(Format.format(deniedCLickMessage));
    }

    public static void setDeniedCLickMessage(String s) {
        deniedCLickMessage = s;
    }
}
