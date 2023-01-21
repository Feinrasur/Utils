package io.github.feinrasur.utils.fun.gun.implement;

import io.github.feinrasur.utils.fun.gun.Gun;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class GunHitTargetEvent extends PlayerEvent implements Cancellable {

    public GunHitTargetEvent(@NotNull Player who, Gun gun, Entity target, EntityDamageByEntityEvent event) {
        super(who);
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return new HandlerList();
    }
}
