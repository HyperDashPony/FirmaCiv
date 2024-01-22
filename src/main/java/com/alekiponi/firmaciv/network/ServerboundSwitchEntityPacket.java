package com.alekiponi.firmaciv.network;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractSwitchEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ServerboundSwitchEntityPacket implements Packet<ServerGamePacketListener> {
    private final boolean switched;
    private final int entityID;

    public ServerboundSwitchEntityPacket(boolean switched, int entityID) {
        this.switched = switched;
        this.entityID = entityID;
    }

    public ServerboundSwitchEntityPacket(FriendlyByteBuf buffer) {
        this.switched = buffer.readBoolean();
        this.entityID = buffer.readInt();
    }

    public void encoder(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.switched);
        buffer.writeInt(this.entityID);
    }

    public static ServerboundSwitchEntityPacket decoder(FriendlyByteBuf buffer) {
        return new ServerboundSwitchEntityPacket(buffer);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Entity entity = context.get().getSender().level().getEntity(this.entityID);
            ServerPlayer serverPlayer = context.get().getSender();
            if(entity instanceof AbstractSwitchEntity) {
                assert serverPlayer != null;
                if (entity.distanceTo(serverPlayer) < 10) {
                    ((AbstractSwitchEntity) entity).setSwitched(this.switched);
                }
            }

        });
    }


    @Override
    public void handle(@NotNull ServerGamePacketListener supplier) {
    }

    @Override
    public void write(@NotNull FriendlyByteBuf buffer) {
    }
}
