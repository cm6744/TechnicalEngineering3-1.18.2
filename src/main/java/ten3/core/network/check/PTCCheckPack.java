package ten3.core.network.check;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import ten3.core.network.Network;

import java.util.function.Supplier;

public class PTCCheckPack {

    public PTCCheckPack(FriendlyByteBuf b) {}

    public PTCCheckPack() {}

    public void writeBuffer(FriendlyByteBuf b) {}

    public void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(() -> {
            handler();
        });
        cs.get().setPacketHandled(true);

    }

    public void handler() {

        Network.sendToServer(new PTSCheckPack());

    }

}
