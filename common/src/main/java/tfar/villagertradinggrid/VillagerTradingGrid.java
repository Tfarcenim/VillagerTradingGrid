package tfar.villagertradinggrid;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.villagertradinggrid.mixin.MerchantMixin;
import tfar.villagertradinggrid.network.PacketHandler;
import tfar.villagertradinggrid.network.client.S2CTradingGridPacket;
import tfar.villagertradinggrid.platform.Services;

import java.util.OptionalInt;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class VillagerTradingGrid {

    public static final String MOD_ID = "villagertradinggrid";
    public static final String MOD_NAME = "VillagerTradingGrid";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
        PacketHandler.registerPackets();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID,path);
    }

    public static void onAttemptOpenTradingMenu(Merchant merchant, Player pPlayer, Component pDisplayName, int pLevel) {
        OptionalInt optionalint = pPlayer.openMenu(new SimpleMenuProvider((p_45298_, p_45299_, p_45300_) -> new VillagerTradingGridMenu(p_45298_, p_45299_, merchant), pDisplayName));
        if (optionalint.isPresent()) {
            MerchantOffers merchantoffers = merchant.getOffers();
            if (!merchantoffers.isEmpty()) {
                pPlayer.sendMerchantOffers(optionalint.getAsInt(), merchantoffers, pLevel, merchant.getVillagerXp(), merchant.showProgressBar(), merchant.canRestock());
            }
        }
    }
}