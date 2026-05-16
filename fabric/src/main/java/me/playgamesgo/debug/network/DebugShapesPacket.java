package me.playgamesgo.debug.network;

import me.playgamesgo.debug.FabricDebugWriter;
import me.playgamesgo.debug.Operation;
import me.playgamesgo.debug.client.shape.FabricShape;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record DebugShapesPacket(@NotNull List<Operation> operations) implements CustomPayload {
    public static final CustomPayload.Id<DebugShapesPacket> PACKET_ID = new CustomPayload.Id<>(Identifier.of("debug", "shapes"));
    public static final PacketCodec<RegistryByteBuf, DebugShapesPacket> PACKET_CODEC = PacketCodec.of(DebugShapesPacket::write, DebugShapesPacket::new);

    public DebugShapesPacket(@NotNull PacketByteBuf buf) {
        this(buf.readList(DebugShapesPacket::readOperation));
    }

    private static @NotNull Operation readOperation(@NotNull PacketByteBuf buf) {
        return switch (buf.readVarInt()) {
            case 0 -> new Operation.Set(buf.readIdentifier().toString(), buf.readEnumConstant(FabricShape.Type.class).deserialize(buf));
            case 1 -> new Operation.Remove(buf.readIdentifier().toString());
            case 2 -> new Operation.ClearNameSpace(buf.readString(32767));
            case 3 -> new Operation.Clear();
            default -> throw new IllegalArgumentException("Unknown operation type");
        };
    }

    public void write(@NotNull PacketByteBuf buf) {
        buf.writeCollection(operations, (b, op) -> op.write(new FabricDebugWriter(b)));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
