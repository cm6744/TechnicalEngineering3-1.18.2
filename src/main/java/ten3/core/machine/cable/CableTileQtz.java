package ten3.core.machine.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CableTileQtz extends CableTile {

    public CableTileQtz(BlockPos pos, BlockState state) {

        super(pos, state);

    }

    @Override
    public int getCapacity()
    {
        return kFE(20);
    }
}
