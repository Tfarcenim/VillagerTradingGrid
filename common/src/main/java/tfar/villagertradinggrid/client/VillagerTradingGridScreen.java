package tfar.villagertradinggrid.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import tfar.villagertradinggrid.TradeSlot;
import tfar.villagertradinggrid.VillagerTradingGrid;
import tfar.villagertradinggrid.VillagerTradingGridMenu;

import java.util.List;

public class VillagerTradingGridScreen extends AbstractContainerScreen<VillagerTradingGridMenu> {
    private static final ResourceLocation VILLAGER_LOCATION = VillagerTradingGrid.id("textures/gui/villager3.png");


    public VillagerTradingGridScreen(VillagerTradingGridMenu menu, Inventory $$1, Component $$2) {
        super(menu, $$1, $$2);
        this.imageWidth = 276;
        this.inventoryLabelX = 106;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(VILLAGER_LOCATION, i, j, 0, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 512, 256);
        InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics, this.leftPos + 188, this.topPos + 57, 20, this.leftPos + 188 - pMouseX, this.topPos + 57 - 30 - pMouseY, this.minecraft.player);
    }

    @Override
    public void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        super.renderSlot(guiGraphics, slot);
        if (slot instanceof TradeSlot tradeSlot) {
            ItemStack stack = slot.getItem();
            if (!stack.isEmpty()) {
                MerchantOffer offer = tradeSlot.getBoundOffer();
                if (offer != null && offer.isOutOfStock()) {
                    int x = slot.x;
                    int y = slot.y;
                    guiGraphics.blit(VILLAGER_LOCATION, x, y, 300, 318, 3, 15, 15, 512, 256);
                }
            }
        }
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        if (this.menu.showProgressBar()) {
            this.renderProgressBar(pGuiGraphics, leftPos, topPos);
        }

        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderProgressBar(GuiGraphics pGuiGraphics, int pPosX, int pPosY) {
        int i = this.menu.getTraderLevel();
        int j = this.menu.getTraderXp();
        if (i < 5) {
            pGuiGraphics.blit(VILLAGER_LOCATION, pPosX + 136, pPosY + 6, 0, 0.0F, 186.0F, 102, 5, 512, 256);
            int k = VillagerData.getMinXpPerLevel(i);
            if (j >= k && VillagerData.canLevelUp(i)) {
                int l = 100;
                float f = 100.0F / (float)(VillagerData.getMaxXpPerLevel(i) - k);
                int i1 = Math.min(Mth.floor(f * (float)(j - k)), 100);
                pGuiGraphics.blit(VILLAGER_LOCATION, pPosX + 136, pPosY + 6, 0, 0.0F, 191.0F, i1 + 1, 5, 512, 256);
                int j1 = this.menu.getFutureTraderXp();
                if (j1 > 0) {
                    int k1 = Math.min(Mth.floor((float)j1 * f), 100 - i1);
                    pGuiGraphics.blit(VILLAGER_LOCATION, pPosX + 136 + i1 + 1, pPosY + 6 + 1, 0, 2.0F, 182.0F, k1, 3, 512, 256);
                }

            }
        }
    }

    @Override
    protected List<Component> getTooltipFromContainerItem(ItemStack $$0) {
        return super.getTooltipFromContainerItem($$0);
    }

    private static final Component LEVEL_SEPARATOR = Component.literal(" - ");

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040, false);

        int i = this.menu.getTraderLevel();
        if (i > 0 && i <= 5 && this.menu.showProgressBar()) {
            Component component = this.title.copy().append(LEVEL_SEPARATOR).append(Component.translatable("merchant.level." + i));
            int j = this.font.width(component);
            int k = 49 + this.imageWidth / 2 - j / 2;
            pGuiGraphics.drawString(this.font, component, titleLabelX, 6, 0x404040, false);
        } else {
            pGuiGraphics.drawString(this.font, this.title, 49 + this.imageWidth / 2 - this.font.width(this.title) / 2, 6, 0x404040, false);
        }
    }

    public Slot getHoveredSlot() {
        return hoveredSlot;
    }
}
