package pl.konrad0.nymoVillager.commands;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.konrad0.nymoVillager.NymoVillager;

import java.util.ArrayList;
import java.util.List;


public class SpawnVillager implements CommandExecutor {

    public NymoVillager main;
    public SpawnVillager(NymoVillager main){
        this.main = main;
    }

    public static String configuratorName = "TEMP";
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            Location location = player.getLocation();

            Villager sprzedawca = (Villager) (player.getWorld()).spawnEntity(location, EntityType.VILLAGER);
            player.sendMessage(ChatColor.GREEN + "Well done! You summoned a trader :)");
            sprzedawca.setInvulnerable(true);
            sprzedawca.setGlowing(true);
            sprzedawca.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 6));
            sprzedawca.setCustomName(ChatColor.RED + "Trader");
            sprzedawca.setAI(false);
            NamespacedKey namespacedKey = new NamespacedKey(main,"discount");
            sprzedawca.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BOOLEAN, true);


            ItemStack zelazo = new ItemStack(Material.IRON_INGOT,5);
            MerchantRecipe trade = new MerchantRecipe(zelazo,10000);
            trade.addIngredient(new ItemStack(Material.COPPER_INGOT,5));

            List<MerchantRecipe> newRecipes = new ArrayList<>();
            newRecipes.add(trade);
            sprzedawca.setRecipes(newRecipes);

            ItemStack patyk = new ItemStack(Material.STICK);
            ItemMeta patykMeta = patyk.getItemMeta();
            patykMeta.setDisplayName(configuratorName);
            patykMeta.addEnchant(Enchantment.UNBREAKING,10,true);
            patyk.setItemMeta(patykMeta);
            player.getInventory().addItem(patyk);






        }

        return false;
    }
}
