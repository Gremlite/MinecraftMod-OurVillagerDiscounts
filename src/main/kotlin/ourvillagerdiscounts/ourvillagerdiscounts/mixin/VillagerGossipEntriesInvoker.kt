package ourvillagerdiscounts.ourvillagerdiscounts.mixin

import org.spongepowered.asm.mixin.Mixin
import net.minecraft.village.VillagerGossips
import net.minecraft.village.VillagerGossips.GossipEntry
import org.spongepowered.asm.mixin.gen.Invoker
import java.util.stream.Stream

@Mixin(VillagerGossips::class)
interface VillagerGossipEntriesInvoker {
    @Invoker("entries")
    fun invokeEntries(): Stream<GossipEntry>
}