package pl.konrad0.nymoVillager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import pl.konrad0.nymoVillager.commands.SpawnVillager;

public final class NymoVillager extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[nymoVillager] ----- Plugin enabled -----");
        getCommand("trader").setExecutor(new SpawnVillager(this));
        Bukkit.getPluginManager().registerEvents(new Interact(this),this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();



        //name of stick configurator
        SpawnVillager.configuratorName = ChatColor.WHITE + "Seller configurator";


    }

    @Override
    public void onDisable() {
        System.out.println("[nymoVillager] Shutting down");
    }
}
