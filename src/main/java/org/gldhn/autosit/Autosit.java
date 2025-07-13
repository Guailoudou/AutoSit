package org.gldhn.autosit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.gldhn.autosit.command.AsitCommand;
import org.gldhn.autosit.command.AsitTabComplete;
import org.gldhn.autosit.service.SitBlockService;
import org.gldhn.autosit.service.TickService;

import java.util.Objects;

public final class Autosit extends JavaPlugin {
    private SitBlockService sitblockservice;
    private TickService tickService;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("autosit 已加载！");
        Objects.requireNonNull(getCommand("asit")).setExecutor(new AsitCommand(this));
        Objects.requireNonNull(getCommand("asit")).setTabCompleter(new AsitTabComplete(this));
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            // 这里是每tick都会执行的代码
            tickService.tick();
        }, 0L, 5L);
    }

    @Override
    public void onDisable() {
        getLogger().info("autosit 已卸载！");
        // Plugin shutdown logic
    }
    public void onLoad() {
        tickService = new TickService(this);
        sitblockservice = new SitBlockService(this);
    }
    public SitBlockService getSitblockservice() {
        return sitblockservice;
    }
    public TickService getTickService() {
        return tickService;
    }
}
