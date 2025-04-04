package ourvillagerdiscounts.ourvillagerdiscounts.listener

import java.util.Comparator
import java.util.stream.Stream
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.ActionResult
import net.minecraft.village.VillagerGossipType
import net.minecraft.village.VillagerData
import net.minecraft.village.VillagerGossips
import net.minecraft.village.VillagerGossips.GossipEntry
import net.minecraft.village.VillagerProfession
import org.apache.logging.log4j.LogManager
import ourvillagerdiscounts.ourvillagerdiscounts.callback.VillagerInteractCallback
import ourvillagerdiscounts.ourvillagerdiscounts.mixin.VillagerGossipEntriesInvoker

class VillagerTradeUpdateListener : VillagerInteractCallback {
    override fun interact(player: PlayerEntity, villager: VillagerEntity): ActionResult {
        val data: VillagerData = villager.villagerData
        val profession: RegistryEntry<VillagerProfession>? = data.profession
        if (profession != VillagerProfession.NONE && profession != VillagerProfession.NITWIT) {
            val gossip: VillagerGossips = villager.gossip
            val gossipAccessor: Stream<GossipEntry> = (gossip as VillagerGossipEntriesInvoker).invokeEntries()
            gossipAccessor
                .filter { a -> a.type == VillagerGossipType.MAJOR_POSITIVE }
                .max(Comparator.comparingInt { a -> a.value })
                .ifPresent { maxEntry: GossipEntry ->
                    val majorPositiveGossipWeighted= maxEntry.value
                    val currentMajorPositiveGossipWeighted = gossip.getReputationFor(
                        player.uuid
                    ) { g -> g == VillagerGossipType.MAJOR_POSITIVE }
                    if (majorPositiveGossipWeighted > currentMajorPositiveGossipWeighted) {
                        val majorPositiveGossipUnweighted = majorPositiveGossipWeighted / maxEntry.type.multiplier
                        val newGossips = VillagerGossips()
                        newGossips.startGossip(player.uuid, VillagerGossipType.MAJOR_POSITIVE, majorPositiveGossipUnweighted)
                        villager.readGossipData(newGossips)
                    }
                }
        }
        return ActionResult.PASS
    }

    companion object {
        private val LOG = LogManager.getLogger(
            VillagerTradeUpdateListener::class.java
        )

        private const val TARGET = "Target"
        private const val TYPE = "Type"
        private const val VALUE = "Value"
    }
}