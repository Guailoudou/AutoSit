package org.gldhn.autosit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.gldhn.autosit.Autosit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AsitTabComplete implements TabCompleter {
    public AsitTabComplete(Autosit autosit) {
        this.autosit = autosit;
    }
    private Autosit autosit;
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> complete = new ArrayList<>();
        Player player = (Player) commandSender;
        if(strings.length == 1)
        {
            complete.add("add");
            complete.add("adds");
            complete.add("remove");
            complete.add("autosit");
            complete.add("setCenterPoint");
            complete.add("list");
            complete.add("clear");
            complete.add("lookblock");
        }
        if (strings.length == 2){
            if(strings[0].equals("add")||strings[0].equals("adds")||strings[0].equals("remove")||strings[0].equals("setCenterPoint"))
                complete.add(String.valueOf((int)player.getLocation().getX()));
        }
        if (strings.length == 3){
            if(strings[0].equals("add")||strings[0].equals("adds")||strings[0].equals("remove")||strings[0].equals("setCenterPoint"))
                complete.add(String.valueOf((int)player.getLocation().getY()));
        }
        if (strings.length == 4){
            if(strings[0].equals("add")||strings[0].equals("adds")||strings[0].equals("remove")||strings[0].equals("setCenterPoint"))
                complete.add(String.valueOf((int)player.getLocation().getZ()));
        }
        if(strings.length == 5){
            if(strings[0].equals("adds")){
                complete.add(String.valueOf((int)player.getLocation().getX()));
            }
        }
        if(strings.length == 6){
            if(strings[0].equals("adds")){
                complete.add(String.valueOf((int)player.getLocation().getY()));
            }
        }
        if(strings.length == 7){
            if(strings[0].equals("adds")){
                complete.add(String.valueOf((int)player.getLocation().getZ()));
            }
        }
        return complete;
    }
}
