package me.feinrasur.utils.gui;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class UserManager
{

    private HashMap<Player, BetterGui> currentGui = new HashMap<>();

    public UserManager()
    {

    }

    public void setCurrentGui(Player player, BetterGui gui)
    {
        if (!currentGui.containsKey(player))
        {
            currentGui.put(player, gui);
            return;
        }
        currentGui.replace(player, gui);
    }

    public BetterGui getCurrentGui(Player player)
    {
        return currentGui.get(player);
    }

}
