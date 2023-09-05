package ten3.core.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import ten3.core.client.ClientHolder;
import ten3.core.machine.channel.ChannelTile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PTCInfoChannelPack
{

    List<BlockPos> in = new ArrayList<>(), out = new ArrayList<>();
    int inSiz, outSiz;
    int inMode, outMode;
    BlockPos pos;

    public PTCInfoChannelPack(FriendlyByteBuf b) {

        inMode = b.readInt();
        outMode = b.readInt();
        inSiz = b.readInt();
        outSiz = b.readInt();
        pos = b.readBlockPos();
        for(int i = 0; i < inSiz; i++)
            in.add(b.readBlockPos());
        for(int i = 0; i < outSiz; i++)
            out.add(b.readBlockPos());
    }

    public PTCInfoChannelPack(ChannelTile c) {
        inMode = c.iMode;
        outMode = c.oMode;
        pos = c.getBlockPos();
        in = c.inputs;
        out = c.outputs;
        inSiz = in.size();
        outSiz = out.size();
    }

    public void writeBuffer(FriendlyByteBuf b) {

        b.writeInt(inMode);
        b.writeInt(outMode);
        b.writeInt(inSiz);
        b.writeInt(outSiz);
        b.writeBlockPos(pos);
        for(int i = 0; i < inSiz; i++)
            b.writeBlockPos(in.get(i));
        for(int i = 0; i < outSiz; i++)
            b.writeBlockPos(out.get(i));

    }

    public void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(this::handler);
        cs.get().setPacketHandled(true);

    }

    public void handler()
    {
        ClientHolder.channelInputs.get(pos).clear();//clear cache
        ClientHolder.channelOutputs.get(pos).clear();//clear cache
        for(int i = 0; i < inSiz; i++)
            ClientHolder.channelInputs.set(pos, in.get(i), i, 5);
        for(int i = 0; i < outSiz; i++)
            ClientHolder.channelOutputs.set(pos, out.get(i), i, 5);
        ClientHolder.channelModeInput.put(pos, inMode);
        ClientHolder.channelModeOutput.put(pos, outMode);
    }

}
