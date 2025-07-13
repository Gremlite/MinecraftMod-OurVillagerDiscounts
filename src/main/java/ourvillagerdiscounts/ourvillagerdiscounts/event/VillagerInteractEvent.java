package ourvillagerdiscounts.ourvillagerdiscounts.event;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.bus.EventBus;
import net.minecraftforge.eventbus.api.event.MutableEvent;

/**
 * An event that fires whenever a player interacts with a villager.
 */
public final class VillagerInteractEvent extends MutableEvent {
    public static final EventBus<VillagerInteractEvent> BUS = EventBus.create(VillagerInteractEvent.class);

    private final Player player;
    private final Villager villager;

    public VillagerInteractEvent(Player player, Villager villager) {
        this.player = player;
        this.villager = villager;
    }

    public Player getPlayer() {
        return player;
    }

    public Villager getVillager() {
        return villager;
    }
}
