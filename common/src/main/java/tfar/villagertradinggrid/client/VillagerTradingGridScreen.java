package tfar.villagertradinggrid.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tfar.villagertradinggrid.VillagerTradingGrid;
import tfar.villagertradinggrid.VillagerTradingGridMenu;

public class VillagerTradingGridScreen extends AbstractContainerScreen<VillagerTradingGridMenu> {
    private static final ResourceLocation VILLAGER_LOCATION = VillagerTradingGrid.id("textures/gui/villager3.png");


    public VillagerTradingGridScreen(VillagerTradingGridMenu menu, Inventory $$1, Component $$2) {
        super(menu, $$1, $$2);
        this.imageWidth = 276;
        this.inventoryLabelX = 102;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(VILLAGER_LOCATION, i, j, 0, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 512, 256);
        InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics, this.leftPos + 188, this.topPos + 57, 20, this.leftPos + 188 - pMouseX, this.topPos + 57 - 30 - pMouseY, this.minecraft.player);
    }


    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);

    }
}
