package io.github.feinrasur.utils.gui.event;

import io.github.feinrasur.utils.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GuiOpenEvent extends Event implements Cancellable {

    private Gui gui;
    private boolean cancelled = false;
    private Player player;

    private static final HandlerList handlers = new HandlerList();

    public GuiOpenEvent(Gui gui, Player player) {
        setGui(gui);
        setPlayer(player);
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
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

    public Player getPlayer() {
        return player;
    }

    private void setGui(Gui gui) {
        this.gui = gui;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }
}
