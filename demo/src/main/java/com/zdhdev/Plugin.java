package com.zdhdev;

import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

  public void onEnable() {
    System.out.println("+++++++++++++++++ENABLED DEMO++++++++++++++++++++++");
  }

  public void onDisable() {
    System.out.println("+++++++++++++++++DISABLED DEMO++++++++++++++++++++++");
  }
}
