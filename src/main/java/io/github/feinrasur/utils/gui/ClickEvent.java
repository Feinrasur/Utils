package io.github.feinrasur.utils.gui;

import io.github.feinrasur.utils.gui.event.GuiClickEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("all")
public abstract class ClickEvent {

    Gui gui;
    Integer slot;
    ItemStack shownItem;
    ClickType type;

    public ClickEvent(Gui gui, Integer slot, ItemStack shownItem, ClickType type) {
        this.gui = gui;
        this.slot = slot;
        this.shownItem = shownItem;

        io.github.feinrasur.utils.gui.interfaces.ClickEvent clickEvent = new io.github.feinrasur.utils.gui.interfaces.ClickEvent() {
            @Override
            public void run(GuiClickEvent event) {

            }
        };

        gui.setItem(slot, shownItem, type, clickEvent);
    }

    public abstract void setClickEvent(InventoryClickEvent event);
}
