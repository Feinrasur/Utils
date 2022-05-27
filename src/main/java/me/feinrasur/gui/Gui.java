package me.feinrasur.gui;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Gui {

    public static List<ClickEvent> eventList = new ArrayList<>();

    public Inventory inventory;
    public JavaPlugin instance;

    public Gui(Integer size, String name, JavaPlugin plugin){
        if (name == null) {
            name = "§e§lGUI";
        }
        inventory = Bukkit.createInventory(null, size, name);
        instance = plugin;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }


}
