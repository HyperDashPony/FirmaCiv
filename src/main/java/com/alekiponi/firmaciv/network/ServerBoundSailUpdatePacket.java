package com.alekiponi.firmaciv.network;

import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ServerBoundSailUpdatePacket implements Packet<ServerGamePacketListener> {
    private final float sheetLength;
    private final int entityID;

    public ServerBoundSailUpdatePacket(float sheetLength, int entityID){
        this.sheetLength = sheetLength;
        this.entityID = entityID;
    }

    public ServerBoundSailUpdatePacket(FriendlyByteBuf buffer){
        this.sheetLength = buffer.readFloat();
        this.entityID = buffer.readInt();
    }

    public void encoder(FriendlyByteBuf buffer){
        buffer.writeFloat(this.sheetLength);
        buffer.writeInt(entityID);
    }

    public static ServerBoundSailUpdatePacket decoder(FriendlyByteBuf buffer){
        return new ServerBoundSailUpdatePacket(buffer);
    }

    public void handle(Supplier<NetworkEvent.Context> context){
        context.get().enqueueWork(() -> {
            Entity entity = context.get().getSender().level().getEntity(this.entityID);
            ServerPlayer player = context.get().getSender();
            if(entity instanceof SloopEntity sloop){
                assert player != null;
                if (player.distanceTo(sloop) < 5) {
                    sloop.setMainsheetLength(this.sheetLength);
                }
            }
        });
    }


    @Override
    public void write(@NotNull FriendlyByteBuf pBuffer) {

    }

    @Override
    public void handle(@NotNull ServerGamePacketListener pHandler) {

    }
}
