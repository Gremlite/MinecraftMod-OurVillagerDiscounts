package ourvillagerdiscounts.ourvillagerdiscounts.mixin

import org.spongepowered.asm.mixin.Mixin
import net.minecraft.entity.passive.VillagerEntity
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.At
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import net.minecraft.util.ActionResult
import ourvillagerdiscounts.ourvillagerdiscounts.callback.VillagerInteractCallback

@Mixin(VillagerEntity::class)
class VillagerInteractMixin {
    @Inject(at = [At(value = "INVOKE")], method = ["interactMob"], cancellable = true)
    private fun onVillagerInteract(player: PlayerEntity, hand: Hand, info: CallbackInfoReturnable<Boolean>) {
        val result = VillagerInteractCallback.EVENT.invoker().interact(player, this as VillagerEntity)
        if (result == ActionResult.FAIL) {
            info.cancel()
        }
    }
}