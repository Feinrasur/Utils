package io.github.feinrasur.utils.gui.event;

import io.github.feinrasur.utils.gui.ClickType;
import io.github.feinrasur.utils.gui.Gui;
import io.github.feinrasur.utils.gui.interfaces.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GuiClickEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private static final HandlerList handlers = new HandlerList();
    private Gui gui = null;
    private Player player = null;
    private Integer clickedSlot = null;
    private ItemStack clickedItem = null;
    private ClickEvent clickEvent = null;
    private ClickType type = null;

    public GuiClickEvent(Gui gui, Player player, Integer slot, ItemStack clickedItem, ClickEvent clickEvent, ClickType type) {
        setGui(gui);
        setPlayer(player);
        setClickedSlot(slot);
        setClickedItem(clickedItem);
        setClickEvent(clickEvent);
        settype(type);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    public Gui getGui() {
        return gui;
    }

    private void setGui(Gui gui) {
        this.gui = gui;
    }

    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getClickedSlot() {
        return clickedSlot;
    }

    private void setClickedSlot(Integer clickedSlot) {
        this.clickedSlot = clickedSlot;
    }

    public ItemStack getClickedItem() {
        return clickedItem;
    }

    private void setClickedItem(ItemStack clickedItem) {
        this.clickedItem = clickedItem;
    }

    public ClickEvent getClickEvent() {
        return clickEvent;
    }

    public void setClickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    public ClickType gettype() {
        return type;
    }

    private void settype(ClickType type) {
        this.type = type;
    }

    public void runClickEvent() {
        clickEvent.run(this);
    }
}
