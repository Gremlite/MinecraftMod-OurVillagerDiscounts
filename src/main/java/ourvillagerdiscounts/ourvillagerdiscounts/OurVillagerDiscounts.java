package ourvillagerdiscounts.ourvillagerdiscounts;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ourvillagerdiscounts.ourvillagerdiscounts.event.VillagerInteractEvent;

import java.lang.invoke.MethodHandles;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ourvillagerdiscounts")
public class OurVillagerDiscounts {
    private static final Logger LOGGER = LogManager.getLogger();

    public OurVillagerDiscounts() {
        PlayerInteractEvent.EntityInteract.BUS.addListener(e -> {
            Entity target = e.getTarget();
            if (target instanceof Villager) {
                VillagerInteractEvent.BUS.post(new VillagerInteractEvent(e.getEntity(), (Villager)target));
            }
        });
    }
}
