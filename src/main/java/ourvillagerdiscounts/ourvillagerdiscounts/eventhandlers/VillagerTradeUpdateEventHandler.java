package ourvillagerdiscounts.ourvillagerdiscounts.eventhandlers;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
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
        final Villager villager = event.getVillager();
        final Player player = event.getPlayer();

        final VillagerData data = villager.getVillagerData();
        final VillagerProfession profession = data.getProfession();

        if (!profession.equals(VillagerProfession.NONE) && !profession.equals(VillagerProfession.NITWIT)) {
            final GossipContainer gossip = villager.getGossips();
            gossip.unpack()
                    .filter(a -> a.type.equals(GossipType.MAJOR_POSITIVE))
                    .max(Comparator.comparingInt(a -> a.value))
                    .ifPresent(maxEntry -> {
                        final int majorPositiveGossipWeighted = maxEntry.weightedValue();
                        final int currentMajorPositiveGossipWeighted = gossip.getReputation(player.getUUID(), g -> g.equals(GossipType.MAJOR_POSITIVE));

                        if (majorPositiveGossipWeighted > currentMajorPositiveGossipWeighted) {
                            final ListTag list = new ListTag();
                            GossipContainer.GossipEntry entry = new GossipContainer.GossipEntry(player.getUUID(), GossipType.MAJOR_POSITIVE, maxEntry.value);
                            list.add(entry.store(NbtOps.INSTANCE).getValue());
                            villager.setGossips(list);
                        }
                    });
        }
    }
}
