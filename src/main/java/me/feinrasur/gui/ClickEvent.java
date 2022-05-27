package me.feinrasur.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class ClickEvent implements Listener {

    Gui gui;
    Integer slot;
    ItemStack showItem;

    public ClickEvent(Gui gui, Integer slot, ItemStack showItem) {
        gui.inventory.setItem(slot, showItem);
        this.gui = gui;
        this.slot = slot;
        this.showItem = showItem;
        gui.instance.getServer().getPluginManager().registerEvents(this, gui.instance);
        Gui.eventList.add(this);
    }


    public abstract void setClickEvent(Player player);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (gui.inventory.equals(inventory)) {
            event.setCancelled(true);
            if (event.isLeftClick()) {
                if (!event.getCurrentItem().equals(showItem)) return;
                if (event.getSlot() == slot) {
                    this.setClickEvent((Player) event.getWhoClicked());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        if (gui.inventory.equals(inventory)) {
            event.setCancelled(true);
        }
    }

    public void setShownItem(Integer slot, ItemStack item) {
        for (ClickEvent event: Gui.eventList) {
            if (event.slot.equals(slot)) {
                gui.inventory.setItem(slot, item);
                this.showItem = item;
            }
        }
    }
}
