package ten3.core.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import ten3.core.client.ClientHolder;
import ten3.core.network.Network;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.extension.CmTileMachineRadiused;
import ten3.util.DirectionHelper;

import java.util.List;
import java.util.function.Supplier;

public class PTCInfoClientPack {

    int ene;
    int itm;
    int res;
    int rd;
    int fld;
    BlockPos pos;
    Direction d;

    public static void send(CmTileMachine t) {
        for(Direction d : Direction.values()) {
            Network.sendToClient(new PTCInfoClientPack(t, d));
        }
    }

    public PTCInfoClientPack(FriendlyByteBuf b) {

        ene = b.readInt();
        itm = b.readInt();
        fld = b.readInt();
        res = b.readInt();
        rd = b.readInt();
        pos = b.readBlockPos();
        d = b.readEnum(Direction.class);

    }

    public PTCInfoClientPack(CmTileMachine t, Direction d) {

        ene = t.info.direCheckEnergy(d);
        itm = t.info.direCheckItem(d);
        fld = t.info.direCheckFluid(d);
        res = t.data.get(CmTileMachine.RED_MODE);
        pos = t.getBlockPos();
        if(t instanceof CmTileMachineRadiused) {
            rd = ((CmTileMachineRadiused) t).radius;
        }
        this.d = d;

    }

    public void writeBuffer(FriendlyByteBuf b) {

        b.writeInt(ene);
        b.writeInt(itm);
        b.writeInt(fld);
        b.writeInt(res);
        b.writeInt(rd);
        b.writeBlockPos(pos);
        b.writeEnum(d);

    }

    public void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(this::handler);
        cs.get().setPacketHandled(true);

    }

    public void handler() {

        List<Integer> energy = ClientHolder.energy.getOrFill(pos, 6);
        List<Integer> item = ClientHolder.item.getOrFill(pos, 6);
        List<Integer> fluid = ClientHolder.fluid.getOrFill(pos, 6);

        int i = DirectionHelper.direToInt(d);
        energy.set(i, ene);
        item.set(i, itm);
        fluid.set(i, fld);

        ClientHolder.redstone.put(pos, res);
        ClientHolder.radius.put(pos, rd);
    }

}
