package me.feinrasur.utils.gui;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class Paginator {

    private final List<Gui> guiList = new ArrayList<>();

    public Paginator(List<Gui> guiList) {
        this.guiList.addAll(guiList);
    }

    public Paginator(Gui... guis) {
        guiList.addAll(Arrays.asList(guis));
    }

    public Paginator(Gui gui) {
        guiList.add(gui);
    }

    public void addGui(Gui gui) {
        guiList.add(gui);
    }

    public void addGui(Gui gui, int index) {
        guiList.add(index, gui);
    }

    public void removeGui(Gui gui) {
        guiList.remove(gui);
    }

    public void removeGui(int index) {
        guiList.remove(index);
    }

    public @Nullable Gui getPreviousPage(Gui gui) {
        if (guiList.contains(gui)) {
            int i = guiList.indexOf(gui);
            if (i > 0) {
                int prev = i - 1;
                return guiList.get(prev);
            }
            return null;
        }
        return null;
    }

    public @Nullable Gui getPreviousPage(int index) {
        Gui gui = guiList.get(index);
        if (gui != null) {
            if (index > 0) {
                return guiList.get(index - 1);
            }
            return null;
        }
        return null;
    }

    public void openPreviousPage(Gui gui, Player player) {
        Gui prev = getPreviousPage(gui);
        if (prev != null)
            prev.open(player);
    }

    public void openPreviousPage(int index, Player player) {
        Gui prev = getPreviousPage(index);
        if (prev != null)
            prev.open(player);
    }

    public @Nullable Gui getNextPage(Gui gui) {
        if (guiList.contains(gui)) {
            int i = guiList.indexOf(gui);
            int next = i + 1;
            return guiList.get(next);
        }
        return null;
    }

    public @Nullable Gui getNextPage(int index) {
        Gui gui = guiList.get(index);
        if (gui != null) {
            if (index > 0) {
                return guiList.get(index + 1);
            }
            return null;
        }
        return null;
    }

    public void openNextPage(Gui gui, Player player) {
        Gui prev = getNextPage(gui);
        if (prev != null)
            prev.open(player);
    }

    public void openNextPage(int index, Player player) {
        Gui prev = getNextPage(index);
        if (prev != null)
            prev.open(player);
    }
}
