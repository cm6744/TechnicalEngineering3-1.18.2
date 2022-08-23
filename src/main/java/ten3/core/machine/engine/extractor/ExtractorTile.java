package ten3.core.machine.engine.extractor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.machine.engine.MatchFuel;
import ten3.lib.tile.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.recipe.CmTileEngine;
import ten3.lib.wrapper.SlotCmFuel;

public class ExtractorTile extends CmTileEngine {

    public ExtractorTile(BlockPos pos, BlockState state) {

        super(pos, state);

        setCap(kFE(60), FaceOption.OUT, FaceOption.IN, 30);

    }

    @Override
    public int matchFuelAndShrink(ItemStack stack, boolean simulate) {
        return MatchFuel.matchFuelAndShrink(stack, simulate);
    }

}
