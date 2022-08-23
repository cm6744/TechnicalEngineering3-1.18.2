package ten3.core.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerIntSetterPacket {

    int mode;
    BlockPos pos;

    public ServerIntSetterPacket(FriendlyByteBuf b) {

        mode = b.readInt();
        pos = b.readBlockPos();

    }

    public ServerIntSetterPacket(int mode, BlockPos pos) {

        this.mode = mode;
        this.pos = pos;

    }

    public final void writeBuffer(FriendlyByteBuf b) {

        b.writeInt(mode);
        b.writeBlockPos(pos);

    }

    public final void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(() -> {
            handler(cs.get().getSender());
        });
        cs.get().setPacketHandled(true);

    }

    protected final void handler(Player player) {

        Level world = player.level;
        BlockEntity e = world.getBlockEntity(pos);
        if(e != null) {
            run(e);
        }

    }

    protected void run(BlockEntity tile) {

    }

}
