package ten3.core.machine.useenergy.compressor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.recipe.IBaseRecipeCm;
import ten3.init.RecipeInit;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.recipe.CmTileMachineProcessed;
import ten3.lib.tile.recipe.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.KeyUtil;

public class CompressorTile extends CmTileMachineProcessed {

    public CompressorTile(BlockPos pos, BlockState state) {

        super(pos, state, false, new SlotInfo(0, 0, 1, 1));

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 15);

        addSlot(new SlotCustomCm(inventory, 0, 42, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 1, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public int getTimeCook() {
        return ((IBaseRecipeCm<Container>) recipeNow).time();
    }

    @Override
    protected boolean canUseRecipeNow() {
        return super.canUseRecipeNow();
    }

}
