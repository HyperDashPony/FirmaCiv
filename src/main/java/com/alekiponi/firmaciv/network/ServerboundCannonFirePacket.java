package com.alekiponi.firmaciv.network;

import com.alekiponi.firmaciv.common.entity.CannonEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.AbstractSwitchEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ServerboundCannonFirePacket implements Packet<ServerGamePacketListener> {
    private final int entityID;

    public ServerboundCannonFirePacket(boolean switched, int entityID) {
        this.entityID = entityID;
    }

    public ServerboundCannonFirePacket(FriendlyByteBuf buffer) {
        this.entityID = buffer.readInt();
    }

    public void encoder(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entityID);
    }

    public static ServerboundCannonFirePacket decoder(FriendlyByteBuf buffer) {
        return new ServerboundCannonFirePacket(buffer);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Entity entity = context.get().getSender().level().getEntity(this.entityID);
            ServerPlayer serverPlayer = context.get().getSender();
            if(entity instanceof CannonEntity) {
                assert serverPlayer != null;
                if (entity.distanceTo(serverPlayer) < 10) {
                    ((CannonEntity) entity).fire();
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
