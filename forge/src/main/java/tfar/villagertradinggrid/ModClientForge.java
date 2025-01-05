package tfar.villagertradinggrid;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tfar.villagertradinggrid.client.ClientTradeTooltip;
import tfar.villagertradinggrid.client.ModClient;
import tfar.villagertradinggrid.client.VillagerTradingGridScreen;

public class ModClientForge {
    public static void init(IEventBus bus) {
        bus.addListener(ModClientForge::setup);
        MinecraftForge.EVENT_BUS.addListener(ModClientForge::tooltip);
        bus.addListener(ModClientForge::tooltipComponents);
    }

    static void setup(FMLClientSetupEvent event) {
        ModClient.screens();
    }

    static void tooltip(RenderTooltipEvent.GatherComponents event) {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof VillagerTradingGridScreen villagerTradingGridScreen) {
            Slot slot = villagerTradingGridScreen.getHoveredSlot();
            if (slot instanceof TradeSlot tradeSlot) {
                MerchantOffer offer = tradeSlot.getBoundOffer();
                if (offer != null) {
                    TradeTooltip tradeTooltip = new TradeTooltip(offer);
                    event.getTooltipElements().add(Either.left(Component.literal("Cost")));
                    event.getTooltipElements().add(Either.right(tradeTooltip));
                }
            }
        }
    }

    static void tooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(TradeTooltip.class, ClientTradeTooltip::new);
    }
}
