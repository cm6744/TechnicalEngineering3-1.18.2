package ten3.lib.capability.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class Tank extends FluidTank
{

    Condition lmt;
    boolean in, out;

    public Tank(int capacity, Condition limit, boolean in, boolean out)
    {
        super(capacity);
        lmt = limit;
        this.in = in;
        this.out = out;
    }

    public boolean isFluidValid(FluidStack stack)
    {
        return super.isFluidValid(stack) && lmt.valid(stack);
    }

    public boolean isFluidValid(int tank, @NotNull FluidStack stack)
    {
        return super.isFluidValid(tank, stack) && lmt.valid(stack);
    }

    public interface Condition
    {
        boolean valid(FluidStack s);
    }

}
