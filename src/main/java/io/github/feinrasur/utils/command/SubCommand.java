package io.github.feinrasur.utils.command;

import java.util.List;

public abstract class SubCommand extends BukkitCommand{

    private String name = null;
    private List<String> aliases = null;
    private String permission = null;

    protected SubCommand() {
        //Annotations

        //End Annotations
    }

    //public abstract boolean execute(CommandSender sender, String label, String[] args, String[] previousArgs);
}
