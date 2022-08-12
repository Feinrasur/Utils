package io.github.feinrasur.utils.gui;

import io.github.feinrasur.utils.chat.Kyori;
import io.github.feinrasur.utils.gui.annotations.IgnoreDeniedClick;
import io.github.feinrasur.utils.gui.annotations.LeftClickable;
import io.github.feinrasur.utils.gui.events.GuiClickEvent;
import io.github.feinrasur.utils.gui.interfaces.ClickEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@LeftClickable // Only left-mousebutton-clicks are alloewed
@IgnoreDeniedClick // Does not send warning message to user if he uses a denied click
// Create Gui instance by extending it
public class Example extends Gui{

    public Example(@Nullable Integer rows, @Nullable Component name) {
        super(rows, name); // Set the Gui-inventory rows and the name | You can also use a string value

        // Now we set our first item
        setItem(10, new ItemStack(Material.STONE), new ClickEvent() {
            @Override                                   //
            public void run(GuiClickEvent event) {      // This custom runnable will be executed when a user clicks on the gui
                                                        // This event contains the player who clicked, the Clicktype and many things more
            }                                           //
        }, ClickEventType.ALL); // Here you can set which click should trigger the event | You can use ALL to make every click valid - Make sure
        // to use @AllClickable above the class

        // Adding items:
        addItem(new ItemStack(Material.DIRT)); // Adds an item to the first empty slot of the inventory

        createClickEvent(3, new ClickEvent() { //Here you can create only a Clickevent
            @Override                               // You can only use ONE ClickeventType PER slot
            public void run(GuiClickEvent event) {

            }
        }, ClickEventType.LEFT_CLICK); //Only left-click action

    }
}

class CreateExample implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        Gui gui = new Example(4, Kyori.createComponent("This is a test Gui"));

        gui.open(player);

        return false;
    }
}