package tfar.villagertradinggrid.network;

import net.minecraft.resources.ResourceLocation;
import tfar.villagertradinggrid.VillagerTradingGrid;
import tfar.villagertradinggrid.network.client.S2CTradingGridPacket;
import tfar.villagertradinggrid.platform.Services;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
       Services.PLATFORM.registerClientPacket(S2CTradingGridPacket.class, S2CTradingGridPacket::new);

    }

    public static ResourceLocation packet(Class<?> clazz) {
        return VillagerTradingGrid.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
