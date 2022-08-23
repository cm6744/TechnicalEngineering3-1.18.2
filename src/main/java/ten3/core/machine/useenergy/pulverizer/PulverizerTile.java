package ten3.core.machine.useenergy.pulverizer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.recipe.IBaseRecipeCm;
import ten3.init.RecipeInit;
import ten3.lib.tile.recipe.CmTileMachineProcessed;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.recipe.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.KeyUtil;

public class PulverizerTile extends CmTileMachineProcessed {

    @SuppressWarnings("all")
    public PulverizerTile(BlockPos pos, BlockState state) {

        super(pos, state, true, new SlotInfo(0, 0, 1, 4));

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 20);

        addSlot(new SlotCustomCm(inventory, 0, 42, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 1, 112, 25, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
        addSlot(new SlotCm(inventory, 2, 130, 25, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
        addSlot(new SlotCm(inventory, 3, 112, 43, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
        addSlot(new SlotCm(inventory, 4, 130, 43, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public int getTimeCook() {
        return ((IBaseRecipeCm<Container>) recipeNow).time();
    }

}
