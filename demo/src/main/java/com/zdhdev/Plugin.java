package com.zdhdev;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin implements Listener {

  public void onEnable() {
    System.out.println("+++++++++++++++++ENABLED DEMO++++++++++++++++++++++");
    getServer().getPluginManager().registerEvents(this, this);

  }

  public void onDisable() {
    System.out.println("+++++++++++++++++DISABLED DEMO++++++++++++++++++++++");
  }
}
