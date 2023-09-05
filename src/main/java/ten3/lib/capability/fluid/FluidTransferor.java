package ten3.lib.capability.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.option.FaceOption;
import ten3.util.DirectionHelper;

import java.util.Queue;

@SuppressWarnings("all")
public class FluidTransferor
{

    CmTileMachine t;

    public FluidTransferor(CmTileMachine t) {
        this.t = t;
    }

    public final Queue<Direction> fluidQR = DirectionHelper.newQueueOffer();

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

        if(d != null) {
            if(FaceOption.isPassive(t.info.direCheckFluid(d))) return;
            if(!FaceOption.isOut(t.info.direCheckFluid(d))) return;
        }
        BlockEntity tile = checkTile(p);
        if(tile != null) {
            IFluidHandler src = handlerOf(t, d);
            if(src == null) return;
            IFluidHandler dest = handlerOf(tile, DirectionHelper.safeOps(d));
            if(dest == null) return;

            int i = 0;
            for(Tank tank : t.tanks) {
                if(!tank.isEmpty() && t.tankType(i).canOut()) {
                    FluidStack insert = tank.getFluid().copy();
                    insert.setAmount(Math.min(insert.getAmount(), v));

                    FluidStack s = FluidUtil.tryFluidTransfer(dest, src, insert, true);
                    if(!s.isEmpty())
                    break;
                }
                i++;
            }
        }

    }

    public void transferFrom(BlockPos p, Direction d, int v) {

        if(d != null) {
            if(FaceOption.isPassive(t.info.direCheckFluid(d))) return;
            if(!FaceOption.isIn(t.info.direCheckFluid(d))) return;
        }

        BlockEntity tile = checkTile(p);
        if(tile != null) {
            IFluidHandler src = handlerOf(tile, DirectionHelper.safeOps(d));
            if(src == null) return;
            IFluidHandler dest = handlerOf(t, d);
            if(dest == null) return;

            FluidUtil.tryFluidTransfer(dest, src, v, true);
        }

    }

    public boolean selfGive(FluidStack stack, int from, int to, boolean sim)
    {
        if(stack.isEmpty()) return true;
        TankArray tka = (TankArray) handlerOf(t, null);
        if(tka == null) return false;
        int amt = tka.forceFill(stack, from, to, IFluidHandler.FluidAction.SIMULATE);
        boolean can = amt == stack.getAmount();
        if(!sim) {
            tka.forceFill(stack, from, to, IFluidHandler.FluidAction.EXECUTE);
        }
        return can;
    }

    public FluidStack selfGet(FluidStack stack, int from, int to,boolean sim)
    {
        TankArray tka = (TankArray) handlerOf(t, null);
        if(tka == null) return FluidStack.EMPTY;
        FluidStack s = tka.forceDrain(stack, from, to, IFluidHandler.FluidAction.SIMULATE);
        if(!sim) {
            tka.forceDrain(stack, from, to, IFluidHandler.FluidAction.EXECUTE);
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
