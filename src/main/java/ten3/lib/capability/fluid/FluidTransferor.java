package ten3.lib.capability.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.DireUtil;

import java.util.Queue;

@SuppressWarnings("all")
public class FluidTransferor
{

    CmTileMachine t;

    public FluidTransferor(CmTileMachine t) {
        this.t = t;
    }

    public final Queue<Direction> fluidQR = DireUtil.newQueueOffer();

    public void transferFluid() {
        //if(getTileAliveTime() % 10 == 0) {
        fluidQR.offer(fluidQR.remove());
        for(Direction d : fluidQR) {
            transferTo(d, t.info.maxExtractFluid);
            transferFrom(d, t.info.maxReceiveFluid);
            break;
        }
        //}
    }

    private BlockEntity checkTile(Direction d) {

        return checkTile(t.getBlockPos().offset(d.getNormal()));

    }

    private BlockEntity checkTile(BlockPos pos) {

        return t.getLevel().getBlockEntity(pos);

    }

    public static IFluidHandler handlerOf(BlockEntity t, Direction d) {

        return t.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, d).orElse(null);

    }

    public void transferTo(BlockPos p, Direction d, int v) {

        if(FaceOption.isPassive(t.info.direCheckFluid(d))) return;
        if(!FaceOption.isOut(t.info.direCheckFluid(d))) return;

        BlockEntity tile = checkTile(p);
        if(tile != null) {
            IFluidHandler src = handlerOf(t, d);
            if(src == null) return;
            IFluidHandler dest = handlerOf(tile, DireUtil.safeOps(d));
            if(dest == null) return;

            int min = -1;
            int i = 0;
            for(Tank tank : t.tanks) {
                if(!tank.isEmpty()) {
                    min = i;
                    break;
                }
                i++;
            }
            if(min == -1) return;
            FluidStack res = t.tanks.get(min).getFluid();
            res.setAmount(Math.min(res.getAmount(), v));
            FluidStack s = FluidUtil.tryFluidTransfer(dest, src, res, true);
            t.tanks.get(min).getFluid().shrink(s.getAmount());
        }

    }

    public void transferFrom(BlockPos p, Direction d, int v) {

        if(FaceOption.isPassive(t.info.direCheckFluid(d))) return;
        if(!FaceOption.isIn(t.info.direCheckFluid(d))) return;

        BlockEntity tile = checkTile(p);
        if(tile != null) {
            IFluidHandler src = handlerOf(tile, DireUtil.safeOps(d));
            if(src == null) return;
            IFluidHandler dest = handlerOf(t, d);
            if(dest == null) return;

            int min = -1;
            int i = 0;
            for(; i < src.getTanks(); i++) {
                if(!src.getFluidInTank(i).isEmpty()) {
                    min = i;
                    break;
                }
            }
            if(min == -1) return;

            int size = t.tanks.get(min).getCapacity() - t.tanks.get(min).getFluidAmount();
            FluidStack res = src.getFluidInTank(min);
            res.setAmount(size);
            FluidStack s = FluidUtil.tryFluidTransfer(dest, src, Math.min(size, v), true);
            t.tanks.get(min).getFluid().grow(s.getAmount());
        }

    }

    public boolean selfGive(FluidStack stack, boolean sim)
    {
        if(stack.isEmpty()) return true;
        TankArray tka = (TankArray) handlerOf(t, null);
        if(tka == null) return false;
        int amt = tka.forceFill(stack, IFluidHandler.FluidAction.SIMULATE);
        boolean can = amt == stack.getAmount();
        if(!sim) {
            tka.forceFill(stack, IFluidHandler.FluidAction.EXECUTE);
        }
        return can;
    }

    public FluidStack selfGet(FluidStack stack, boolean sim)
    {
        TankArray tka = (TankArray) handlerOf(t, null);
        if(tka == null) return FluidStack.EMPTY;
        FluidStack s = tka.forceDrain(stack, IFluidHandler.FluidAction.SIMULATE);
        if(!sim) {
            tka.forceDrain(stack, IFluidHandler.FluidAction.EXECUTE);
        }
        return s;
    }

    public void transferTo(Direction d, int v) {
        transferTo(t.getBlockPos().offset(d.getNormal()), d, v);
    }

    public void transferFrom(Direction d, int v) {
        transferFrom(t.getBlockPos().offset(d.getNormal()), d, v);
    }

}
