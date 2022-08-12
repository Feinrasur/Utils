package io.github.feinrasur.utils.gui.events;

import io.github.feinrasur.utils.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class GuiCloseEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private static final HandlerList handlers = new HandlerList();
    private Gui gui;
    private Player player;

    public GuiCloseEvent(Gui gui, Player player) {
        setGui(gui);
        setPlayer(player);
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
}
