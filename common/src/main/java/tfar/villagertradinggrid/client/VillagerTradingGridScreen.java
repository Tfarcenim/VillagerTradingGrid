package tfar.villagertradinggrid.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
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
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(VILLAGER_LOCATION, i, j, 0, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 512, 256);
    }
}
