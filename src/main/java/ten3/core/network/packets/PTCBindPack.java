package ten3.core.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import ten3.core.client.ClientHolder;

import java.util.function.Supplier;

public class PTCBindPack {

    BlockPos bind, pos;

    public PTCBindPack(FriendlyByteBuf b) {
        bind = b.readBlockPos();
        pos = b.readBlockPos();
    }

    public final void writeBuffer(FriendlyByteBuf b) {
        b.writeBlockPos(bind);
        b.writeBlockPos(pos);
    }

    public PTCBindPack(BlockPos v, BlockPos pos) {
        this.pos = pos;
        this.bind = v;
    }

    public final void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(() -> {
            ClientHolder.binds.put(pos, bind);
        });
        cs.get().setPacketHandled(true);

    }

}
