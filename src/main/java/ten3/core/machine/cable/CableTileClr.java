package ten3.core.machine.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CableTileClr extends CableTile {

    public CableTileClr(BlockPos pos, BlockState state) {

        super(pos, state);

    }

    @Override
    public int getCapacity()
    {
        return kFE(50);
    }
}
