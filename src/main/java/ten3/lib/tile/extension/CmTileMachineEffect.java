package ten3.lib.tile.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.Type;

public abstract class CmTileMachineEffect extends CmTileMachine {

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    public CmTileMachineEffect(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public void update()
    {
        doBaseData();
        process();
    }

    public void process()
    {
        if(energyAllowRun() && signalAllowRun() && conditionStart()) {
            data.translate(PROGRESS, getActualEfficiency());
            data.translate(ENERGY, -getActualEfficiency());

            data.set(MAX_PROGRESS, (int) (seconds() * 20 * initialEfficientIn));
            if(data.get(PROGRESS) > data.get(MAX_PROGRESS))
            {
                data.set(PROGRESS, 0);
                effect();
            }
        } else {
            reflection.setActive(false);
            data.set(PROGRESS, 0);
        }
    }

    public abstract void effect();

    public abstract double seconds();

    public boolean conditionStart()
    {
        return true;
    }

}
