package me.feinrasur.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public abstract class BetterGui
{

    Inventory inventory;
    HashMap<Integer, ClickEvent> events = new HashMap<>();

    /**
     * size is 1 indexed
     * @param size Sets the size of the GUI inventory
     * @param name Sets the name of the GUI inventory
     */
    @SuppressWarnings("deprecation")
    public BetterGui(Integer size, String name)
    {
        inventory = Bukkit.createInventory(null, size, name);
    }

    /**
     * @param player Player to open GUI for
     */
    public void open(Player player)
    {
        player.openInventory(inventory);
    }

    /**
     * Slot is normally indexed
     * @param itemStack ItemStack to add in to the inventory
     * @param slot Slot where ItemStack should be
     * @param clickEvent ClickEvent for the ItemStack
     */
    public void setItem(ItemStack itemStack, Integer slot, ClickEvent clickEvent)
    {
        inventory.setItem(slot, itemStack);
        events.put(slot, clickEvent);
    }


    /**
     * @return GUI Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @param item Adds ItemStacks in the GUI inventory without ClickEvent
     */
    public void add(ItemStack... item)
    {
        inventory.addItem(item);
    }

    /**
     * @param itemList Adds List of ItemStacks in the GUI inventory without ClickEvent
     */
    public void add(List<ItemStack> itemList)
    {
        for (ItemStack i: itemList) {
            inventory.addItem(i);
        }
    }


    /**
     * Create the ClickEvent
     */
    public interface ClickEvent
    {
        void run(InventoryClickEvent event);
    }

    /**
     * @param slot GUI Inventory slot
     * @return ClickEvent assigned to slot
     */
    public ClickEvent getAction(Integer slot)
    {
        return events.get(slot);
    }

    public void createClickEvents(Integer slot, ClickEvent event) {
        events.put(slot, event);
    }


}
