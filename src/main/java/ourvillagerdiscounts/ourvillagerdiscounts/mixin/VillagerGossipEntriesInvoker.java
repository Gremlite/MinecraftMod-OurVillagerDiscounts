package ourvillagerdiscounts.ourvillagerdiscounts.mixin;

import net.minecraft.village.VillagerGossips;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.stream.Stream;

@Mixin(VillagerGossips.class)
public interface VillagerGossipEntriesInvoker {
    @Invoker("entries")
    Stream<VillagerGossips.GossipEntry> invokeEntries();
}
