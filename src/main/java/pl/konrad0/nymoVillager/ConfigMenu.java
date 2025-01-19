package pl.konrad0.nymoVillager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ConfigMenu implements Listener {


    public Villager sprzedawca;

    ConfigMenu(Player player, Entity entity){
        Inventory inv = Bukkit.createInventory(player, 54, ChatColor.RED.toString() + ChatColor.BOLD + "Trader configuration");
        sprzedawca = (Villager) entity;

        List<MerchantRecipe> recepies = sprzedawca.getRecipes();
        int i = 0;
        for(MerchantRecipe r : recepies){
            if(i<6){
                if(r.getIngredients().size() == 1){
                    inv.setItem(i*9,r.getIngredients().get(0));
                } else if (r.getIngredients().size() == 2){
                    inv.setItem(i*9,r.getIngredients().get(0));
                    inv.setItem(i*9+1,r.getIngredients().get(1));
                }
                inv.setItem(i*9+2,r.getResult());
                i++;
            }else{
                if(r.getIngredients().size() == 1){
                    inv.setItem((i-6)*9+4,r.getIngredients().get(0));
                } else if (r.getIngredients().size() == 2){
                    inv.setItem((i-6)*9+4,r.getIngredients().get(0));
                    inv.setItem((i-6)*9+5,r.getIngredients().get(1));
                }
                inv.setItem((i-6)*9+6,r.getResult());
                i++;
            }

        }

        //glass panes
        int[] numbers = {3,7,12,21,30,39,48,16,25,34,43,52};
        for(int j : numbers){
            ItemStack item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("|");
            item.setItemMeta(itemMeta);
            inv.setItem(j,item);
        }


        //save
        ItemStack exitAndSave = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta exitAndSaveMeta = exitAndSave.getItemMeta();
        exitAndSaveMeta.setDisplayName(ChatColor.GREEN + "Save and exit");
        exitAndSave.setItemMeta(exitAndSaveMeta);
        inv.setItem(53,exitAndSave);

        //dont save
        ItemStack exit = new ItemStack(Material.RED_CONCRETE);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.RED + "Cancel");
        exit.setItemMeta(exitMeta);
        inv.setItem(44,exit);

        //glowing
        ItemStack glowing = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta glowingMeta = glowing.getItemMeta();
        glowingMeta.setDisplayName(ChatColor.YELLOW + "Change glowing effect");
        glowing.setItemMeta(glowingMeta);
        inv.setItem(35,glowing);

        //discount
        ItemStack discount = new ItemStack(Material.EMERALD);
        ItemMeta discountMeta = discount.getItemMeta();
        discountMeta.setDisplayName(ChatColor.WHITE + "Change discount options");
        discount.setItemMeta(discountMeta);
        inv.setItem(26,discount);


        //kill
        ItemStack kill = new ItemStack(Material.BARRIER);
        ItemMeta killMeta = kill.getItemMeta();
        killMeta.setDisplayName(ChatColor.DARK_RED + "KILL VILLAGER");
        kill.setItemMeta(killMeta);
        inv.setItem(8,kill);

        //invulnerable
        ItemStack invulnerable = new ItemStack(Material.TNT);
        ItemMeta invulnerableMeta = invulnerable.getItemMeta();
        invulnerableMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Change of insensitivity");
        invulnerable.setItemMeta(invulnerableMeta);
        inv.setItem(17,invulnerable);




        player.openInventory(inv);
    }



}
