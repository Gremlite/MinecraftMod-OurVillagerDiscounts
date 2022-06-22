package ourvillagerdiscounts.ourvillagerdiscounts.event;

import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

/**
 * An event that fires whenever a player interacts with a villager.
 */
public class VillagerInteractEvent extends Event {
    private final PlayerEntity player;
    private final VillagerEntity villager;

    public VillagerInteractEvent(PlayerEntity player, VillagerEntity villager) {
        this.player = player;
        this.villager = villager;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public VillagerEntity getVillager() {
        return villager;
    }
}
