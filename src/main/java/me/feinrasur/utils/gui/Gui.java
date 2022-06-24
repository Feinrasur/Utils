package me.feinrasur.utils.gui;

import me.feinrasur.utils.chat.Chat;
import me.feinrasur.utils.gui.annotations.*;
import me.feinrasur.utils.gui.interfaces.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class Gui {

    boolean leftClickable = true;
    boolean rightClickable = false;
    boolean leftShiftClickable = false;
    boolean rightShiftClickable = false;
    boolean swapOffHandClickable = false;
    boolean keyBoardClickable = false;
    boolean middleClickable = false;


    Inventory guiInventory;
    Gui previousGUI = null;
    Gui nextGUI = null;

    HashMap<Integer, ClickEvent> clickEvents = new HashMap<>();
    Map<Integer, ClickEvent> leftClickEvents = new HashMap<>();
    Map<Integer, ClickEvent> rightClickEvents = new HashMap<>();
    Map<Integer, ClickEvent> leftShiftClickEvents = new HashMap<>();
    Map<Integer, ClickEvent> rightShiftClickEvents = new HashMap<>();
    Map<Integer, ClickEvent> swapOffhandClickEvents = new HashMap<>();
    Map<Integer, ClickEvent> keyboardClickEvents = new HashMap<>();
    Map<Integer, ClickEvent> middleClickEvents = new HashMap<>();

    /**
     * @param size Size for the inventory of the GUI
     * @param name Name for the inventory of the GUI
     */
    @SuppressWarnings("deprecation")
    public Gui(@Nullable Integer size, @Nullable String name) {

        if (size == null)
            size = 27;
        if (name == null)
            name = "&#2cb2ff&lCustomGUI";
        for (Annotation annotation : this.getClass().getAnnotations()) {

            if (annotation instanceof LeftClickable leftClickable) {
                this.leftClickable = leftClickable.value();
            }
            if (annotation instanceof RightClickable rightClickable) {
                this.rightClickable = rightClickable.value();
            }
            if (annotation instanceof ShiftLeftClickable shiftLeftClickable) {
                this.leftShiftClickable = shiftLeftClickable.value();
            }
            if (annotation instanceof ShiftRightClickable shiftRightClickable) {
                this.rightShiftClickable = shiftRightClickable.value();
            }
            if (annotation instanceof SwapOffhandClickable swapOffhandClickable) {
                this.swapOffHandClickable = swapOffhandClickable.value();
            }
            if (annotation instanceof KeyboardClickable keyboardClickable) {
                this.keyBoardClickable = keyboardClickable.value();
            }
            if (annotation instanceof MiddleClickable middleClickable) {
                this.middleClickable = middleClickable.value();
            }
            if (annotation instanceof ShiftClickable shiftClickable) {
                this.leftShiftClickable = shiftClickable.value();
                this.rightShiftClickable = shiftClickable.value();
            }
        }

        guiInventory = Bukkit.createInventory(null, size, Chat.format(name));
    }

    /**
     * @return The previous GUI of the current GUI
     */
    public @Nullable Gui getPreviousGui() {
        return previousGUI;
    }

    /**
     * @return The next GUI of the current GUI
     */
    public @Nullable Gui getNextGui() {
        return nextGUI;
    }

    /**
     * @param gui Set the previous GUI of the current GUI
     */
    public void setPreviousGui(Gui gui) {
        this.previousGUI = gui;
    }

    /**
     * @param gui Set the next GUI of the current GUI
     */
    public void setNextGui(Gui gui) {
        this.nextGUI = gui;
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createClickEvent(Integer slot, ClickEvent clickEvent) {
        leftClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     * @param type       Set the ClickEventType for the ClickEvent
     */
    public void createClickEvent(Integer slot, ClickEvent clickEvent, ClickEventType type) {
        setClickEventByType(slot, clickEvent, type);
    }

    /**
     * Opens the previousGUI of the current GUI
     *
     * @param slot Slot for the ClickEvent
     */
    public void createBackClickEvent(Integer slot) {
        createClickEvent(slot, event -> {
            if (previousGUI != null) {
                previousGUI.open((Player) event.getWhoClicked());
            }
        });
    }

    /**
     * Opens the next GUI of the current GUI
     *
     * @param slot Slot for the ClickEvent
     */
    public void createNextClickEvent(Integer slot) {
        createClickEvent(slot, event -> {
            if (nextGUI != null) {
                nextGUI.open((Player) event.getWhoClicked());
            }
        });
    }

    /**
     * Closes the current GUI
     *
     * @param slot Slot for the ClickEvent
     */
    public void createCloseClickEvent(Integer slot) {
        createClickEvent(slot, event -> event.getWhoClicked().closeInventory());
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createLeftClickEvent(Integer slot, ClickEvent clickEvent) {
        leftClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createRightClickEvent(Integer slot, ClickEvent clickEvent) {
        rightClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createLeftShiftClickEvent(Integer slot, ClickEvent clickEvent) {
        leftShiftClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createRightShiftClickEvent(Integer slot, ClickEvent clickEvent) {
        rightShiftClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createSwapOffhandClickEvent(Integer slot, ClickEvent clickEvent) {
        swapOffhandClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createKeyboardClickEvent(Integer slot, ClickEvent clickEvent) {
        keyboardClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createMiddleClickEvent(Integer slot, ClickEvent clickEvent) {
        middleClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createShiftClickEvent(Integer slot, ClickEvent clickEvent) {
        leftShiftClickEvents.put(slot, clickEvent);
        rightShiftClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot Slot of the gui to get the ClickEvents from
     * @return ClickEvent in the slot
     */
    public @Nullable ClickEvent getLeftClickEvent(Integer slot) {
        if (leftClickEvents.containsKey(slot)) {
            return leftClickEvents.get(slot);
        }
        return null;
    }

    /**
     * @param slot Slot of the gui to get the ClickEvents from
     * @return ClickEvent in the slot
     */
    public @Nullable ClickEvent getRightClickEvent(Integer slot) {
        if (rightClickEvents.containsKey(slot)) {
            return rightClickEvents.get(slot);
        }
        return null;
    }

    /**
     * @param slot Slot of the gui to get the ClickEvents from
     * @return ClickEvent in the slot
     */
    public @Nullable ClickEvent getLeftShiftClickEvent(Integer slot) {
        if (leftShiftClickEvents.containsKey(slot)) {
            return leftShiftClickEvents.get(slot);
        }
        return null;
    }

    /**
     * @param slot Slot of the gui to get the ClickEvents from
     * @return ClickEvent in the slot
     */
    public @Nullable ClickEvent getRightShiftClickEvent(Integer slot) {
        if (rightShiftClickEvents.containsKey(slot)) {
            return rightShiftClickEvents.get(slot);
        }
        return null;
    }

    /**
     * @param slot Slot of the gui to get the ClickEvents from
     * @return ClickEvent in the slot
     */
    public @Nullable ClickEvent getSwapOffhandClickEvent(Integer slot) {
        if (swapOffhandClickEvents.containsKey(slot)) {
            return swapOffhandClickEvents.get(slot);
        }
        return null;
    }

    /**
     * @param slot Slot of the gui to get the ClickEvents from
     * @return ClickEvent in the slot
     */
    public @Nullable ClickEvent getKeyboardClickEvent(Integer slot) {
        if (keyboardClickEvents.containsKey(slot)) {
            return keyboardClickEvents.get(slot);
        }
        return null;
    }

    /**
     * @param slot Slot of the gui to get the ClickEvents from
     * @return ClickEvent in the slot
     */
    public @Nullable ClickEvent getMiddleClickEvent(Integer slot) {
        if (middleClickEvents.containsKey(slot)) {
            return middleClickEvents.get(slot);
        }
        return null;
    }

    /**
     * @param slot Slot of the gui to get the ClickEvents from
     * @return List of the ClickEvents in the slots
     */
    public @Nullable List<ClickEvent> getShiftClickEvents(Integer slot) {
        return List.of(leftShiftClickEvents.get(slot), rightShiftClickEvents.get(slot));
    }

    /**
     * @param player Player to open the inventory for
     */
    public void open(Player player) {
        player.openInventory(getInventory());
        GUIListener.manager.setCurrentGui(player, this);
    }

    /**
     * @return Inventory of the GUI
     */
    public Inventory getInventory() {
        return guiInventory;
    }

    /**
     * @param slot Set the slot for the Item
     * @param item item to put in the slot
     */
    public void setItem(Integer slot, ItemStack item) {
        guiInventory.setItem(slot, item);
    }

    /**
     * @param slot           Set the slot for the Item
     * @param item           item to put in the slot
     * @param clickEvent     Set the ClickEvent
     * @param clickEventType Set the ClickEventType for the ClickEvent
     */
    public void setItem(@NotNull Integer slot, @NotNull ItemStack item, @NotNull ClickEvent clickEvent, @NotNull ClickEventType clickEventType) {
        guiInventory.setItem(slot, item);
        setClickEventByType(slot, clickEvent, clickEventType);
    }

    /**
     * @param slot           Set the slot for the ClickEvent
     * @param clickEvent     set the ClickEvent
     * @param clickEventType Set the ClickEventType for the ClickEvent
     */
    private void setClickEventByType(Integer slot, ClickEvent clickEvent, ClickEventType clickEventType) {
        switch (clickEventType) {
            case LEFT_CLICK -> leftClickEvents.put(slot, clickEvent);
            case RIGHT_CLICK -> rightClickEvents.put(slot, clickEvent);
            case LEFT_SHIFT_CLICK -> leftShiftClickEvents.put(slot, clickEvent);
            case RIGHT_SHIFT_CLICK -> rightShiftClickEvents.put(slot, clickEvent);
            case SWAP_OFFHAND_CLICK -> swapOffhandClickEvents.put(slot, clickEvent);
            case KEYBOARD_CLICK -> keyboardClickEvents.put(slot, clickEvent);
            case MIDDLE_CLICK -> middleClickEvents.put(slot, clickEvent);
            case SHIFT_CLICK -> {
                leftShiftClickEvents.put(slot, clickEvent);
                rightShiftClickEvents.put(slot, clickEvent);
            }
        }
    }

    /**
     * @param item item to add
     * @return Slot where item got put in
     */
    public @Nullable Integer addItem(ItemStack item) {
        int freeSlot = guiInventory.firstEmpty();
        if (freeSlot >= 0) {
            guiInventory.setItem(freeSlot, item);
            return freeSlot;
        }
        return null;
    }

    /**
     * @param item           item to add
     * @param clickEvent     set the ClickEvent for the item
     * @param clickEventType set the ClickEvent type for the ClickEvent
     * @return Slot where item got put in
     */
    public @Nullable Integer addItem(ItemStack item, ClickEvent clickEvent, ClickEventType clickEventType) {
        int freeSlot = guiInventory.firstEmpty();
        if (freeSlot >= 0) {
            guiInventory.setItem(freeSlot, item);
            setClickEventByType(freeSlot, clickEvent, clickEventType);
            return freeSlot;
        }
        return null;
    }

    /**
     * @return If Leftclicks are allowed
     */
    public boolean isLeftClickable() {
        return leftClickable;
    }

    /**
     * @return If Righttclicks are allowed
     */
    public boolean isRightClickable() {
        return rightClickable;
    }

    /**
     * @return If Leftshiftclicks are allowed
     */
    public boolean isLeftShiftClickable() {
        return leftShiftClickable;
    }

    /**
     * @return If Rightshiftclicks are allowed
     */
    public boolean isRightShiftClickable() {
        return rightShiftClickable;
    }

    /**
     * @return If SwapOffhandclicks are allowed (F on the keyboard)
     */
    public boolean isSwapOffHandClickable() {
        return swapOffHandClickable;
    }

    /**
     * @return If Scrollwheelclicks are allowed
     */
    public boolean isMiddleClickable() {
        return middleClickable;
    }

    /**
     * @return If Keyboardclicks are allowed
     */
    public boolean isKeyBoardClickable() {
        return keyBoardClickable;
    }

    /**
     * @return If Shiftclicks are allowed
     */
    public boolean isShiftClickable() {
        return isLeftShiftClickable() && isRightShiftClickable();
    }

    /**
     * @param item Item to convert
     * @param name Sets the name for the item
     * @return Custom modified item
     */
    public static ItemStack convertGuiItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.setDisplayName(Chat.format(name));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
}
