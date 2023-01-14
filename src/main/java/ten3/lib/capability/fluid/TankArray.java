package ten3.lib.capability.fluid;

import net.minecraft.core.Direction;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import ten3.lib.capability.UtilCap;
import ten3.lib.tile.mac.CmTileMachine;

public class TankArray implements IFluidHandler
{

    public CmTileMachine tile;
    Direction di;

    public TankArray(Direction d, CmTileMachine t)
    {
        di = d;
        tile = t;
    }

    public int getTanks()
    {
        return tile.tanks.size();
    }

    @NotNull
    public FluidStack getFluidInTank(int tank)
    {
        if(tile.tanks.size() <= tank) {
            return FluidStack.EMPTY;
        }
        if(!UtilCap.canOutFLU(di, tile)) {
            return FluidStack.EMPTY;
        }
        return tile.tanks.get(tank).getFluid();
    }

    public int getTankCapacity(int tank)
    {
        if(tile.tanks.size() <= tank) return 0;
        return tile.tanks.get(tank).getCapacity();
    }

    public boolean isFluidValid(int tank, @NotNull FluidStack stack)
    {
        if(tile.tanks.size() <= tank) return false;
        return tile.tanks.get(tank).isFluidValid(stack);
    }

    public int fill(FluidStack resource, FluidAction action)
    {
        if(!UtilCap.canInFLU(di, tile)) {
            return 0;
        }
        FluidStack copy = resource.copy();
        for(Tank t : tile.tanks) {
            if(t == null) continue;
            if(!t.in) continue;
            copy.shrink(t.fill(copy, action));
        }
        return resource.getAmount() - copy.getAmount();
    }

    public int forceFill(FluidStack resource, FluidAction action)
    {
        FluidStack copy = resource.copy();
        for(Tank t : tile.tanks) {
            if(t == null) continue;
            copy.shrink(t.fill(copy, action));
        }
        return resource.getAmount() - copy.getAmount();
    }

    @NotNull
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        if(!UtilCap.canOutFLU(di, tile)) {
            return FluidStack.EMPTY;
        }
        FluidStack s = new FluidStack(resource.getFluid(), 0);
        for(Tank t : tile.tanks) {
            if(t == null) continue;
            if(!t.out) continue;
            s.grow(t.drain(resource, action).getAmount());
            if(s.getAmount() >= resource.getAmount()) break;
        }
        return s;
    }

    @NotNull
    public FluidStack forceDrain(FluidStack resource, FluidAction action)
    {
        FluidStack s = new FluidStack(resource.getFluid(), 0);
        for(Tank t : tile.tanks) {
            if(t == null) continue;
            s.grow(t.drain(resource, action).getAmount());
            if(s.getAmount() >= resource.getAmount()) break;
        }
        return s;
    }

    @NotNull
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        if(!UtilCap.canOutFLU(di, tile)) {
            return FluidStack.EMPTY;
        }
        FluidStack s = null;
        for(Tank t : tile.tanks) {
            if(t == null) continue;
            if(!t.out) continue;
            if(s == null) {
                s = t.drain(maxDrain, action);
            }
            else {
                FluidStack s2 = t.drain(maxDrain, action);
                if(s2.getFluid() == s.getFluid()) {
                    s.grow(s2.getAmount());
                }
            }
            if(s.getAmount() >= maxDrain) break;
        }
        return s == null ? FluidStack.EMPTY : s;
    }

    @NotNull
    public FluidStack forceDrain(int maxDrain, FluidAction action)
    {
        FluidStack s = null;
        for(Tank t : tile.tanks) {
            if(t == null) continue;
            if(s == null) {
                s = t.drain(maxDrain, action);
            }
            else {
                FluidStack s2 = t.drain(maxDrain, action);
                if(s2.getFluid() == s.getFluid()) {
                    s.grow(s2.getAmount());
                }
            }
            if(s.getAmount() >= maxDrain) break;
        }
        return s == null ? FluidStack.EMPTY : s;
    }

}
