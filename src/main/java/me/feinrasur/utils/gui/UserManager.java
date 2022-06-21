package me.feinrasur.utils.gui;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class UserManager {

    private static UserManager manager = null;
    Map<@NotNull Player, @Nullable Gui> guiMap = new HashMap<>();

    private UserManager() {

    }

    public static UserManager init() {
        manager = new UserManager();
        return manager;
    }

    public static UserManager getManager() throws RuntimeException{
        if (manager == null)
            throw  new RuntimeException("Failed to init UserManager. Are you sure you used UserManager.init() in your main file?");
        return manager;
    }

    public void setCurrentGui(@NotNull Player player, @Nullable Gui gui) {
        guiMap.put(player, gui);
    }

    public @Nullable Gui getCurrentGui(Player player) {
        if (guiMap.containsKey(player))
            return guiMap.get(player);
        return null;
    }
}
