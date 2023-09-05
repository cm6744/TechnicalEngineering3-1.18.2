package ten3.core.machine.engine.extractor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import ten3.core.machine.engine.MatchFuel;
import ten3.lib.tile.extension.CmTileEngine;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.wrapper.SlotCm;

public class ExtractorTile extends CmTileEngine {

    public ExtractorTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(60));
        setEfficiency(30);
        addSlot(new SlotCm(this, 0, 43, 36));
    }

    public int inventorySize()
    {
        return 1;
    }

    public IngredientType slotType(int slot)
    {
        return IngredientType.INPUT;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return MatchFuel.matchFuelAndShrink(stack, true) > 0;
    }

    public IngredientType tankType(int tank)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return true;
    }

    @Override
    public int matchFuelAndShrink(ItemStack stack, boolean simulate) {
        return MatchFuel.matchFuelAndShrink(stack, simulate);
    }

}
