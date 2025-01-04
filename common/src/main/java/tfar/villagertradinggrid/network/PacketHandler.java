package tfar.villagertradinggrid.network;

import net.minecraft.resources.ResourceLocation;
import tfar.villagertradinggrid.VillagerTradingGrid;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
      //  Services.PLATFORM.registerClientPacket(S2CLevelUpInfoPacket.class, S2CLevelUpInfoPacket::new);

    }

    public static ResourceLocation packet(Class<?> clazz) {
        return VillagerTradingGrid.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
