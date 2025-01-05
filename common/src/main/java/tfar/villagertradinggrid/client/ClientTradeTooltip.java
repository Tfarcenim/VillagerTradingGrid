package tfar.villagertradinggrid.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import tfar.villagertradinggrid.TradeTooltip;

public class ClientTradeTooltip implements ClientTooltipComponent {

    private final MerchantOffer offer;
    public ClientTradeTooltip(TradeTooltip tradeTooltip) {
        this.offer = tradeTooltip.offer();
    }

    @Override
    public void renderImage(Font font, int pX, int pY, GuiGraphics pGuiGraphics) {
        ItemStack stackA = offer.getCostA();
        ItemStack stackB = offer.getCostB();

        pGuiGraphics.renderFakeItem(stackA, pX, pY);
        pGuiGraphics.renderItemDecorations(font, stackA, pX, pY);

        pGuiGraphics.renderFakeItem(stackB, pX+36, pY);
        pGuiGraphics.renderItemDecorations(font, stackB, pX+36, pY);
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public int getWidth(Font font) {
        return 80;
    }
}
