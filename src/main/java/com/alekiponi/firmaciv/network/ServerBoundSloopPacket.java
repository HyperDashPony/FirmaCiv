package com.alekiponi.firmaciv.network;

import com.alekiponi.firmaciv.common.entity.vehicle.SloopEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ServerBoundSloopPacket implements Packet<ServerGamePacketListener> {
    private final float sheetLength;
    private final float rudderAngle;
    private final int entityID;

    public ServerBoundSloopPacket(float sheetLength, float rudderAngle,int entityID){
        this.sheetLength = sheetLength;
        this.rudderAngle = rudderAngle;
        this.entityID = entityID;
    }

    public ServerBoundSloopPacket(FriendlyByteBuf buffer){
        this.sheetLength = buffer.readFloat();
        this.rudderAngle = buffer.readFloat();
        this.entityID = buffer.readInt();
    }

    public void encoder(FriendlyByteBuf buffer){
        buffer.writeFloat(this.sheetLength);
        buffer.writeFloat(this.rudderAngle);
        buffer.writeInt(this.entityID);
    }

    public static ServerBoundSloopPacket decoder(FriendlyByteBuf buffer){
        return new ServerBoundSloopPacket(buffer);
    }

    public void handle(Supplier<NetworkEvent.Context> context){
        context.get().enqueueWork(() -> {
            Entity entity = context.get().getSender().level().getEntity(this.entityID);
            ServerPlayer player = context.get().getSender();
            if(entity instanceof SloopEntity sloop){
                assert player != null;
                if (player.distanceTo(sloop) < 5) {
                    sloop.setMainsheetLength(this.sheetLength);
                    sloop.setRudderRotation(this.rudderAngle);
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
