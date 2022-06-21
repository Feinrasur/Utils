package me.feinrasur.utils.gui.old;

import me.feinrasur.utils.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public abstract class GuiOld
{

    Inventory inventory;
    HashMap<Integer, ClickEvent> events = new HashMap<>();

    /**
     * size is 1 indexed
     * @param size Sets the size of the GUI inventory
     * @param name Sets the name of the GUI inventory
     */
    @SuppressWarnings("deprecation")
    public GuiOld(Integer size, String name)
    {
        inventory = Bukkit.createInventory(null, size, name);
    }

    /**
     * @param player Player to open GUI for
     */
    public void open(Player player)
    {
        player.openInventory(inventory);
        GUIListenerOld.manager.setCurrentGui(player, this);
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
     * @param item item ItemStack to put in the GUI Inventory
     * @return slot the item got put in | Returns -1 if no slot is available
     */
    public Integer add(ItemStack item)
    {
        Integer freeSlot = inventory.firstEmpty();
        if (freeSlot.equals((int) -1)) return null;
        inventory.setItem(freeSlot, item);
        return freeSlot;
    }

    /**
     * @param item ItemStack to put in the GUI Inventory
     * @param clickEvent ClickEvent to set for the ItemStack
     * @return slot the item got put in | Returns -1 if no slot is available
     */
    public Integer add(ItemStack item, ClickEvent clickEvent) {
        Integer freeSlot = inventory.firstEmpty();
        if (freeSlot.equals((int) -1)) return null;
        inventory.setItem(freeSlot, item);
        createClickEvent(freeSlot, clickEvent);
        return freeSlot;
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

    /**
     * @param slot Set the slot for the ClickEvent
     * @param event Set the ClickEvent for the slot
     */
    public void createClickEvent(Integer slot, ClickEvent event) {
        events.put(slot, event);
    }


    public static ItemStack convertGuiItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.setDisplayName(Chat.format(name));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }


}
