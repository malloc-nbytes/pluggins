package org.test.startgui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class StartMenuPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("StartMenuPlugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("StartMenuPlugin disabled!");
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();

        if (command.equals("/start")) {
            event.setCancelled(true);
            openStartMenu(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.BOLD + "Start Menu")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().equals(ChatColor.BOLD + "Start Menu")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if (event.getDestination().getType().equals(ChatColor.BOLD + "Start Menu")) {
            event.setCancelled(true);
        }
    }

    private void openStartMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 54, ChatColor.BOLD + "Start Menu");

        ItemStack item1 = new ItemStack(Material.DIRT);
        ItemMeta item1Meta = item1.getItemMeta();
        item1Meta.setDisplayName(ChatColor.YELLOW + "Option 1");
        item1.setItemMeta(item1Meta);

        ItemStack item2 = new ItemStack(Material.DIRT);
        ItemMeta item2Meta = item2.getItemMeta();
        item2Meta.setDisplayName(ChatColor.YELLOW + "Option 2");
        item2.setItemMeta(item2Meta);

        ItemStack item3 = new ItemStack(Material.DIRT);
        ItemMeta item3Meta = item3.getItemMeta();
        item3Meta.setDisplayName(ChatColor.YELLOW + "Option 3");
        item3.setItemMeta(item3Meta);

        menu.setItem(20, item1);
        menu.setItem(22, item2);
        menu.setItem(24, item3);

        player.openInventory(menu);
    }
}
