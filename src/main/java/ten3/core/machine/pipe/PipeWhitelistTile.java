package ten3.core.machine.pipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.SlotCm;

public class PipeWhitelistTile extends PipeTile {

    public PipeWhitelistTile(BlockPos pos, BlockState state) {

        super(pos, state);

        for(int x = 7, y = 35, i = 0; i < 9; i++) {
            addSlot(new SlotCm(inventory, i, x + 18 * i, y, SlotCm.RECEIVE_ALL_INPUT, false, false));
        }

    }

    @Override
    public boolean isItemCanBeTransferred(ItemStack stack)
    {
        return inventory.asAValidCheckOf(stack);
    }

}
