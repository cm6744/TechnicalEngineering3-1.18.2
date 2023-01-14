package ten3.core.machine.engine.biomass;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.machine.engine.MatchFuel;
import ten3.lib.tile.extension.CmTileEngine;
import ten3.lib.wrapper.SlotCm;

public class BiomassTile extends CmTileEngine {

    public BiomassTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(80));
        setEfficiency(80);
        addSlot(new SlotCm(inventory, 0, 43, 36, SlotCm.RECEIVE_ALL_INPUT, false, true));
    }

    @Override
    public int matchFuelAndShrink(ItemStack stack, boolean simulate) {
        return MatchFuel.matchFuelAndShrinkPlant(stack, simulate);
    }

}
