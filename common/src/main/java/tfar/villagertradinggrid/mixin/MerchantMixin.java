package tfar.villagertradinggrid.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.Merchant;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tfar.villagertradinggrid.VillagerTradingGrid;

@Mixin(Merchant.class)
@Debug(export = true)
public interface MerchantMixin {


    /**
     * @author
     * @reason
     */
    @Overwrite
    default void openTradingScreen(Player pPlayer, Component pDisplayName, int pLevel) {
        VillagerTradingGrid.onAttemptOpenTradingMenu((Merchant) this,pPlayer,pDisplayName,pLevel);
    }
}
