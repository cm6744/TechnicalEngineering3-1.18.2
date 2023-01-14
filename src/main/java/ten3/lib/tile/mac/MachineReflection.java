package ten3.lib.tile.mac;

import net.minecraft.core.Direction;
import ten3.core.machine.Machine;

public class MachineReflection
{

    CmTileMachine t;
    
    public MachineReflection(CmTileMachine tile)
    {
        t = tile;
    }

    public void setActive(boolean a)
    {
        t.getLevel().setBlock(t.getBlockPos(), t.getBlockState().setValue(Machine.active, a), 3);
    }

    public void setFace(Direction f)
    {
        t.getLevel().setBlock(t.getBlockPos(), t.getBlockState().setValue(Machine.dire, f), 3);
    }

    public boolean isActive()
    {
        return t.getBlockState().hasProperty(Machine.active) ? t.getBlockState().getValue(Machine.active) : false;
    }

    protected Direction direction()
    {
        return t.getBlockState().hasProperty(Machine.dire) ? t.getBlockState().getValue(Machine.dire) : Direction.NORTH;
    }
    
}
