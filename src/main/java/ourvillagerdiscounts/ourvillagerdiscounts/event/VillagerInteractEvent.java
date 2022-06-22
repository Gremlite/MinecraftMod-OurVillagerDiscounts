package ourvillagerdiscounts.ourvillagerdiscounts.event;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

/**
 * An event that fires whenever a player interacts with a villager.
 */
public class VillagerInteractEvent extends Event {
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
