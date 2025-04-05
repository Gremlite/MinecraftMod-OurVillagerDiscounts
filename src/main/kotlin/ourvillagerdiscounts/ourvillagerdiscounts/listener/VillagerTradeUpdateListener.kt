package ourvillagerdiscounts.ourvillagerdiscounts.listener

import java.util.Comparator
import java.util.stream.Stream
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.village.VillagerData
import net.minecraft.village.VillagerGossipType
import net.minecraft.village.VillagerGossips
import net.minecraft.village.VillagerGossips.GossipEntry
import net.minecraft.village.VillagerProfession
import org.apache.logging.log4j.LogManager
import ourvillagerdiscounts.ourvillagerdiscounts.callback.VillagerInteractCallback
import ourvillagerdiscounts.ourvillagerdiscounts.mixin.VillagerGossipEntriesInvoker

class VillagerTradeUpdateListener : VillagerInteractCallback {
    override fun interact(player: PlayerEntity, villager: VillagerEntity): ActionResult {
        val data: VillagerData = villager.villagerData
        val profession = data.profession
        if (profession != VillagerProfession.NONE && profession != VillagerProfession.NITWIT) {
            val gossip: VillagerGossips = villager.gossip
            val gossipAccessor: Stream<GossipEntry> = (gossip as VillagerGossipEntriesInvoker).invokeEntries()
            gossipAccessor
                .filter { a -> a.type == VillagerGossipType.MAJOR_POSITIVE }
                .max(Comparator.comparingInt { a -> a.value })
                .ifPresent { maxEntry: GossipEntry ->
                    val majorPositiveGossipWeighted= maxEntry.getValue()
                    val currentMajorPositiveGossipWeighted = gossip.getReputationFor(
                        player.uuid
                    ) { g -> g == VillagerGossipType.MAJOR_POSITIVE }
                    if (majorPositiveGossipWeighted > currentMajorPositiveGossipWeighted) {
                        val majorPositiveGossipUnweighted = majorPositiveGossipWeighted / maxEntry.type.multiplier
                        villager.readGossipData(VillagerGossips().apply {
                            this.startGossip(player.uuid, VillagerGossipType.MAJOR_POSITIVE, majorPositiveGossipUnweighted)
                        })
                    }
                }
        }
        return ActionResult.PASS
    }

    companion object {
        private val LOG = LogManager.getLogger(
            VillagerTradeUpdateListener::class.java
        )
    }
}