package me.feinrasur.utils.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ClickEvent {

    Gui gui;
    Integer slot;
    ItemStack shownItem;
    ClickEventType clickEventType;

    public ClickEvent(Gui gui, Integer slot, ItemStack shownItem, ClickEventType clickEventType) {
        this.gui = gui;
        this.slot = slot;
        this.shownItem = shownItem;


        me.feinrasur.utils.gui.interfaces.ClickEvent clickEvent = new me.feinrasur.utils.gui.interfaces.ClickEvent() {
            @Override
            public void run(InventoryClickEvent event) {
                setClickEvent(event);
            }
        };

        gui.setItem(slot, shownItem, clickEvent, clickEventType);
    }

    public abstract void setClickEvent(InventoryClickEvent event);
}
