package ten3.core.machine.engine.solar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import ten3.core.machine.engine.MatchFuel;
import ten3.lib.tile.extension.CmTileEngine;
import ten3.lib.tile.mac.IngredientType;

public class SolarTile extends CmTileEngine {

    public SolarTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(80));
        setEfficiency(10);
    }

    public int inventorySize()
    {
        return 0;
    }

    public IngredientType slotType(int slot)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return true;
    }

    public IngredientType tankType(int tank)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return true;
    }

    public int matchFuelAndShrink(ItemStack stack, boolean simulate)
    {
        if(level.canSeeSky(worldPosition.above()) && level.isDay() && !level.isRaining()) {
            return 600;
        }
        return 0;
    }

}
