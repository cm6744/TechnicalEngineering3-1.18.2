package ten3.core.machine.useenergy.pulverizer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.recipe.FormsCombinedRecipe;
import ten3.lib.recipe.IBaseRecipeCm;
import ten3.lib.capability.fluid.Tank;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.extension.SlotInfo;
import ten3.lib.wrapper.SlotCm;

public class PulverizerTile extends CmTileMachineRecipe<FormsCombinedRecipe>
{

    @SuppressWarnings("all")
    public PulverizerTile(BlockPos pos, BlockState state) {

        super(pos, state, new SlotInfo(0, 0, 1, 4, 0, 0, 0, 0));

        info.setCap(kFE(20));
        setEfficiency(20);

        addSlot(new SlotCm(this, 0, 43, 20));
        addSlot(new SlotCm(this, 1, 112, 25).withIsResultSlot());
        addSlot(new SlotCm(this, 2, 130, 25).withIsResultSlot());
        addSlot(new SlotCm(this, 3, 112, 43).withIsResultSlot());
        addSlot(new SlotCm(this, 4, 130, 43).withIsResultSlot());
    }

    public int inventorySize()
    {
        return 5;
    }

    public IngredientType slotType(int slot)
    {
        if(slot == 0) return IngredientType.INPUT;
        if(slot == 1 || slot == 2 || slot == 3 || slot == 4) return IngredientType.OUTPUT;
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return true;
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
    public int ticks()
    {
        return ((IBaseRecipeCm<Container>) recipeNow).time();
    }

}
