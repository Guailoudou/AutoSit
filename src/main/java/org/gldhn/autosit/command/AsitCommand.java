package org.gldhn.autosit.command;
import dev.geco.gsit.GSitMain;
import dev.geco.gsit.api.GSitAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.gldhn.autosit.Autosit;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AsitCommand implements CommandExecutor {

    public AsitCommand(Autosit autosit) {
        this.autosit = autosit;
    }
    private final Autosit autosit;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            Block block = player.getLocation().getBlock();
            if(player.isOp()){
                if(strings.length==4 && strings[0].equals("add"))addLocation(strings);
                if(strings.length==4 && strings[0].equals("remove"))removeLocation(strings);
                if(strings.length==7 && strings[0].equals("add"))addLocation(strings);
                if(strings.length==1 && strings[0].equals("autosit"))autosit(player);
                GSitAPI.createSeat(block, player);

            }else player.sendMessage("你无权使用这个命令");
        }else Bukkit.getLogger().warning("你不能在控制台使用这个命令");

        return false;
    }

    private void removeLocation(String[] strings) {
        autosit.getSitblockservice().removeBlockLocation(Double.parseDouble(strings[1]), Double.parseDouble(strings[2]), Double.parseDouble(strings[3]));
    }

    private void addLocation(String[] strings){
        if(strings.length==4){
            autosit.getSitblockservice().addBlockLocation(Double.parseDouble(strings[1]), Double.parseDouble(strings[2]), Double.parseDouble(strings[3]));
        }
        if(strings.length==7){
            autosit.getSitblockservice().addBlockLocations(Double.parseDouble(strings[1]), Double.parseDouble(strings[2]), Double.parseDouble(strings[3]), Double.parseDouble(strings[4]), Double.parseDouble(strings[5]), Double.parseDouble(strings[6]));
        }
    }
    private void autosit(Player sender){
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        int sitblocklength = autosit.getSitblockservice().getBlockLocations().size();
        List<Location> locations = autosit.getSitblockservice().getBlockLocations();
        for(Player player:onlinePlayers){
            if(sitblocklength>0){
                Location location = locations.get(sitblocklength / 2);
                if(location.getBlock().getType().isAir()){
                    location.setY(location.getY()-1);
                }
                if(Tag.STAIRS.isTagged(location.getBlock().getType()))
                    GSitAPI.createSeat(location.getBlock(), player, true,0,-0.5,0, 0,true);
                else GSitAPI.createSeat(location.getBlock(), player);
                locations.remove(sitblocklength / 2);
                sitblocklength = sitblocklength - 1;
            }else {
                sender.sendMessage("位置不足，还有玩家没有位置，请补充");
                break;
            }
        }

    }
}
