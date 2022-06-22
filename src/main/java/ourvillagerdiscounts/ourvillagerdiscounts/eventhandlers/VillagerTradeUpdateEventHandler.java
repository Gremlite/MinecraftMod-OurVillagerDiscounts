package ourvillagerdiscounts.ourvillagerdiscounts.eventhandlers;

import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.village.GossipManager;
import net.minecraft.village.GossipType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ourvillagerdiscounts.ourvillagerdiscounts.event.VillagerInteractEvent;

import java.util.Comparator;

/**
 * Updates villager trades for a player whenever the player interacts with a villager.
 */
@Mod.EventBusSubscriber
public class VillagerTradeUpdateEventHandler {
    @SubscribeEvent
    public static void updateVillagerTrades(VillagerInteractEvent event) {
        final VillagerEntity villager = event.getVillager();
        final PlayerEntity player = event.getPlayer();

        final VillagerData data = villager.getVillagerData();
        final VillagerProfession profession = data.getProfession();

        if (!profession.equals(VillagerProfession.NONE) && !profession.equals(VillagerProfession.NITWIT)) {
            final GossipManager gossip = villager.getGossip();
            gossip.getGossipEntries()
                    .filter(a -> a.type.equals(GossipType.MAJOR_POSITIVE))
                    .max(Comparator.comparingInt(a -> a.value))
                    .ifPresent(maxEntry -> {
                        final int majorPositiveGossipWeighted = maxEntry.weightedValue();
                        final int currentMajorPositiveGossipWeighted = gossip.getReputation(player.getUniqueID(), g -> g.equals(GossipType.MAJOR_POSITIVE));

                        if (majorPositiveGossipWeighted > currentMajorPositiveGossipWeighted) {
                            final ListNBT list = new ListNBT();
                            GossipManager.GossipEntry entry = new GossipManager.GossipEntry(player.getUniqueID(), GossipType.MAJOR_POSITIVE, maxEntry.value);
                            list.add(entry.write(NBTDynamicOps.INSTANCE).getValue());
                            villager.setGossips(list);
                        }
                    });
        }
    }
}
