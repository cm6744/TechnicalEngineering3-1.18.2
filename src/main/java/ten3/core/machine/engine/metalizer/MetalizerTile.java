package ten3.core.machine.engine.metalizer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import ten3.core.machine.engine.MatchFuel;
import ten3.lib.tile.extension.CmTileEngine;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.wrapper.SlotCm;

public class MetalizerTile extends CmTileEngine {

    public MetalizerTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(80));
        setEfficiency(80);
        addSlot(new SlotCm(this, 0, 43, 36));
    }

    public IngredientType slotType(int slot)
    {
        return IngredientType.INPUT;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return MatchFuel.matchFuelAndShrinkMetal(stack, true) > 0;
    }

    public IngredientType tankType(int tank)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return true;
    }

    public int inventorySize()
    {
        return 1;
    }

    @Override
    public int matchFuelAndShrink(ItemStack stack, boolean simulate) {
        return MatchFuel.matchFuelAndShrinkMetal(stack, simulate);
    }

}
