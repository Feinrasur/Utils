package io.github.feinrasur.utils.fun.gun.implement;

import io.github.feinrasur.utils.fun.gun.Gun;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class GunShootEvent extends PlayerEvent implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    private boolean cancelled = false;
    private Gun gun;

    public GunShootEvent(@NotNull Player who, Gun gun) {
        super(who);
        this.gun = gun;
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
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Gun getGun() {
        return gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }
}
