package tfar.villagertradinggrid;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class Init {

    public static final MenuType<VillagerTradingGridMenu> VILLAGER_TRADING_GRID = new MenuType<>(VillagerTradingGridMenu::client, FeatureFlags.VANILLA_SET);

}
