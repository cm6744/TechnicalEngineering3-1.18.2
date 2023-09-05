package ten3.core.machine.useenergy.compressor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.recipe.FormsCombinedRecipe;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.util.RecipeHelper;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.extension.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.util.TagHelper;

public class CompressorTile extends CmTileMachineRecipe<FormsCombinedRecipe>
{

    public CompressorTile(BlockPos pos, BlockState state) {

        super(pos, state, new SlotInfo(0, 1, 2, 2, 0, 0, 0, 0));

        info.setCap(kFE(20));
        setEfficiency(15);

        addSlot(new SlotCm(this, 0, 43, 15));
        addSlot(new SlotCm(this, 1, 43, 51));
        addSlot(new SlotCm(this, 2, 115, 34).withIsResultSlot());
    }

    public boolean customFitStackIn(ItemStack s, int slot)
    {
        if(TagHelper.containsItem(s.getItem(), TagHelper.keyItem("ten3:moulds"))) {
            return slot == 1;
        }
        var o1 = RecipeHelper.getRecipeUsing(level, recipeType, inventory.getItem(1));
        var o2 = RecipeHelper.getRecipeUsing(o1, s);
        return !o2.isEmpty();
    }

    public int inventorySize()
    {
        return 3;
    }

    public IngredientType slotType(int slot)
    {
        if(slot == 0 || slot == 1) return IngredientType.INPUT;
        if(slot == 2) return IngredientType.OUTPUT;
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        if(slot == 1) {
            return TagHelper.containsItem(stack.getItem(), TagHelper.keyItem("ten3:moulds"));
        }
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

    public boolean canShrink(ItemStack i, int slot)
    {
        return slot != 1;
    }

}
