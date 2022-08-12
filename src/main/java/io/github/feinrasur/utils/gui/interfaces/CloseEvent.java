package io.github.feinrasur.utils.gui.interfaces;

import io.github.feinrasur.utils.gui.events.GuiCloseEvent;

@SuppressWarnings("all")
public interface CloseEvent {
    void run(GuiCloseEvent event);
}
