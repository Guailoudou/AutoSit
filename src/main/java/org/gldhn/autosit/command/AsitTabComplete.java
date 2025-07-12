package org.gldhn.autosit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.gldhn.autosit.Autosit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AsitTabComplete implements TabCompleter {
    public AsitTabComplete(Autosit autosit) {
        this.autosit = autosit;
    }
    private Autosit autosit;
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
