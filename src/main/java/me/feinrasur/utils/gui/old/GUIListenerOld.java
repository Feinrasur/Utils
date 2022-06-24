package me.feinrasur.utils.gui.old;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class GUIListenerOld implements Listener {

    static UserManagerOld manager;

    public GUIListenerOld(JavaPlugin plugin, UserManagerOld userManager) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        manager = userManager;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInvClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getCurrentItem() == null) return;
        if (event.getClickedInventory() == null) return;
        Player player = (Player) event.getWhoClicked();
        GuiOld gui = manager.getCurrentGui(player);
        if (gui == null) return;

        Inventory topInv = player.getOpenInventory().getTopInventory();
        Inventory bottomInv = player.getOpenInventory().getBottomInventory();
        Inventory guiInv = gui.getInventory();
        if (guiInv == null) return;
        if (event.getClickedInventory().equals(player.getInventory())) {
            if (topInv != null && topInv.equals(guiInv))
                event.setCancelled(true);
        }
        Inventory inv = event.getClickedInventory();
        if (inv == null) return;
        Inventory tempInv = null;
        try {
            tempInv = manager.getCurrentGui((Player) event.getWhoClicked()).getInventory();
        } catch (Exception e) {
            return;
        }
        if (!inv.equals(tempInv)) return;
        event.setCancelled(true);
        if (event.getClick().equals(ClickType.SWAP_OFFHAND) && event.isCancelled()) {
            player.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());
        }
        if (!event.getClick().equals(ClickType.LEFT) || event.getClick().equals(ClickType.SHIFT_LEFT)) {
            event.setCancelled(true);
            return;
        }
        if (event.isLeftClick()) {
            GuiOld.ClickEvent clickEvent = gui.getAction(event.getSlot());
            if (clickEvent == null) return;
            clickEvent.run(event);
        }

    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent event) {
        if (event.getInventory().equals(manager.getCurrentGui((Player) event.getWhoClicked()).getInventory())) {
            event.setCancelled(true);
        }
    }

}
