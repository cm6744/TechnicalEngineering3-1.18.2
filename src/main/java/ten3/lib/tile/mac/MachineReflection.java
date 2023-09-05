package ten3.lib.tile.mac;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.block.mac.Machine4;
import ten3.core.block.mac.Machine6;
import ten3.core.block.mac.MachineN;

public class MachineReflection
{

    CmTileMachine t;
    
    public MachineReflection(CmTileMachine tile)
    {
        t = tile;
    }

    public void setActive(boolean a)
    {
        BlockState s = t.getBlockState();
        t.getLevel().setBlock(t.getBlockPos(), s.setValue(MachineN.active, a), 3);
    }

    public void setFace(Direction f)
    {
        BlockState s = t.getBlockState();
        if(s.getBlock() instanceof Machine4) {
            t.getLevel().setBlock(t.getBlockPos(), s.setValue(Machine4.direction, f), 3);
        }
        else if(s.getBlock() instanceof Machine6) {
            t.getLevel().setBlock(t.getBlockPos(), s.setValue(Machine6.direction, f), 3);
        }
    }

    public boolean isActive()
    {
        BlockState s = t.getBlockState();
        return s.hasProperty(MachineN.active) ? s.getValue(MachineN.active) : false;
    }

    public Direction direction()
    {
        BlockState s = t.getBlockState();
        if(s.getBlock() instanceof Machine4) {
            return s.hasProperty(Machine4.direction) ? s.getValue(Machine4.direction) : Direction.NORTH;
        }
        else if(s.getBlock() instanceof Machine6) {
            return s.hasProperty(Machine6.direction) ? s.getValue(Machine6.direction) : Direction.NORTH;
        }
        return Direction.NORTH;
    }
    
}
