package tfar.villagertradinggrid.network.server;

import net.minecraft.server.level.ServerPlayer;
import tfar.villagertradinggrid.network.ModPacket;

public interface C2SModPacket extends ModPacket {

    void handleServer(ServerPlayer player);

}
