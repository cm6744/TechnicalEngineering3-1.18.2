package ten3.core.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import ten3.core.item.IModeChangable;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.FaceOption;

import java.util.function.Supplier;

public class PTSChangeModePack
{

    public PTSChangeModePack(FriendlyByteBuf b) {
    }

    public final void writeBuffer(FriendlyByteBuf b) {
    }

    public PTSChangeModePack() {
    }

    public final void run(Supplier<NetworkEvent.Context> cs) {

        cs.get().enqueueWork(() -> {
            Player player = cs.get().getSender();
            if(player != null) {
                if(player.getMainHandItem().getItem() instanceof IModeChangable c)
                {
                    c.change(player);
                }
            }
        });
        cs.get().setPacketHandled(true);

    }

}
