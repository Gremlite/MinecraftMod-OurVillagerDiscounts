package ourvillagerdiscounts.ourvillagerdiscounts

import net.fabricmc.api.ModInitializer
import ourvillagerdiscounts.ourvillagerdiscounts.callback.VillagerInteractCallback
import ourvillagerdiscounts.ourvillagerdiscounts.listener.VillagerTradeUpdateListener

@Suppress("UNUSED")
object OurVillagerDiscounts : ModInitializer {
    private const val MOD_ID = "our_villager_discounts"

    override fun onInitialize() {
        VillagerInteractCallback.EVENT.register(VillagerTradeUpdateListener())
    }
}