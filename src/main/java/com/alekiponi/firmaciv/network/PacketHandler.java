package com.alekiponi.firmaciv.network;

import com.alekiponi.firmaciv.Firmaciv;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE;

    static {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Firmaciv.MOD_ID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
    }

    public static void init(){
        int id = 0;
        INSTANCE.messageBuilder(ServerboundCompartmentInputPacket.class, id)
                .encoder(ServerboundCompartmentInputPacket::encoder)
                .decoder(ServerboundCompartmentInputPacket::decoder)
                .consumerMainThread(ServerboundCompartmentInputPacket::handle)
                .add();
        id++;
        INSTANCE.messageBuilder(ServerboundSwitchEntityPacket.class, id)
                .encoder(ServerboundSwitchEntityPacket::encoder)
                .decoder(ServerboundSwitchEntityPacket::decoder)
                .consumerMainThread(ServerboundSwitchEntityPacket::handle)
                .add();
        id++;
        INSTANCE.messageBuilder(ServerBoundSloopPacket.class, id)
                .encoder(ServerBoundSloopPacket::encoder)
                .decoder(ServerBoundSloopPacket::decoder)
                .consumerMainThread(ServerBoundSloopPacket::handle)
                .add();
    }

    public static void clientSendPacket(Object msg){
        INSTANCE.sendToServer(msg);
    }


}
