package ten3.core.machine.cell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CellTileGolden extends CellTile {

    public CellTileGolden(BlockPos pos, BlockState state) {

        super(pos, state);

    }

    public int getCapacity() {
        return kFE(2000);
    }

}
