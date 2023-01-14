package ten3.core.machine.useenergy.pulverizer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.recipe.IBaseRecipeCm;
import ten3.lib.capability.fluid.Tank;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.lib.tile.extension.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;

public class PulverizerTile extends CmTileMachineRecipe
{

    @SuppressWarnings("all")
    public PulverizerTile(BlockPos pos, BlockState state) {

        super(pos, state, new SlotInfo(0, 0, 1, 4));

        info.setCap(kFE(20));
        setEfficiency(20);

        addTank(new Tank(1000, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 0, 43, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 1, 112, 25, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
        addSlot(new SlotCm(inventory, 2, 130, 25, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
        addSlot(new SlotCm(inventory, 3, 112, 43, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
        addSlot(new SlotCm(inventory, 4, 130, 43, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    public RecipeCheckType slotType(int slot)
    {
        if(slot == 0) return RecipeCheckType.INPUT;
        if(slot == 1 || slot == 2 || slot == 3 || slot == 4) return RecipeCheckType.OUTPUT;
        return RecipeCheckType.IGNORE;
    }

    public RecipeCheckType tankType(int tank)
    {
        return RecipeCheckType.IGNORE;
    }

    @Override
    public int ticks() {
        return ((IBaseRecipeCm<Container>) recipeNow).time();
    }

}
