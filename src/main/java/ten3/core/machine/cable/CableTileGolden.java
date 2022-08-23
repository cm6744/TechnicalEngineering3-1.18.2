package ten3.core.machine.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CableTileGolden extends CableTile {

    public CableTileGolden(BlockPos pos, BlockState state) {

        super(pos, state);

    }

    @Override
    public int getCapacity() {
        return kFE(5);
    }

}
