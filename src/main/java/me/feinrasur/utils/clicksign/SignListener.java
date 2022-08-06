package me.feinrasur.utils.clicksign;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        Block block = event.getClickedBlock();
        if (block == null) return;
        if (!block.getType().name().contains("_SIGN")) return;

        Sign sign = (Sign) block.getState();
    }
}
