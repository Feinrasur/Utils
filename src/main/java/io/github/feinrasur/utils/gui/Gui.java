package io.github.feinrasur.utils.gui;

import io.github.feinrasur.utils.chat.Chat;
import io.github.feinrasur.utils.gui.annotation.*;
import io.github.feinrasur.utils.gui.event.GuiOpenEvent;
import io.github.feinrasur.utils.gui.interfaces.ClickEvent;
import io.github.feinrasur.utils.gui.interfaces.CloseEvent;
import io.github.feinrasur.utils.gui.interfaces.OpenEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Gui {

    boolean leftClickable = true;
    boolean rightClickable = false;
    boolean leftShiftClickable = false;
    boolean rightShiftClickable = false;
    boolean swapOffHandClickable = false;
    boolean keyBoardClickable = false;
    boolean middleClickable = false;
    boolean ignoreDeniedClick = false;
    boolean editable = false;


    private Inventory guiInventory;
    private Gui previousGUI = null;
    private Gui nextGUI = null;
    private int rows;
    private int size;
    private String permission = null;

    private final Map<Integer, ClickEvent> leftClickEvents = new HashMap<>();
    private final Map<Integer, ClickEvent> rightClickEvents = new HashMap<>();
    private final Map<Integer, ClickEvent> leftShiftClickEvents = new HashMap<>();
    private final Map<Integer, ClickEvent> rightShiftClickEvents = new HashMap<>();
    private final Map<Integer, ClickEvent> swapOffhandClickEvents = new HashMap<>();
    private final Map<Integer, ClickEvent> keyboardClickEvents = new HashMap<>();
    private final Map<Integer, ClickEvent> middleClickEvents = new HashMap<>();
    private CloseEvent closeEvent = null;
    private OpenEvent openEvent = null;

    /**
     * @param rows rows for the inventory of the GUI
     * @param name Name for the inventory of the GUI
     */
    @Deprecated
    public Gui(@Nullable Integer rows, @Nullable String name) {
        if (name == null)
            name = "&#2cb2ff&lCustomGUI";
        init(rows);
        TextComponent cmp = Chat.formatComponent(name);
        guiInventory = Bukkit.createInventory(null, size, cmp);
    }

    /**
     * @param rows rows for the inventory of the GUI
     * @param name Name for the inventory of the GUI
     */
    public Gui(@Nullable Integer rows, @Nullable Component name) {
        if (name == null)
            name = Chat.formatComponent("&#2cb2ff&lCustomGUI");
        init(rows);
        guiInventory = Bukkit.createInventory(null, size, name);
    }

    private void init(Integer rows) {
        if (rows == null) {
            rows = 1;
        }
        if (rows < 1) {
            rows = 1;
        }
        if (rows > 6) {
            rows = 6;
        }

        switch (rows) {
            case 1 -> size = 9;
            case 2 -> size = 18;
            case 4 -> size = 36;
            case 5 -> size = 45;
            case 6 -> size = 54;
            default -> size = 27;
        }

        this.rows = rows;

        for (Annotation annotation : this.getClass().getAnnotations()) {

            if (annotation instanceof LeftClickable leftClickable) {
                this.leftClickable = leftClickable.value();
            }
            if (annotation instanceof RightClickable rightClickable) {
                this.rightClickable = rightClickable.value();
            }
            if (annotation instanceof LeftShiftClickable shiftLeftClickable) {
                this.leftShiftClickable = shiftLeftClickable.value();
            }
            if (annotation instanceof LeftShiftClickable rightShiftClickable) {
                this.rightShiftClickable = rightShiftClickable.value();
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
            if (annotation instanceof AllClickable allClickable) {
                this.leftClickable = true;
                this.rightClickable = true;
                this.leftShiftClickable = true;
                this.rightShiftClickable = true;
                this.swapOffHandClickable = true;
                this.keyBoardClickable = true;
                this.middleClickable = true;
            }
            if (annotation instanceof InfoDeniedClick) {
                ignoreDeniedClick = true;
            }

            if (annotation instanceof Editable) {
                editable = true;
            }
            if (annotation instanceof Permission permission) {
                this.permission = permission.value();
            }
        }
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
     * Automatically creates LeftClickEvent
     *
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createClickEvent(Integer slot, ClickEvent clickEvent) {
        leftClickEvents.put(slot, clickEvent);
    }

    /**
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     * @param type       Set the type for the ClickEvent
     */
    public void createClickEvent(Integer slot, ClickType type, ClickEvent clickEvent) {
        setClickEventByType(slot, type, clickEvent);
    }

    /**
     * Opens the previousGUI of the current GUI
     *
     * @param slot Slot for the ClickEvent
     */
    public void createBackClickEvent(Integer slot) {
        createClickEvent(slot, event -> {
            if (previousGUI != null) {
                previousGUI.open(event.getPlayer());
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
                nextGUI.open(event.getPlayer());
            }
        });
    }

    /**
     * Closes the current GUI
     *
     * @param slot Slot for the ClickEvent
     */
    public void createCloseClickEvent(Integer slot) {
        createClickEvent(slot, event -> event.getPlayer().closeInventory());
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
     * Creates ClickEvents for all types. See type
     *
     * @param slot       Slot for the ClickEvent
     * @param clickEvent ClickEvent to be runned when clicked on the Slot
     */
    public void createAllClickEvent(Integer slot, ClickEvent clickEvent) {
        leftClickEvents.put(slot, clickEvent);
        rightClickEvents.put(slot, clickEvent);
        leftShiftClickEvents.put(slot, clickEvent);
        rightShiftClickEvents.put(slot, clickEvent);
        swapOffhandClickEvents.put(slot, clickEvent);
        keyboardClickEvents.put(slot, clickEvent);
        middleClickEvents.put(slot, clickEvent);
    }

    public void createCloseEvent(CloseEvent closeEvent) {
        this.closeEvent = closeEvent;
    }

    public void createOpenEvent(OpenEvent openEvent) {
        this.openEvent = openEvent;
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

    public @Nullable CloseEvent getCloseEvent() {
        return closeEvent;
    }

    public @Nullable OpenEvent getOpenEvent() {
        return openEvent;
    }

    /**
     * @param player Player to open the inventory for
     */
    public void open(Player player) {
        String permission = getPermission();
        if (permission == null) {
            GUIListener.plugin.getServer().getPluginManager().callEvent(new GuiOpenEvent(this, player));
            player.openInventory(getInventory());
            GUIListener.manager.setCurrentGui(player, this);
            return;
        }
        if (player.hasPermission(permission) || permission == null) {
            GUIListener.plugin.getServer().getPluginManager().callEvent(new GuiOpenEvent(this, player));
            player.openInventory(getInventory());
            GUIListener.manager.setCurrentGui(player, this);
        } else {
            Chat.send(player, "No permission to open this gui.");
        }
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
     * @param slot       Set the slot for the Item
     * @param item       item to put in the slot
     * @param clickEvent Set the ClickEvent
     * @param type       Set the type for the ClickEvent
     */
    public void setItem(@NotNull Integer slot, @NotNull ItemStack item, @NotNull ClickType type, @NotNull ClickEvent clickEvent) {
        guiInventory.setItem(slot, item);
        setClickEventByType(slot, type, clickEvent);
    }

    /**
     * @param slot       Set the slot for the ClickEvent
     * @param clickEvent set the ClickEvent
     * @param type       Set the type for the ClickEvent
     */
    private void setClickEventByType(Integer slot, ClickType type, ClickEvent clickEvent) {
        switch (type) {
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
            case ALL -> {
                leftClickEvents.put(slot, clickEvent);
                rightClickEvents.put(slot, clickEvent);
                leftShiftClickEvents.put(slot, clickEvent);
                rightShiftClickEvents.put(slot, clickEvent);
                swapOffhandClickEvents.put(slot, clickEvent);
                keyboardClickEvents.put(slot, clickEvent);
                middleClickEvents.put(slot, clickEvent);
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
     * Fills the empty slots in the inventory with Light_gray_stained_glass_panes
     */
    public void fillInventory() {
        int x;
        ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Chat.formatComponent("&o"));
        item.setItemMeta(meta);
        while (guiInventory.firstEmpty() != -1) {
            x = guiInventory.firstEmpty();
            setItem(x, item);
        }
    }

    /**
     * Fills the empty slots in the inventory with the item of your choice
     */
    public void fillInventory(ItemStack item) {
        int x;
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Chat.formatComponent("&o"));
        item.setItemMeta(meta);
        while (guiInventory.firstEmpty() != -1) {
            x = guiInventory.firstEmpty();
            setItem(x, item);
        }
    }

    /**
     * Sets a row full with an item
     *
     * @param row  Row to set
     * @param item Item for row
     */
    public void setRow(Integer row, ItemStack item) {
        if (row > 0 && row <= this.rows) {
            getSlotsByRow(row).forEach(r -> setItem(r, item));
        }
    }

    /**
     * Sets a row full with an item
     *
     * @param row        Row to set
     * @param item       Item for row
     * @param clickEvent clickEvent for every item in the row
     * @param type       ClickType for the ClickEvent
     */
    public void setRow(Integer row, ItemStack item, ClickType type, ClickEvent clickEvent) {
        if (row > 0 && row <= this.rows) {
            getSlotsByRow(row).forEach(r -> setItem(r, item, type, clickEvent));
        }
    }

    /**
     * Fills a row full with an item
     *
     * @param row  Row to fill
     * @param item Item for row
     */
    public void fillRow(Integer row, ItemStack item) {
        if (row > 0 && row <= this.rows) {
            getSlotsByRow(row).forEach(r -> {
                if (guiInventory.getItem(r) == null) {
                    setItem(r, item);
                }
            });
        }
    }

    /**
     * Fills a row full with an item
     *
     * @param row        Row to fill
     * @param item       Item for row
     * @param clickEvent clickEvent for every item in the row
     * @param type       ClickType for the ClickEvent
     */
    public void fillRow(Integer row, ItemStack item, ClickType type, ClickEvent clickEvent) {
        if (row > 0 && row <= this.rows) {
            getSlotsByRow(row).forEach(r -> {
                if (guiInventory.getItem(r) == null) {
                    setItem(r, item, type, clickEvent);
                }
            });
        }
    }

    /**
     * @param item       item to add
     * @param clickEvent set the ClickEvent for the item
     * @param type       set the ClickEvent type for the ClickEvent
     * @return Slot where item got put in
     */
    public @Nullable Integer addItem(ItemStack item, ClickType type, ClickEvent clickEvent) {
        int freeSlot = guiInventory.firstEmpty();
        if (freeSlot >= 0) {
            guiInventory.setItem(freeSlot, item);
            setClickEventByType(freeSlot, type, clickEvent);
            return freeSlot;
        }
        return null;
    }

    /**
     * @param inventory inventory to set for the gui-inventory
     */
    public void setInventory(Inventory inventory) {
        this.guiInventory = inventory;
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
     * @return If denied clicks are ignored
     */
    public boolean isInfoDeniedClick() {
        return ignoreDeniedClick;
    }

    /**
     * @return If gui is editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @return Permission to open the gui
     */
    public String getPermission() {
        return this.permission;
    }

    /**
     * @param leftClickable Set rule if left-clicks are allowed
     */
    public void setLeftClickable(boolean leftClickable) {
        this.leftClickable = leftClickable;
    }

    /**
     * @param rightClickable Set rule if right-clicks are allowed
     */
    public void setRightClickable(boolean rightClickable) {
        this.rightClickable = rightClickable;
    }

    /**
     * @param leftShiftClickable Set rule if left-shift-clicks are allowed
     */
    public void setLeftShiftClickable(boolean leftShiftClickable) {
        this.leftShiftClickable = leftShiftClickable;
    }

    /**
     * @param rightShiftClickable Set rule if right-shift-clicks are allowed
     */
    public void setRightShiftClickable(boolean rightShiftClickable) {
        this.rightShiftClickable = rightShiftClickable;
    }

    /**
     * @param swapOffHandClickable Set rule if swap-offhand-clicks are allowed ("f" on the keyborad)
     */
    public void setSwapOffHandClickable(boolean swapOffHandClickable) {
        this.swapOffHandClickable = swapOffHandClickable;
    }

    /**
     * @param keyBoardClickable Set rule if num-clicks (1-9) are allowed
     */
    public void setKeyBoardClickable(boolean keyBoardClickable) {
        this.keyBoardClickable = keyBoardClickable;
    }

    /**
     * @param middleClickable Set rule if middle-clicks are allowed
     */
    public void setMiddleClickable(boolean middleClickable) {
        this.middleClickable = middleClickable;
    }

    /**
     * @param ignoreDeniedClick Set rule to ignore denied clicks
     */
    public void setIgnoreDeniedClick(boolean ignoreDeniedClick) {
        this.ignoreDeniedClick = ignoreDeniedClick;
    }

    /**
     * @param editable Set rule to make the opened gui editable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @param item Item to convert
     * @param name Sets the name for the item
     * @return Custom modified item
     */
    @Deprecated
    public static ItemStack convertGuiItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.MENDING, 1, true);
        Map<TextDecoration, TextDecoration.State> decorations = new HashMap<>() {
            {
                put(TextDecoration.OBFUSCATED, TextDecoration.State.FALSE);
                put(TextDecoration.BOLD, TextDecoration.State.FALSE);
                put(TextDecoration.STRIKETHROUGH, TextDecoration.State.FALSE);
                put(TextDecoration.UNDERLINED, TextDecoration.State.FALSE);
                put(TextDecoration.ITALIC, TextDecoration.State.FALSE);
            }
        };
        TextComponent component = Chat.formatComponent(name);
        component.decorations(decorations);
        meta.displayName(component);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack convertGuiItem(ItemStack item, Component name) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.displayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * @param row Row to get
     * @return The slots of the row
     */
    private static List<Integer> getSlotsByRow(Integer row) {
        List<Integer> slots = new ArrayList<>();
        switch (row) {
            case 1 -> {
                for (int i = 0; i < 9; i++) {
                    slots.add(i);
                }
            }
            case 2 -> {
                for (int i = 9; i < 18; i++) {
                    slots.add(i);
                }
            }
            case 3 -> {
                for (int i = 18; i < 27; i++) {
                    slots.add(i);
                }
            }
            case 4 -> {
                for (int i = 27; i < 36; i++) {
                    slots.add(i);
                }
            }
            case 5 -> {
                for (int i = 36; i < 45; i++) {
                    slots.add(i);
                }
            }
            case 6 -> {
                for (int i = 45; i < 54; i++) {
                    slots.add(i);
                }
            }
        }
        return slots;
    }
}
