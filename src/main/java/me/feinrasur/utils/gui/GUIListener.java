package me.feinrasur.utils.gui;

import me.feinrasur.utils.chat.Chat;
import me.feinrasur.utils.gui.interfaces.ClickEvent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

public class GUIListener implements Listener {

    static UserManager manager;

    public GUIListener(JavaPlugin plugin) {
        manager = UserManager.getManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent event)
    {
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        Gui gui = manager.getCurrentGui(player);
        if (gui == null) return;
        if (event.getInventory().equals(gui.getInventory()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        Inventory eventInventory = event.getClickedInventory();
        if (eventInventory == null) return;
        InventoryView view = player.getOpenInventory();
        Inventory topInv = view.getTopInventory();

        Gui gui = manager.getCurrentGui(player);
        if (gui == null) return;
        Inventory guiInv = gui.getInventory();
        if (!eventInventory.equals(guiInv)) return;
        if (!topInv.equals(guiInv)) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);

        Integer slot = event.getSlot();

        switch (event.getClick()) {
            case LEFT -> {
                if (!gui.isLeftClickable()) {
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
                    Chat.send(player, "&cDieser Klick ist nicht erlaubt! Bitte benutze einen anderen Klick.");
                    return;
                }
            }
            case SWAP_OFFHAND -> {
                if (event.isCancelled()) {
                    player.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());
                }
                if (!gui.isSwapOffHandClickable()){
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
                if (!gui.isMiddleClickable()){
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
                if (!gui.isKeyBoardClickable()){
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
}
