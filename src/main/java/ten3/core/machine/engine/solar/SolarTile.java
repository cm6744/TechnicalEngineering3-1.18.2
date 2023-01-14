package ten3.core.machine.engine.solar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import ten3.lib.tile.extension.CmTileEngine;

public class SolarTile extends CmTileEngine {

    public SolarTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(80));
        setEfficiency(10);
    }

    public int matchFuelAndShrink(ItemStack stack, boolean simulate)
    {
        if(level.canSeeSky(worldPosition.above()) && level.isDay() && !level.isRaining()) {
            return 600;
        }
        return 0;
    }

}
