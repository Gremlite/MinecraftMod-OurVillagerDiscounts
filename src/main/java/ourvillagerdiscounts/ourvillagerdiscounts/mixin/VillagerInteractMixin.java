package ourvillagerdiscounts.ourvillagerdiscounts.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ourvillagerdiscounts.ourvillagerdiscounts.callback.VillagerInteractCallback;

@Mixin(VillagerEntity.class)
public class VillagerInteractMixin {
    @Inject(at = @At(value = "INVOKE"), method="interactMob", cancellable = true)
    private void onVillagerInteract(final PlayerEntity player, final Hand hand, final CallbackInfoReturnable<Boolean> info) {
        ActionResult result = VillagerInteractCallback.EVENT.invoker().interact(player, (VillagerEntity)(Object)this);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}