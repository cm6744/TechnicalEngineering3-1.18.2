package ten3.core.machine.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CableTileStar extends CableTile {

    public CableTileStar(BlockPos pos, BlockState state) {

        super(pos, state);

    }

    @Override
    public int getCapacity()
    {
        return Integer.MAX_VALUE;
    }
}
