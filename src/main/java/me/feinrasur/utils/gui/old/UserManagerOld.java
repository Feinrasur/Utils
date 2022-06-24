package me.feinrasur.utils.gui.old;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class UserManagerOld {

    private final HashMap<Player, GuiOld> currentGui = new HashMap<>();

    public UserManagerOld() {

    }

    public void setCurrentGui(Player player, GuiOld gui) {
        if (!currentGui.containsKey(player)) {
            currentGui.put(player, gui);
            return;
        }
        currentGui.replace(player, gui);
    }

    public GuiOld getCurrentGui(Player player) {
        if (currentGui.containsKey(player)) {
            return currentGui.get(player);
        }
        return null;
    }

}
