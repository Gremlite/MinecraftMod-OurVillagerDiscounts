package ourvillagerdiscounts.ourvillagerdiscounts.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.util.ActionResult
import java.util.function.Function

/**
 * Callback for interacting with a villager.
 * Upon return:
 * - SUCCESS cancels further processing and continues with normal interaction behavior.
 * - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available.
 * - FAIL cancels further processing and does not interact with the villager.
 */
interface VillagerInteractCallback {
    fun interact(player: PlayerEntity, villager: VillagerEntity): ActionResult

    companion object {
        @JvmField
        val EVENT: Event<VillagerInteractCallback> = EventFactory.createArrayBacked(
            VillagerInteractCallback::class.java
        ) { listeners: Array<VillagerInteractCallback> ->
            return@createArrayBacked object : VillagerInteractCallback {
                override fun interact(player: PlayerEntity, villager: VillagerEntity): ActionResult {
                    for (listener in listeners) {
                        val result: ActionResult = listener.interact(player, villager)
                        if (result != ActionResult.PASS) {
                            return result
                        }
                    }
                    return ActionResult.PASS
                }
            }
        }
    }
}