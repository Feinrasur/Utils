package me.feinrasur.utils.gui.old;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ClickEventOld
{
    GuiOld gui;
    Integer slot;
    ItemStack shownItem;

    public ClickEventOld(GuiOld gui, Integer slot, ItemStack shownItem)
    {
        this.gui = gui;
        this.slot = slot;
        this.shownItem = shownItem;


        GuiOld.ClickEvent clickEvent = new GuiOld.ClickEvent()
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
