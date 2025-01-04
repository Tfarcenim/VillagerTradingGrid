package tfar.villagertradinggrid.client;

import net.minecraft.client.gui.screens.MenuScreens;
import tfar.villagertradinggrid.Init;
import tfar.villagertradinggrid.VillagerTradingGridMenu;

public class ModClient {
    public static void screens() {
        MenuScreens.register(Init.VILLAGER_TRADING_GRID, VillagerTradingGridScreen::new);
    }
}
