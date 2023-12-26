package com.alekiponi.firmaciv.network;

import com.alekiponi.firmaciv.Firmaciv;
import net.dries007.tfc.util.Helpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        INSTANCE.messageBuilder(ServerboundCompartmentInputPacket.class, id = id++)
                .encoder(ServerboundCompartmentInputPacket::encoder)
                .decoder(ServerboundCompartmentInputPacket::decoder)
                .consumerMainThread(ServerboundCompartmentInputPacket::handle)
                .add();
    }

    public static void clientSendPacket(Object msg){
        INSTANCE.sendToServer(msg);
    }


}
