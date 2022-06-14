package me.feinrasur.utils.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class BetterClickEvent
{
    BetterGui gui;
    Integer slot;
    ItemStack shownItem;

    public BetterClickEvent(BetterGui gui, Integer slot, ItemStack shownItem)
    {
        this.gui = gui;
        this.slot = slot;
        this.shownItem = shownItem;


        BetterGui.ClickEvent clickEvent = new BetterGui.ClickEvent()
        {
            @Override
            public void run(InventoryClickEvent event)
            {
                setClickEvent(event);
            }
        };

        gui.setItem(shownItem, slot, clickEvent);
    }

    public abstract void setClickEvent(InventoryClickEvent event);

}
