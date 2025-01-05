package tfar.villagertradinggrid;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;
import tfar.villagertradinggrid.network.client.S2CTradingGridPacket;
import tfar.villagertradinggrid.platform.Services;

import java.util.OptionalInt;

@Mod(VillagerTradingGrid.MOD_ID)
public class VillagerTradingGridForge {

    public VillagerTradingGridForge() {

        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::register);
        if (FMLEnvironment.dist.isClient()) {
            ModClientForge.init(bus);
        }
        // Use Forge to bootstrap the Common mod.
        VillagerTradingGrid.init();

    }

    void register(RegisterEvent event) {
        if ((Object) event.getForgeRegistry() == ForgeRegistries.MENU_TYPES) {
            event.register(Registries.MENU, VillagerTradingGrid.id("menu"), () -> Init.VILLAGER_TRADING_GRID);
        }
    }
}