package dev.nanoflux.networks.event;

import dev.nanoflux.networks.component.ComponentType;
import dev.nanoflux.networks.utils.NamespaceUtils;
import dev.nanoflux.config.lang.LanguageController;
import dev.nanoflux.networks.Main;
import dev.nanoflux.networks.Network;
import dev.nanoflux.networks.Manager;
import dev.nanoflux.networks.Config;
import dev.nanoflux.networks.utils.DoubleChestUtils;
import dev.nanoflux.networks.utils.BlockLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static dev.nanoflux.networks.Main.manager;

public class BlockPlaceListener implements Listener {

    private final Manager net;
    private final Config config;
    private final LanguageController lang;

    private final DoubleChestUtils dcd;

    public BlockPlaceListener (Main main, DoubleChestUtils doubleChestDisconnecter) {
        this.net = main.getNetworkManager();
        this.config = main.getConfiguration();
        this.lang = main.getLanguage();

        dcd = doubleChestDisconnecter;
    }

    @EventHandler(priority= EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.isCancelled()) {

            Player p = event.getPlayer();
            BlockLocation pos = new BlockLocation(event.getBlock());
            Network network = net.selection(p);

            ItemStack item = event.getItemInHand().clone();
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();

            ComponentType type = ComponentType.get(container.get(NamespaceUtils.COMPONENT.key(), PersistentDataType.STRING));

            if (type == null) return;

            if (config.checkLocation(pos, type)) {

                if (network == null) {
                    lang.message(p, "select.noselection");
                    return;
                }

                manager.createComponent(network, event.getBlock().getType(), type, pos, container);
                lang.message(p, "component."+type.tag+".add", network.name(), pos.toString());

            }
        }
    }
}