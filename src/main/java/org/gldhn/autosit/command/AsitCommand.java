package org.gldhn.autosit.command;
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
import org.gldhn.autosit.Autosit;
import org.gldhn.autosit.util.ParticleBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AsitCommand implements CommandExecutor {

    public AsitCommand(Autosit autosit) {
        this.autosit = autosit;
    }
    private final Autosit autosit;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            Block block = player.getLocation().getBlock();
            if(player.isOp()){
                if(strings.length==4 && strings[0].equals("add"))return addLocation(strings,player);
                if(strings.length==4 && strings[0].equals("setCenterPoint"))return setCenterPoint(strings,player);
                if(strings.length==4 && strings[0].equals("remove"))return removeLocation(strings,player);
                if(strings.length==7 && strings[0].equals("adds"))return addLocation(strings,player);
                if(strings.length==1 && strings[0].equals("autosit"))return autosit(player);
                if(strings.length==1 && strings[0].equals("list"))return allList(player);
                if(strings.length==1 && strings[0].equals("lookblock"))return lookBlock(player);
                if(strings.length==1 && strings[0].equals("clear")){
                    autosit.getSitblockservice().clearBlockLocations();
                    player.sendMessage("已清空所有位置");
                    return true;
                }
                //GSitAPI.createSeat(block, player);

            }else player.sendMessage("你无权使用这个命令");
        }else Bukkit.getLogger().warning("你不能在控制台使用这个命令");

        return false;
    }

    private boolean lookBlock(Player player) {
        if(autosit.getTickService().getLookBlockTick()){
            autosit.getTickService().setLookBlockTick(false, player);
            player.sendMessage("已关闭显示方块");
        }else {
            autosit.getTickService().setLookBlockTick(true, player);
            player.sendMessage("已开启显示方块");
        }
        return true;
    }

    private boolean allList(Player player) {
        AtomicReference<String> Message = new AtomicReference<>("所有位置：");
        autosit.getSitblockservice().getBlockLocations().forEach(location -> {
            Message.set(Message + "X:" + location.getX() + " Y:" + location.getY() + " Z:" + location.getZ());
        });
        player.sendMessage(Message.get());
        if(autosit.getSitblockservice().getCenterPoint()==null){
            player.sendMessage("未设置中心点");
        }else player.sendMessage("中心点：X:" + autosit.getSitblockservice().getCenterPoint().getX() + " Y:" + autosit.getSitblockservice().getCenterPoint().getY() + " Z:" + autosit.getSitblockservice().getCenterPoint().getZ());
        return true;
    }

    private boolean setCenterPoint(String[] strings, Player player) {
        Location location = new Location(null, Double.parseDouble(strings[1]), Double.parseDouble(strings[2]), Double.parseDouble(strings[3]));
        autosit.getSitblockservice().setCenterPoint(location);
        player.sendMessage("设置中心点成功 to"+ location.getX() + "," + location.getY() + "," + location.getZ());
        return true;
    }

    private boolean removeLocation(String[] strings, Player player) {
        autosit.getSitblockservice().removeBlockLocation(Double.parseDouble(strings[1]), Double.parseDouble(strings[2]), Double.parseDouble(strings[3]));
        player.sendMessage("删除"+ strings[1] + "," + strings[2] + "," + strings[3]+"成功");
        return true;
    }

    private boolean addLocation(String[] strings, Player player){
        if(strings.length==4){
            autosit.getSitblockservice().addBlockLocation(Double.parseDouble(strings[1]), Double.parseDouble(strings[2]), Double.parseDouble(strings[3]));
        }
        if(strings.length==7){
            autosit.getSitblockservice().addBlockLocations(Double.parseDouble(strings[1]), Double.parseDouble(strings[2]), Double.parseDouble(strings[3]), Double.parseDouble(strings[4]), Double.parseDouble(strings[5]), Double.parseDouble(strings[6]));
        }
        player.sendMessage("添加成功");
        return true;
    }
    private boolean autosit(Player sender){
        if(autosit.getSitblockservice().getCenterPoint()== null){
            sender.sendMessage("请先设置中心点！");
            return false;
        }
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        autosit.getSitblockservice().sort();
        int sitblocklength = autosit.getSitblockservice().getBlockLocations().size();
//        List<Location> locations = new ArrayList<>(autosit.getSitblockservice().getBlockLocations());
        List<Location> locations = new ArrayList<>();
        for(Location location:autosit.getSitblockservice().getBlockLocations()){
            locations.add(location.clone());
        }
//        List<Location> locations = autosit.getSitblockservice().getBlockLocations();
//        Bukkit.getLogger().info(locations.toString());
        for(Player player:onlinePlayers){
            if(sitblocklength>0){
                Location location = locations.get(0);
                location.setWorld(sender.getWorld());
                while(location.getBlock().getType().isAir()||location.getBlock().getType()==Material.LIGHT){
                    location.setY(location.getY()-1);
                }
                if(Tag.STAIRS.isTagged(location.getBlock().getType()))
                    GSitAPI.createSeat(location.getBlock(), player, true,0,-0.5,0, 0,true);
                else GSitAPI.createSeat(location.getBlock(), player);
                locations.remove(0);
                sitblocklength = sitblocklength - 1;
            }else {
                sender.sendMessage("位置不足，还有玩家没有位置，请补充");
                break;
            }
        }
        return true;
    }
}
