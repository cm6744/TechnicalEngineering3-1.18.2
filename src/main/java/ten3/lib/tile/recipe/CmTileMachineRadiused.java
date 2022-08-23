package ten3.lib.tile.recipe;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.Type;

public abstract class CmTileMachineRadiused extends CmTileMachine {

    public int radius;
    public int initialRadius;

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    public CmTileMachineRadiused(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public abstract boolean isInWorkRadius(BlockPos pos);

    @Override
    public void resetAll()
    {
        super.resetAll();
        radius = initialRadius;
    }

}
