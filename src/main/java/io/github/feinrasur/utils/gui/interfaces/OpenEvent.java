package io.github.feinrasur.utils.gui.interfaces;

import io.github.feinrasur.utils.gui.events.GuiOpenEvent;

@SuppressWarnings("all")
public interface OpenEvent {
    void run(GuiOpenEvent event);
}
