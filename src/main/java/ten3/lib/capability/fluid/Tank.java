package ten3.lib.capability.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class Tank extends FluidTank
{

    public Tank(int capacity)
    {
        super(capacity);
    }

    public boolean isFluidValid(int tank, @NotNull FluidStack stack)
    {
        return super.isFluidValid(tank, stack)
                && getFluid().getFluid() == stack.getFluid();
    }

    public Tank copy()
    {
        Tank t = new Tank(capacity);
        t.setFluid(getFluid());
        return t;
    }

}
