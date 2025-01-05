package tfar.villagertradinggrid;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.trading.MerchantOffer;

public record TradeTooltip(MerchantOffer offer) implements TooltipComponent {
}
