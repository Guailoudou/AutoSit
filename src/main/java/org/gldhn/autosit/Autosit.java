package org.gldhn.autosit;

import org.bukkit.plugin.java.JavaPlugin;
import org.gldhn.autosit.command.AsitCommand;
import org.gldhn.autosit.command.AsitTabComplete;
import org.gldhn.autosit.service.sitblockservice;

import java.util.Objects;

public final class Autosit extends JavaPlugin {
    private sitblockservice sitblockservice;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("autosit 已加载！");
        Objects.requireNonNull(getCommand("asit")).setExecutor(new AsitCommand(this));
        getCommand("asit").setTabCompleter(new AsitTabComplete(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("autosit 已卸载！");
        // Plugin shutdown logic
    }
    public void onLoad() {
        sitblockservice = new sitblockservice(this);
    }
    public sitblockservice getSitblockservice() {
        return sitblockservice;
    }
}
