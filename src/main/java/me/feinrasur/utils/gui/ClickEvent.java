package me.feinrasur.utils.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ClickEvent
{
    Gui gui;
    Integer slot;
    ItemStack shownItem;

    public ClickEvent(Gui gui, Integer slot, ItemStack shownItem)
    {
        this.gui = gui;
        this.slot = slot;
        this.shownItem = shownItem;


        Gui.ClickEvent clickEvent = new Gui.ClickEvent()
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
