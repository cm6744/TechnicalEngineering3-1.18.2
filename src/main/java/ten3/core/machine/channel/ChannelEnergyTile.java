package ten3.core.machine.channel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import ten3.lib.capability.energy.BatteryTile;

public class ChannelEnergyTile extends ChannelTile
{

    public ChannelEnergyTile(BlockPos pos, BlockState state)
    {
        super(pos, state);
        info.setCap(getCapacity(), 0, 0);
    }

    public int getCapacity()
    {
        return kFE(10);
    }

    public void update()
    {
        doBaseData();
        reflection.setActive(false);
        if(!signalAllowRun()) {
            return;
        }

        etr.transferEnergy();

        if(outputs.size() > 0) {
            reflection.setActive(true);
            BlockPos pos = outputs.get(nowOutputIndex);
            etr.transferTo(pos, null, info.maxExtractEnergy);
            if(!isPosSame(pos)) {
                spiltOut(pos);
            }
        }
        if(inputs.size() > 0) {
            reflection.setActive(true);
            BlockPos pos = inputs.get(nowInputIndex);
            etr.transferFrom(inputs.get(nowInputIndex), null, info.maxReceiveEnergy);
            if(!isPosSame(pos)) {
                spiltIn(pos);
            }
        }

        cycle();
    }

    public boolean isPosSame(BlockPos pos)
    {
        return (level.getBlockEntity(pos) instanceof ChannelEnergyTile);
    }

    protected boolean hasFaceCapability(Capability<?> cap, Direction d)
    {
        return cap == CapabilityEnergy.ENERGY &&
                (d == reflection.direction() || d == null);
    }

    IEnergyStorage handler;

    public void initHandlers()
    {
        handler = new BatteryTile(null, this);
    }

    public LazyOptional<IEnergyStorage> getEnergyHandler(Direction d)
    {
        return LazyOptional.of(() -> handler);
    }

}
