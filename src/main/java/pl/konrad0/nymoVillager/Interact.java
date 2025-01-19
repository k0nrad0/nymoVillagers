package pl.konrad0.nymoVillager;

import com.sun.tools.javac.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import pl.konrad0.nymoVillager.commands.SpawnVillager;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Interact implements Listener {

    public Entity entity;
    public NymoVillager main;
    public Interact(NymoVillager main){
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){

        Player player = e.getPlayer();
        entity = e.getRightClicked();
        if(e.getRightClicked().getType() == EntityType.VILLAGER){
            if(Objects.equals(e.getRightClicked().getCustomName(), ChatColor.RED + "Trader")) {

                NamespacedKey namespacedKey = new NamespacedKey(main,"discount");
                boolean discount = entity.getPersistentDataContainer().get(namespacedKey, PersistentDataType.BOOLEAN);
                if(!discount){
                    if(player.hasPotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE)){
                        player.removePotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE);
                    }

//                    Villager sprzedawca = (Villager) entity;
//
//                    List<MerchantRecipe> newRecipes = new ArrayList<>();
//                    List<MerchantRecipe> recipes = sprzedawca.getRecipes();
//
//                    for (MerchantRecipe recipe : recipes) {
//                        // Set the setPriceMultiplier to 1
//
//                        System.out.println(recipe.getSpecialPrice());
//                        System.out.println(recipe.getPriceMultiplier());
//
//                        newRecipes.add(recipe);
//
//                    }
//                    sprzedawca.setRecipes(newRecipes);

                }


                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                ItemMeta itemMeta = itemInHand.getItemMeta();
                int enL = 0;


                if (itemMeta != null && itemMeta.hasEnchant(Enchantment.UNBREAKING)) {
                    enL = Objects.requireNonNull(itemMeta).getEnchantLevel(Enchantment.UNBREAKING);
                }

                if (itemInHand.getType() == Material.STICK && enL == 10 && itemMeta.getDisplayName().equals(SpawnVillager.configuratorName)) {
                    ConfigMenu menu = new ConfigMenu(player,e.getRightClicked());
                    e.setCancelled(true);


                }

//                player.sendMessage("To jest villager sprzdawca");
            }else{
                boolean disableVillagers = main.getConfig().getBoolean("disableVillagers");
                boolean killVillagers = main.getConfig().getBoolean("killVillagers");
                if(disableVillagers){
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Sorry, this doesnt work :(");
                }
                if(killVillagers){
                    e.getRightClicked().remove();
                    player.sendMessage(ChatColor.RED + "Sorry, this doesnt work :(");
                }


            }

        }
    }

    @EventHandler
    public void onClick (InventoryClickEvent e){

        Player player = (Player) e.getWhoClicked();

        if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.RED.toString() + ChatColor.BOLD + "Trader configuration")
                && e.getCurrentItem() != null){

            Villager sprzedawca = (Villager) entity;

            boolean exit = false;
            switch (e.getRawSlot()){
                case 8:
                    sprzedawca.remove();
                    exit = true;
                    break;
                case 17:
                    sprzedawca.setInvulnerable(!sprzedawca.isInvulnerable());
                    if(sprzedawca.isInvulnerable()){
                        player.sendMessage (ChatColor.LIGHT_PURPLE + "Villager is now invulnerable");
                    }else{
                        player.sendMessage (ChatColor.LIGHT_PURPLE + "Villager is now not invulnerable");
                    }
                    exit = true;
                    break;
                case 26:
                    NamespacedKey namespacedKey = new NamespacedKey(main,"discount");
                    boolean discount = !(entity.getPersistentDataContainer().get(namespacedKey, PersistentDataType.BOOLEAN));
                    sprzedawca.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BOOLEAN, discount);
                    if(discount){

                        player.sendMessage (ChatColor.YELLOW + "Villager is now not removing anything");
                    }else{
                        player.sendMessage (ChatColor.YELLOW + "Villager is now removing HeroOfTheVillage effect from the player");
                    }
                    exit= true;
                    break;


                case 35:
                    sprzedawca.setGlowing(!sprzedawca.isGlowing());
                    if(sprzedawca.isGlowing()){
                        player.sendMessage (ChatColor.YELLOW + "Villager is now glowing");
                    }else{
                        player.sendMessage (ChatColor.YELLOW + "Villager is now not glowing");
                    }
                    exit = true;
                    break;
                case 44:
                    exit = true;
                    break;
                case 53:
                    Inventory inv = player.getOpenInventory().getTopInventory();
                    List<MerchantRecipe> trades = new ArrayList<>();

                    //odczytywanie i tworzenie receptur 1-6
                    for (int i = 0; i < 6; i++) {

                        int a = i*9+2;
                        //czy istnieje wynik receptury
                        if(inv.getItem(i*9+2) != null){

                            ItemStack item1 = null,item2 = null,result;
                            if(inv.getItem(i*9) != null){item1 = inv.getItem(i*9);}
                            if(inv.getItem(i*9+1) != null){item2 = inv.getItem(i*9+1);}
                            result = inv.getItem(i*9+2);



                            MerchantRecipe trade = new MerchantRecipe(result,10000000);

                            if(item1 != null){trade.addIngredient(item1);}
                            if(item2 != null){trade.addIngredient(item2);}
                            if(item1 == null && item2 == null){
                                trade.addIngredient(new ItemStack(Material.AIR));
                            }


                            trades.add(trade);

                        }
                    }
                    //odczytywanie i tworzenie receptur 7-12
                    for (int i = 0; i < 6; i++) {

                        int a = i*9+6;
                        //czy istnieje wynik receptury
                        if(inv.getItem(i*9+6) != null){

                            ItemStack item1 = null,item2 = null,result;
                            if(inv.getItem(i*9+4) != null){item1 = inv.getItem(i*9+4);}
                            if(inv.getItem(i*9+5) != null){item2 = inv.getItem(i*9+5);}
                            result = inv.getItem(i*9+6);



                            MerchantRecipe trade = new MerchantRecipe(result,10000000);

                            if(item1 != null){trade.addIngredient(item1);}
                            if(item2 != null){trade.addIngredient(item2);}
                            if(item1 == null && item2 == null){
                                trade.addIngredient(new ItemStack(Material.AIR));
                            }


                            trades.add(trade);

                        }else {
                        }
                    }

                    sprzedawca.setRecipes(trades);
                    exit = true;

                    break;
                case 3,7,12,21,30,39,48,16,25,34,43,52:
                    e.setCancelled(true);
                    break;
                default:
                    return;
            }


            if(exit) {
                player.closeInventory();
            }

        }


    }



}
