package ten3.core.network.check;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PTSCheckPack {

    public static boolean GET = false;

    public PTSCheckPack(FriendlyByteBuf b) {}

    public PTSCheckPack() {}

    public void writeBuffer(FriendlyByteBuf b) {}

    public void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(() -> {
            handler();
        });
        cs.get().setPacketHandled(true);

    }

    public void handler() {

        GET = true;

    }

}
