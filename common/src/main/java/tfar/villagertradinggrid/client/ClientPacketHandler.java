package tfar.villagertradinggrid.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.trading.MerchantOffers;
import tfar.villagertradinggrid.VillagerTradingGridMenu;
import tfar.villagertradinggrid.network.client.S2CTradingGridPacket;

public class ClientPacketHandler {
    public static void handleMerchantOffers(S2CTradingGridPacket s2CTradingGridPacket) {
        AbstractContainerMenu abstractcontainermenu = Minecraft.getInstance().player.containerMenu;
        if (s2CTradingGridPacket.getContainerId() == abstractcontainermenu.containerId && abstractcontainermenu instanceof VillagerTradingGridMenu merchantmenu) {
            merchantmenu.setClientsideOffers(new MerchantOffers(s2CTradingGridPacket.getOffers().createTag()));
            merchantmenu.setXp(s2CTradingGridPacket.getVillagerXp());
            merchantmenu.setMerchantLevel(s2CTradingGridPacket.getVillagerLevel());
            merchantmenu.setShowProgressBar(s2CTradingGridPacket.showProgress());
            merchantmenu.setCanRestock(s2CTradingGridPacket.canRestock());
        }
    }
}
