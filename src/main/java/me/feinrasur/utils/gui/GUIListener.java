package me.feinrasur.utils.gui;

import me.feinrasur.utils.chat.Chat;
import me.feinrasur.utils.gui.interfaces.ClickEvent;
import me.feinrasur.utils.gui.interfaces.CloseEvent;
import me.feinrasur.utils.gui.interfaces.OpenEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

public class GUIListener implements Listener {

    static UserManager manager;
    static JavaPlugin plugin;
    boolean lockAllInventories = false;

    public GUIListener(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        manager = new UserManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GUIListener(JavaPlugin javaPlugin, boolean lockAllInventories) {
        plugin = javaPlugin;
        manager = new UserManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.lockAllInventories = lockAllInventories;
    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        Gui gui = manager.getCurrentGui(player);
        if (gui == null) return;
        if (event.getInventory().equals(gui.getInventory())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        Gui gui = manager.getCurrentGui(player);
        if (gui == null) return;
        Inventory guiInv = gui.getInventory();
        if (guiInv == null) return;
        InventoryView view = player.getOpenInventory();
        Inventory topInv = view.getTopInventory();
        Inventory eventInv = event.getClickedInventory();
        if (eventInv == null) return;
        Inventory playerInv = player.getInventory();
        if (eventInv.equals(playerInv) && topInv.equals(guiInv)) {
            if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                event.setCancelled(true);
                return;
            }
            if (lockAllInventories) event.setCancelled(true);
            if (event.isCancelled())
                player.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());
            return;
        }
        if (!eventInv.equals(guiInv)) return;
        Integer slot = event.getSlot();
        if (event.getClickedInventory().equals(guiInv))
            event.setCancelled(true);

        switch (event.getClick()) {
            case LEFT -> {
                if (!gui.isLeftClickable()) {
                    if (!gui.isIgnoreDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                    return;
                }
                ClickEvent clickEvent = gui.getLeftClickEvent(slot);
                if (clickEvent != null) {
                    clickEvent.run(event);
                    return;
                }
            }
            case RIGHT -> {
                if (!gui.isRightClickable()) {
                    if (!gui.isIgnoreDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                    return;
                }
                ClickEvent clickEvent = gui.getRightClickEvent(slot);
                if (clickEvent != null) {
                    clickEvent.run(event);
                    return;
                }
            }
            case SHIFT_LEFT -> {
                if (gui.isLeftShiftClickable() || gui.isShiftClickable()) {
                    ClickEvent clickEvent = gui.getLeftShiftClickEvent(slot);
                    if (clickEvent != null) {
                        clickEvent.run(event);
                        return;
                    }
                }
                if (!gui.isIgnoreDeniedClick())
                    Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                return;
            }
            case SHIFT_RIGHT -> {
                if (gui.isRightShiftClickable() || gui.isShiftClickable()) {
                    ClickEvent clickEvent = gui.getRightShiftClickEvent(slot);
                    if (clickEvent != null) {
                        clickEvent.run(event);
                        return;
                    }
                    if (!gui.isIgnoreDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                    return;
                }
            }
            case SWAP_OFFHAND -> {
                if (event.isCancelled()) {
                    player.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());
                }
                if (!gui.isSwapOffHandClickable()) {
                    if (!gui.isIgnoreDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                    return;
                }
                ClickEvent clickEvent = gui.getSwapOffhandClickEvent(slot);
                if (clickEvent != null) {
                    clickEvent.run(event);
                    return;
                }
            }
            case MIDDLE -> {
                if (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
                    event.setCancelled(false);
                    return;
                }
                if (!gui.isMiddleClickable()) {
                    if (!gui.isIgnoreDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                    return;
                }
                ClickEvent clickEvent = gui.getMiddleClickEvent(slot);
                if (clickEvent != null) {
                    clickEvent.run(event);
                    return;
                }
            }
            case NUMBER_KEY -> {
                if (!gui.isKeyBoardClickable()) {
                    if (!gui.isIgnoreDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                    return;
                }
                ClickEvent clickEvent = gui.getKeyboardClickEvent(slot);
                if (clickEvent != null) {
                    clickEvent.run(event);
                    return;
                }
            }
        }
    }

    /**
    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        Gui gui = manager.getCurrentGui(player);
        if (gui == null) return;
        Inventory guiInv = gui.getInventory();
        if (guiInv.equals(event.getInventory())) manager.setCurrentGui(player, null);
    }


    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Gui gui = manager.getCurrentGui(player);
        if (gui != null) manager.setCurrentGui(player, null);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Gui gui = manager.getCurrentGui(player);
        if (gui != null) manager.setCurrentGui(player, null);
    }
    */

    /**
     * May not work correctly lol
     */
    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        Gui gui = manager.getCurrentGui(player);
        if (event.getInventory().equals(gui.getInventory())) {
            OpenEvent openEvent = gui.getOpenEvent();
            if (openEvent != null)
                openEvent.run(event);
        }
    }

    /**
     * May not work correctly lol
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        Gui gui = manager.getCurrentGui(player);
        if (event.getInventory().equals(gui.getInventory())) {
            CloseEvent closeEvent = gui.getCloseEvent();
            if (closeEvent != null)
                closeEvent.run(event);
        }
    }
}
