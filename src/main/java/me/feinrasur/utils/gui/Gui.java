package me.feinrasur.utils.gui;

import me.feinrasur.utils.chat.Chat;
import me.feinrasur.utils.gui.annotations.*;
import me.feinrasur.utils.gui.interfaces.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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

    @SuppressWarnings("deprecation")
    public Gui(@Nullable Integer size, @Nullable String name) {

        if (size == null)
            size = 27;
        if (name == null)
            name = "&#2cb2ff&lCustomGUI";
        for (Annotation annotation: this.getClass().getAnnotations()) {

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

    public @Nullable Gui getPreviousGui() {
        return previousGUI;
    }

    public @Nullable Gui getNextGui() {
        return nextGUI;
    }

    public void setNextGui(Gui gui) {
        this.nextGUI = gui;
    }

    private void createClickEvent(Integer slot, ClickEvent clickEvent) {
        leftClickEvents.put(slot, clickEvent);
    }

    public void createClickEvent(Integer slot, ClickEvent clickEvent, ClickEventType type) {
        setClickEventByType(slot, clickEvent, type);
    }


    public void createBackClickEvent(Integer slot) {
        createClickEvent(slot, event -> {
            if (previousGUI != null) {
                previousGUI.open((Player) event.getWhoClicked());
            }
        });
    }

    public void createNextClickEvent(Integer slot) {
        createClickEvent(slot, event -> {
            if (nextGUI != null) {
                nextGUI.open((Player) event.getWhoClicked());
            }
        });
    }

    public void createCloseClickEvent(Integer slot) {
        createClickEvent(slot, event -> event.getWhoClicked().closeInventory());
    }

    public void createLeftClickEvent(Integer slot, ClickEvent clickEvent) {
        leftClickEvents.put(slot, clickEvent);
    }

    public void createRightClickEvent(Integer slot, ClickEvent clickEvent) {
        rightClickEvents.put(slot, clickEvent);
    }

    public void createLeftShiftClickEvent(Integer slot, ClickEvent clickEvent) {
        leftShiftClickEvents.put(slot, clickEvent);
    }

    public void createRightShiftClickEvent(Integer slot, ClickEvent clickEvent) {
        rightShiftClickEvents.put(slot, clickEvent);
    }

    public void createSwapOffhandClickEvent(Integer slot, ClickEvent clickEvent) {
        swapOffhandClickEvents.put(slot, clickEvent);
    }

    public void createKeyboardClickEvent(Integer slot, ClickEvent clickEvent) {
        keyboardClickEvents.put(slot, clickEvent);
    }

    public void createMiddleClickEvent(Integer slot, ClickEvent clickEvent) {
        middleClickEvents.put(slot, clickEvent);
    }

    public void createShiftClickEvent(Integer slot, ClickEvent clickEvent) {
        leftShiftClickEvents.put(slot, clickEvent);
        rightShiftClickEvents.put(slot, clickEvent);
    }

    public @Nullable ClickEvent getLeftClickEvent(Integer slot) {
        if (leftClickEvents.containsKey(slot)) {
            return leftClickEvents.get(slot);
        }
        return null;
    }

    public @Nullable ClickEvent getRightClickEvent(Integer slot) {
        if (rightClickEvents.containsKey(slot)) {
            return rightClickEvents.get(slot);
        }
        return null;
    }

    public @Nullable ClickEvent getLeftShiftClickEvent(Integer slot) {
        if (leftShiftClickEvents.containsKey(slot)) {
            return leftShiftClickEvents.get(slot);
        }
        return null;
    }

    public @Nullable ClickEvent getRightShiftClickEvent(Integer slot) {
        if (rightShiftClickEvents.containsKey(slot)) {
            return rightShiftClickEvents.get(slot);
        }
        return null;
    }

    public @Nullable ClickEvent getSwapOffhandClickEvent(Integer slot) {
        if (swapOffhandClickEvents.containsKey(slot)) {
            return swapOffhandClickEvents.get(slot);
        }
        return null;
    }

    public @Nullable ClickEvent getKeyboardClickEvent(Integer slot) {
        if (keyboardClickEvents.containsKey(slot)) {
            return keyboardClickEvents.get(slot);
        }
        return null;
    }

    public @Nullable ClickEvent getMiddleClickEvent(Integer slot) {
        if (middleClickEvents.containsKey(slot)) {
            return middleClickEvents.get(slot);
        }
        return null;
    }

    public @Nullable List<ClickEvent> getShiftClickEvents(Integer slot) {
        return List.of(leftClickEvents.get(slot), rightClickEvents.get(slot));
    }

    public void open(Player player) {
        player.openInventory(getInventory());
        UserManager.getManager().setCurrentGui(player, this);
    }

    public Inventory getInventory() {
        return guiInventory;
    }

    public void setItem(Integer slot, ItemStack item) {
        guiInventory.setItem(slot, item);
    }

    public void setItem(@NotNull Integer slot, @NotNull ItemStack item, @NotNull ClickEvent clickEvent, @NotNull ClickEventType clickEventType) {
        guiInventory.setItem(slot, item);
        setClickEventByType(slot, clickEvent, clickEventType);
    }

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

    public @Nullable Integer addItem(ItemStack item) {
        int freeSlot = guiInventory.firstEmpty();
        if (freeSlot <= 0) {
            guiInventory.setItem(freeSlot, item);
            return freeSlot;
        }
        return null;
    }

    public @Nullable Integer addItem(ItemStack item, ClickEvent clickEvent, ClickEventType clickEventType) {
        int freeSlot = guiInventory.firstEmpty();
        if (freeSlot <= 0) {
            guiInventory.setItem(freeSlot, item);
            setClickEventByType(freeSlot, clickEvent, clickEventType);
            return freeSlot;
        }
        return null;
    }

    public boolean isLeftClickable() {
        return leftClickable;
    }

    public boolean isRightClickable() {
        return rightClickable;
    }

    public boolean isLeftShiftClickable() {
        return leftShiftClickable;
    }

    public boolean isRightShiftClickable() {
        return rightShiftClickable;
    }

    public boolean isSwapOffHandClickable() {
        return swapOffHandClickable;
    }

    public boolean isMiddleClickable() {
        return middleClickable;
    }

    public boolean isKeyBoardClickable() {
        return keyBoardClickable;
    }

    public boolean isShiftClickable() {
        return isLeftShiftClickable() && isRightShiftClickable();
    }
}
