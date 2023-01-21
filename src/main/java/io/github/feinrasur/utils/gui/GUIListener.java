package io.github.feinrasur.utils.gui;

import io.github.feinrasur.utils.Utils;
import io.github.feinrasur.utils.gui.event.GuiClickEvent;
import io.github.feinrasur.utils.gui.event.GuiCloseEvent;
import io.github.feinrasur.utils.gui.event.GuiOpenEvent;
import io.github.feinrasur.utils.gui.interfaces.CloseEvent;
import io.github.feinrasur.utils.gui.interfaces.OpenEvent;
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

public final class GUIListener implements Listener {

    static UserManager manager;
    static JavaPlugin plugin;

    public GUIListener(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        manager = new UserManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
                if (gui.isEditable()) {
                    gui.setInventory(eventInv);
                    return;
                }
                event.setCancelled(true);
                return;
            }
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
                    if (gui.isInfoDeniedClick())
                        player.sendMessage(Utils.deniedClick());
                    return;
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getLeftClickEvent(slot), ClickType.LEFT_CLICK));
            }
            case RIGHT -> {
                if (!gui.isRightClickable()) {
                    if (gui.isInfoDeniedClick())
                        player.sendMessage(Utils.deniedClick());
                    return;
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getRightClickEvent(slot), ClickType.RIGHT_CLICK));
            }
            case SHIFT_LEFT -> {
                if (gui.isLeftShiftClickable() || gui.isShiftClickable()) {
                    if (gui.isInfoDeniedClick()) {
                        player.sendMessage(Utils.deniedClick());
                        return;
                    }
                    Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getLeftShiftClickEvent(slot), ClickType.LEFT_SHIFT_CLICK));
                }
            }
            case SHIFT_RIGHT -> {
                if (gui.isRightShiftClickable() || gui.isShiftClickable()) {
                    if (gui.isInfoDeniedClick())
                        player.sendMessage(Utils.deniedClick());
                    return;
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getRightShiftClickEvent(slot), ClickType.RIGHT_SHIFT_CLICK));
            }
            case SWAP_OFFHAND -> {
                if (event.isCancelled()) {
                    player.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());
                }
                if (!gui.isSwapOffHandClickable()) {
                    if (gui.isInfoDeniedClick())
                        player.sendMessage(Utils.deniedClick());
                    return;
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getSwapOffhandClickEvent(slot), ClickType.SWAP_OFFHAND_CLICK));
            }
            case MIDDLE -> {
                if (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
                    event.setCancelled(false);
                }
                if (!gui.isMiddleClickable()) {
                    if (gui.isInfoDeniedClick())
                        player.sendMessage(Utils.deniedClick());
                    return;
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getMiddleClickEvent(slot), ClickType.MIDDLE_CLICK));
            }
            case NUMBER_KEY -> {
                if (!gui.isKeyBoardClickable()) {
                    if (gui.isInfoDeniedClick())
                        player.sendMessage(Utils.deniedClick());
                    return;
                }
                Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, player, slot, event.getCurrentItem(), gui.getKeyboardClickEvent(slot), ClickType.KEYBOARD_CLICK));
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

        Gui gui = event.getGui();
        if (gui.isUnClosable()) {
            gui.open(event.getPlayer());
            return;
        }

        CloseEvent closeEvent = gui.getCloseEvent();
        if (closeEvent != null)
            closeEvent.run(event);
    }

    @EventHandler
    public void onGuiClick(GuiClickEvent event) {
        if (!event.isCancelled()) {
            if (event.getClickEvent() != null)
                event.runClickEvent();
        }
    }
}
