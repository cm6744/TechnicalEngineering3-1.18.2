package ten3.lib.tile.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;

public abstract class CmTileEngine extends CmTileMachine {

    public CmTileEngine(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot)
    {
        return matchFuelAndShrink(s, true) > 0;
    }

    @Override
    public Type typeOf()
    {
        return Type.GENERATOR;
    }

    @Override
    public int initialFaceMode(Capability<?> cap)
    {
        if(cap == CapabilityEnergy.ENERGY) {
            return FaceOption.OUT;
        }
        return FaceOption.BOTH;
    }

    @Override
    public void update() {

        super.update();

        if(!signalAllowRun() || !energyAllowRun()) return;

        ItemStack ext = inventory.getItem(0);

        if(data.get(FUEL) > 0) {
            data.translate(ENERGY, getActualEfficiency(), info.maxStorageEnergy);//up energy
            data.translate(FUEL, -efficientIn, 0);//down fuel
        }
        else {
            int fuel_p = matchFuelAndShrink(ext, false);
            if(fuel_p > 0) {
                data.set(FUEL, fuel_p);
                data.set(MAX_FUEL, fuel_p);
            }
        }

    }

    @Override
    protected boolean hasFaceCapability(Capability<?> cap, Direction d)
    {
        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return false;
        }
        if(cap == CapabilityEnergy.ENERGY) {
            return d == null || d == Direction.UP;
        }
        return d != Direction.UP;
    }

    public abstract int matchFuelAndShrink(ItemStack stack, boolean simulate);

}
