package ten3.lib.tile.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CmTileMachineRadiused extends CmTileMachineEffect {

    public int radius;
    public int initialRadius;

    public CmTileMachineRadiused(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void resetAll()
    {
        super.resetAll();
        radius = initialRadius;
    }

}
