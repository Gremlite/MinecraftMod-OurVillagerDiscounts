package ourvillagerdiscounts.ourvillagerdiscounts.listener

import java.util.Comparator
import java.util.stream.Stream
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.util.ActionResult
import net.minecraft.village.VillageGossipType
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
        val profession: VillagerProfession = data.profession
        if (profession != VillagerProfession.NONE && profession != VillagerProfession.NITWIT) {
            val gossip: VillagerGossips = villager.gossip
            val gossipAccessor: Stream<GossipEntry> = (gossip as VillagerGossipEntriesInvoker).invokeEntries()
            gossipAccessor
                .filter { a -> a.type == VillageGossipType.MAJOR_POSITIVE }
                .max(Comparator.comparingInt { a -> a.value })
                .ifPresent { maxEntry: GossipEntry ->
                    val majorPositiveGossip = maxEntry.value
                    val currentMajorPositiveGossip = gossip.getReputationFor(
                        player.uuid
                    ) { g -> g == VillageGossipType.MAJOR_POSITIVE }
                    if (majorPositiveGossip > currentMajorPositiveGossip) {
                        val list = ListTag()
                        val tag = CompoundTag()
                        tag.putString(TYPE, VillageGossipType.MAJOR_POSITIVE.key)
                        tag.putInt(VALUE, majorPositiveGossip)
                        tag.putUuid(TARGET, player.uuid)
                        list.add(tag)
                        villager.setGossipDataFromTag(list)
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