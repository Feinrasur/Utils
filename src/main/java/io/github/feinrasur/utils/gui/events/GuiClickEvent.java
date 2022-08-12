package io.github.feinrasur.utils.gui.events;

import io.github.feinrasur.utils.gui.ClickEventType;
import io.github.feinrasur.utils.gui.Gui;
import io.github.feinrasur.utils.gui.interfaces.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class GuiClickEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private static final HandlerList handlers = new HandlerList();
    private Gui gui;
    private Player player;
    private Integer clickedSlot;
    private ItemStack clickedItem;
    private ClickEvent clickEvent;
    private ClickEventType clickEventType;

    public GuiClickEvent(Gui gui, Player player, Integer slot, ItemStack clickedItem, ClickEvent clickEvent, ClickEventType clickEventType) {
        setGui(gui);
        setPlayer(player);
        setClickedSlot(slot);
        setClickedItem(clickedItem);
        setClickEvent(clickEvent);
        setClickEventType(clickEventType);
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

    public ClickEventType getClickEventType() {
        return clickEventType;
    }

    private void setClickEventType(ClickEventType clickEventType) {
        this.clickEventType = clickEventType;
    }

    public void runClickEvent() {
        clickEvent.run(this);
    }
}
