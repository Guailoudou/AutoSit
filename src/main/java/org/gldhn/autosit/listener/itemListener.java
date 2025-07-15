package org.gldhn.autosit.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.gldhn.autosit.Autosit;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class itemListener implements Listener {
    private final long COOLDOWN_TIME = 500;
    private HashMap<UUID, Long> playerLastActionTime = new HashMap<>();
    private Autosit autosit;
    private HashMap<UUID,Location> rightLocation = new HashMap<>(); //右
    private HashMap<UUID,Location> leftLocation = new HashMap<>(); //左
    public itemListener(Autosit autosit) {
        this.autosit = autosit;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        Action action = event.getAction();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!autosit.getTickService().getLookBlockTick())return;
        if(!item.getType().equals(Material.IRON_SHOVEL))return;
        if(!player.isOp())return;
        long currentTime = System.currentTimeMillis();
        if(playerLastActionTime.containsKey(playerId)&&(currentTime - playerLastActionTime.get(playerId) < COOLDOWN_TIME)) return;

        playerLastActionTime.put(playerId, currentTime);

        Block targetBlock = event.getClickedBlock();
        Block adjacentBlock = Objects.requireNonNull(targetBlock).getRelative(event.getBlockFace());

        //右
        if(action == Action.RIGHT_CLICK_BLOCK){
            rightLocation.put(playerId, adjacentBlock.getLocation());
            player.sendMessage("§6已记录右击方块位置："+ adjacentBlock.getLocation());
            event.setCancelled(true);
        }
        //左
        if(action == Action.LEFT_CLICK_BLOCK){
            leftLocation.put(playerId, adjacentBlock.getLocation());
            player.sendMessage("§6已记录左击方块位置"+ adjacentBlock.getLocation());
            event.setCancelled(true);
        }
    }

    public HashMap<UUID, Location> getRightLocation() {
        return rightLocation;
    }


    public HashMap<UUID, Location> getLeftLocation() {
        return leftLocation;
    }

}
