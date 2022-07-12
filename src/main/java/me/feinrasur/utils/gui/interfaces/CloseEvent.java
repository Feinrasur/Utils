package me.feinrasur.utils.gui.interfaces;

import me.feinrasur.utils.gui.events.GuiCloseEvent;

@SuppressWarnings("all")
public interface CloseEvent {
    void run(GuiCloseEvent event);
}
