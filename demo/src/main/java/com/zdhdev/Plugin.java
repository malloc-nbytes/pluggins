package com.zdhdev;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
  public void onEnable() {
    System.out.println("++++++++++++++++++++DEMO ENABLED++++++++++++++++++++");
    System.out.println("This is the plugin's onEnable() method.");
    System.out.println("Enabling now.");
  }

  public void onDisable() {
    System.out.println("++++++++++++++++++++DEMO DISABLED++++++++++++++++++++");
    System.out.println("This is the plugin's onDisable() method.");
    System.out.println("Exiting now.");
  }
}
