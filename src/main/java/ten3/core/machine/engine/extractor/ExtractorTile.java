package ten3.core.machine.engine.extractor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.machine.engine.MatchFuel;
import ten3.lib.tile.extension.CmTileEngine;
import ten3.lib.wrapper.SlotCm;

public class ExtractorTile extends CmTileEngine {

    public ExtractorTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(60));
        setEfficiency(30);
        addSlot(new SlotCm(inventory, 0, 43, 36, SlotCm.RECEIVE_ALL_INPUT, false, true));
    }

    @Override
    public int matchFuelAndShrink(ItemStack stack, boolean simulate) {
        return MatchFuel.matchFuelAndShrink(stack, simulate);
    }

}
