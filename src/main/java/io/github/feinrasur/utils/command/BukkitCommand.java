package io.github.feinrasur.utils.command;

import java.util.List;

public abstract class BukkitCommand {

    private String name = null;
    private List<String> aliases = null;
    private String permission = null;

    protected BukkitCommand() {
        //Annotations

        //End Annotations
    }

    //public abstract boolean execute(CommandSender sender, String label, String[] args, String[] previousArgs);
}
