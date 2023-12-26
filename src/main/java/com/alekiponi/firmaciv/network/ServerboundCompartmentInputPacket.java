package com.alekiponi.firmaciv.network;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.EmptyCompartmentEntity;
import com.google.common.graph.Network;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ServerboundCompartmentInputPacket implements Packet<ServerGamePacketListener> {
    private final boolean inputLeft;
    private final boolean inputRight;
    private final boolean inputUp;
    private final boolean inputDown;

    public ServerboundCompartmentInputPacket(boolean inputLeft, boolean inputRight, boolean inputUp, boolean inputDown) {
        this.inputLeft = inputLeft;
        this.inputRight = inputRight;
        this.inputUp = inputUp;
        this.inputDown = inputDown;
    }

    public ServerboundCompartmentInputPacket(FriendlyByteBuf buffer) {
        this.inputLeft = buffer.readBoolean();
        this.inputRight = buffer.readBoolean();
        this.inputUp = buffer.readBoolean();
        this.inputDown = buffer.readBoolean();
    }

    public void encoder(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.inputLeft);
        buffer.writeBoolean(this.inputRight);
        buffer.writeBoolean(this.inputUp);
        buffer.writeBoolean(this.inputDown);
    }

    public static ServerboundCompartmentInputPacket decoder(FriendlyByteBuf buffer) {
        return new ServerboundCompartmentInputPacket(buffer);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null && player.getVehicle() != null) {
                if (player.getVehicle() instanceof EmptyCompartmentEntity compartmentEntity) {
                    compartmentEntity.setInput(this.inputLeft, this.inputRight, this.inputUp, this.inputDown);
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
