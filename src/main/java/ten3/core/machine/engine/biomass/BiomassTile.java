package ten3.core.machine.engine.biomass;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.machine.engine.MatchFuel;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.recipe.CmTileEngine;

public class BiomassTile extends CmTileEngine {

    public BiomassTile(BlockPos pos, BlockState state) {

        super(pos, state);

        setCap(kFE(80), FaceOption.OUT, FaceOption.IN, 80);

    }

    @Override
    public int matchFuelAndShrink(ItemStack stack, boolean simulate) {
        return MatchFuel.matchFuelAndShrinkPlant(stack, simulate);
    }

}
