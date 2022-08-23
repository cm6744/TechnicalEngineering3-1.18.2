package ten3.core.machine.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ten3.core.machine.Cable;
import ten3.lib.capability.energy.EnergyTransferor;
import ten3.lib.capability.energy.FEStorageWayFinding;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;

public class CableTile extends CmTileMachine {

    public CableTile(BlockPos pos, BlockState state) {

        super(pos, state);

        setCap(getCapacity(), FaceOption.BOTH, FaceOption.OFF, getCapacity());

    }

    @Override
    public Type typeOf() {
        return Type.CABLE;
    }

    @Override
    public LazyOptional<IEnergyStorage> crtLazyEne(Direction d) {
        return LazyOptional.of(() -> new FEStorageWayFinding(d, this));
    }

    @Override
    public LazyOptional<IItemHandler> crtLazyItm(Direction d) {
        return LazyOptional.empty();
    }


    public int getCapacity() {

        return kFE(1);

    }

    boolean eneWFS;

    @Override
    public void update() {

        if(getTileAliveTime() % 5 == 0) {
            ((Cable) getBlockState().getBlock()).update(world, pos);
            FEStorageWayFinding.updateNet(this);
        }

        if(getTileAliveTime() % 15 == 0) {
            eneWFS = EnergyTransferor.handlerOf(this, null).getEnergyStored() > 0;
        }

        setActive(eneWFS && checkCanRun());

    }

    @Override
    protected boolean can(Capability<?> cap, Direction d) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return false;
        }
        return super.can(cap, d);
    }

}
