package tfar.villagertradinggrid.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tfar.villagertradinggrid.network.client.S2CTradingGridPacket;
import tfar.villagertradinggrid.platform.Services;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    /**
     * @author
     * @reason
     */
    @Overwrite
    public void sendMerchantOffers(int pContainerId, MerchantOffers pOffers, int pLevel, int pXp, boolean pShowProgress, boolean pCanRestock) {
        Services.PLATFORM.sendToClient(new S2CTradingGridPacket(pContainerId, pOffers, pLevel, pXp, pShowProgress, pCanRestock),(ServerPlayer) (Object)this);
    }
}
