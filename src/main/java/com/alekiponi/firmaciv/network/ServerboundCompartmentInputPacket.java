package com.alekiponi.firmaciv.network;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.EmptyCompartmentEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ServerboundCompartmentInputPacket implements Packet<ServerGamePacketListener> {
    private final boolean inputLeft;
    private final boolean inputRight;
    private final boolean inputUp;
    private final boolean inputDown;

    private final int entityID;

    public ServerboundCompartmentInputPacket(boolean inputLeft, boolean inputRight, boolean inputUp, boolean inputDown, int id) {
        this.inputLeft = inputLeft;
        this.inputRight = inputRight;
        this.inputUp = inputUp;
        this.inputDown = inputDown;
        this.entityID = id;
    }

    public ServerboundCompartmentInputPacket(FriendlyByteBuf buffer) {
        this.inputLeft = buffer.readBoolean();
        this.inputRight = buffer.readBoolean();
        this.inputUp = buffer.readBoolean();
        this.inputDown = buffer.readBoolean();
        this.entityID = buffer.readInt();
    }

    public void encoder(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.inputLeft);
        buffer.writeBoolean(this.inputRight);
        buffer.writeBoolean(this.inputUp);
        buffer.writeBoolean(this.inputDown);
        buffer.writeInt(this.entityID);
    }

    public static ServerboundCompartmentInputPacket decoder(FriendlyByteBuf buffer) {
        return new ServerboundCompartmentInputPacket(buffer);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Entity entity = context.get().getSender().level().getEntity(this.entityID);
            ServerPlayer player = context.get().getSender();
            if(entity instanceof EmptyCompartmentEntity compartment) {
                assert player != null;
                if (player.distanceTo(compartment) < 2) {
                    compartment.setInput(this.inputLeft, this.inputRight, this.inputUp, this.inputDown);
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
