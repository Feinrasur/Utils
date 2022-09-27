package io.github.feinrasur.utils.gui;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static final Map<Player, Gui> guiMap = new HashMap<>();

    protected UserManager() {

    }

    public void setCurrentGui(Player player, Gui gui) {
        guiMap.put(player, gui);
    }

    public Gui getCurrentGui(Player player) {
        if (guiMap.containsKey(player)) {
            return guiMap.get(player);
        }
        return null;
    }
}
