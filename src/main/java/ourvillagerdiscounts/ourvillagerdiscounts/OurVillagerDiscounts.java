package ourvillagerdiscounts.ourvillagerdiscounts;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ourvillagerdiscounts.ourvillagerdiscounts.event.VillagerInteractEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("our-villager-discounts")
public class OurVillagerDiscounts {
    private static final Logger LOGGER = LogManager.getLogger();

    public OurVillagerDiscounts() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract e) {
        Entity target = e.getTarget();
        if (target instanceof VillagerEntity) {
            MinecraftForge.EVENT_BUS.post(new VillagerInteractEvent(e.getPlayer(), (VillagerEntity)target));
        }
    }
}
