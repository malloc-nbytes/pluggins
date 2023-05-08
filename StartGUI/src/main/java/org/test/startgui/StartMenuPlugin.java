package org.test.startgui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class StartMenuPlugin extends JavaPlugin implements Listener {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getCommand("start").setExecutor(this);
        getCommand("sg").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("start") && sender instanceof Player) {
            Player player = (Player) sender;
            openStartMenu(player);
            return true;
        } else if (command.getName().equalsIgnoreCase("sg")) {
            if (args.length > 0) {
                String subcommand = args[0];
                if (subcommand.equalsIgnoreCase("reload")) {
                    reloadConfig();
                    config = getConfig();
                    sender.sendMessage(colorize("&aStartGUI configuration reloaded."));
                    return true;
                } else if (subcommand.equalsIgnoreCase("help")) {
                    sender.sendMessage(colorize("&aStartGUI commands:"));
                    sender.sendMessage(colorize("&a/sg reload - Reload the StartGUI configuration"));
                    return true;
                }
            }
            sender.sendMessage(colorize("&cUnknown command or invalid arguments. Use /sg help for available commands."));
            return true;
        }
        return false;
    }

    private void openStartMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 54, colorize(getConfigString("menu-title")));

        ItemStack fillItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillItemMeta = fillItem.getItemMeta();
        fillItemMeta.setDisplayName(" ");
        fillItem.setItemMeta(fillItemMeta);

        for (int i = 0; i < 54; i++) {
            if (menu.getItem(i) == null) {
                menu.setItem(i, fillItem);
            }
        }

        for (int i = 1; i <= 3; i++) {
            String optionPath = "options.option" + i + ".";
            Material optionMaterial = Material.valueOf(getConfigString(optionPath + "material"));
            String optionDisplayName = colorize(getConfigString(optionPath + "display-name"));
            List<String> optionLore = colorizeList(getConfigStringList(optionPath + "lore"));

            ItemStack optionItem = new ItemStack(optionMaterial);
            ItemMeta optionItemMeta = optionItem.getItemMeta();
            optionItemMeta.setDisplayName(optionDisplayName);
            optionItemMeta.setLore(optionLore);
            optionItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
            optionItem.setItemMeta(optionItemMeta);

            int slot = getConfigInt(optionPath + "slot");
            menu.setItem(slot, optionItem);
        }

        player.openInventory(menu);
    }

    private String getConfigString(String path) {
        return config.getString(path, "");
    }

    private List<String> getConfigStringList(String path) {
        return config.getStringList(path);
    }

    private int getConfigInt(String path) {
        return config.getInt(path, 0);
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private List<String> colorizeList(List<String> list) {
        List<String> colorizedList = new ArrayList<>();
        for (String line : list) {
            colorizedList.add(colorize(line));
        }
        return colorizedList;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(colorize(getConfigString("menu-title")))) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }
            for (int i = 1; i <= 3; i++) {
                String optionPath = "options.option" + i + ".";
                Material optionMaterial = Material.valueOf(getConfigString(optionPath + "material"));
                if (clickedItem.getType() == optionMaterial) {
                    String command = getConfigString(optionPath + "command");
                    if (!command.isEmpty()) {
                        Bukkit.dispatchCommand(player, command);
                    }
                    player.closeInventory();
                    return;
                }
            }
        }
    }
}

