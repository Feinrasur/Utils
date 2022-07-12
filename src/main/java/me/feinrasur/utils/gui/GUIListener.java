package me.feinrasur.utils.gui;

import me.feinrasur.utils.chat.Chat;
import me.feinrasur.utils.gui.events.GuiClickEvent;
import me.feinrasur.utils.gui.events.GuiCloseEvent;
import me.feinrasur.utils.gui.events.GuiOpenEvent;
import me.feinrasur.utils.gui.interfaces.CloseEvent;
import me.feinrasur.utils.gui.interfaces.OpenEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("all")
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
                    if (!gui.isIgnoringDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getLeftClickEvent(slot), ClickEventType.LEFT_CLICK));
            }
            case RIGHT -> {
                if (!gui.isRightClickable()) {
                    if (!gui.isIgnoringDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getRightClickEvent(slot), ClickEventType.RIGHT_CLICK));
            }
            case SHIFT_LEFT -> {
                if (gui.isLeftShiftClickable() || gui.isShiftClickable()) {
                    if (!gui.isIgnoringDeniedClick()) {
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                    }
                    Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getLeftShiftClickEvent(slot), ClickEventType.LEFT_SHIFT_CLICK));
                }
            }
            case SHIFT_RIGHT -> {
                if (gui.isRightShiftClickable() || gui.isShiftClickable()) {
                    if (!gui.isIgnoringDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getRightShiftClickEvent(slot), ClickEventType.RIGHT_SHIFT_CLICK));
            }
            case SWAP_OFFHAND -> {
                if (event.isCancelled()) {
                    player.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());
                }
                if (!gui.isSwapOffHandClickable()) {
                    if (!gui.isIgnoringDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getSwapOffhandClickEvent(slot), ClickEventType.SWAP_OFFHAND_CLICK));
            }
            case MIDDLE -> {
                if (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
                    event.setCancelled(false);
                }
                if (!gui.isMiddleClickable()) {
                    if (!gui.isIgnoringDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getMiddleClickEvent(slot), ClickEventType.MIDDLE_CLICK));
            }
            case NUMBER_KEY -> {
                if (!gui.isKeyBoardClickable()) {
                    if (!gui.isIgnoringDeniedClick())
                        Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getKeyboardClickEvent(slot), ClickEventType.KEYBOARD_CLICK));
            }
        }
    }


    @EventHandler
    public void onOpen(GuiOpenEvent event) {
        OpenEvent openEvent = event.getGui().getOpenEvent();
        if (openEvent != null)
            openEvent.run(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        Gui gui = manager.getCurrentGui(player);
        if (gui == null) return;
        if (event.getInventory().equals(gui.getInventory())) {
            Bukkit.getServer().getPluginManager().callEvent(new GuiCloseEvent(gui, player));
        }
    }

    @EventHandler
    public void onCloseGui(GuiCloseEvent event) {
        CloseEvent closeEvent = event.getGui().getCloseEvent();
        if (closeEvent != null)
            closeEvent.run(event);
    }

    @EventHandler
    public void onGuiClick(GuiClickEvent event) {
        event.runClickEvent();
    }
}
