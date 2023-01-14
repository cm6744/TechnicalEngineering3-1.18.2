package ten3.lib.tile.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.Type;

public abstract class CmTileMachineProcess extends CmTileMachine {

    @Override
    public Type typeOf() {
        return Type.MACHINE_PROCESS;
    }

    public CmTileMachineProcess(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public abstract int ticks();

    @Override
    public void update()
    {
        doBaseData();
        process();
    }

    public void process()
    {
        if(conditionStart() && signalAllowRun() && energyAllowRun()) {
            reflection.setActive(true);
            data.set(MAX_PROGRESS, ticks() * initialEfficientIn);
            data.translate(PROGRESS, getActualEfficiency());

            if(cooking()) {
                reflection.setActive(false);
                return;
            }
            data.translate(ENERGY, -getActualEfficiency());

            if(data.get(PROGRESS) > data.get(MAX_PROGRESS)) {
                cookEnd();
                data.set(PROGRESS, 0);
            }
        }
        else {
            reflection.setActive(false);
            data.set(PROGRESS, 0);
        }
    }

    //return: end cook
    public boolean cooking()
    {
        return false;
    }

    public void cookEnd()
    {}

    public boolean conditionStart()
    {
        return true;
    }

}
