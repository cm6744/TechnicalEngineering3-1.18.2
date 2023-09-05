package ten3.core.machine.useenergy.smelter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import ten3.core.item.upgrades.LevelupBlast;
import ten3.core.item.upgrades.LevelupSmoke;
import ten3.lib.recipe.FormsCombinedIngredient;
import ten3.lib.recipe.RandRecipe;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.extension.SlotInfo;
import ten3.lib.wrapper.SlotCm;

import java.util.List;

public class FurnaceTile extends CmTileMachineRecipe<SmeltingRecipe>
{

    public FurnaceTile(BlockPos pos, BlockState state) {

        super(pos, state, new SlotInfo(0, 0, 1, 1, 0, 0, 0, 0));

        info.setCap(kFE(20));
        setEfficiency(15);

        recipeType = RecipeType.SMELTING;

        addSlot(new SlotCm(this, 0, 43, 20));
        addSlot(new SlotCm(this, 1, 115, 34).withIsResultSlot());
    }

    public boolean customFitStackIn(FluidStack s, int tank)
    {
        return false;
    }

    public boolean customFitStackIn(ItemStack s, int slot)
    {
        return getRecipe(recipeType, s) != null;
    }

    public int inventorySize()
    {
        return 2;
    }

    @Override
    public void initialRecipeType()
    {
        recipeType = RecipeType.SMELTING;
    }

    public IngredientType slotType(int slot)
    {
        if(slot == 0) return IngredientType.INPUT;
        if(slot == 1) return IngredientType.OUTPUT;
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
    public void shrinkItems()
    {
        for(int i = slotInfo.i1(); i <= slotInfo.i2(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            int c1 = 1;
            if(!stack.getContainerItem().isEmpty()) {
                ItemStack s2 = stack.getContainerItem();
                s2.setCount(c1);
                Block.popResource(level, worldPosition, s2);
            }
            stack.shrink(c1);
        }
    }

    public boolean cooking()
    {
        ItemStack fullAdt = recipeNow.assemble(inventory);

        boolean give = true;
        if(fullAdt.isEmpty()) {
            return false;
        }

        if(!itr.selfGive(fullAdt, slotInfo.o1(), slotInfo.o2(), true)) {
            give = false;
        }

        if(!give) {
            reflection.setActive(false);
            data.set(PROGRESS, 0);
            return true;
        }

        return false;
    }

    public void cookEnd()
    {
        itr.selfGive(recipeNow.assemble(inventory), slotInfo.o1(), slotInfo.o2(), false);
        shrinkItems();
    }

    @Override
    public int ticks()
    {
        if((upgradeSlots.countUpgrade(LevelupSmoke.class) > 0 && getRecipe(RecipeType.SMOKING, inventory) != null)
                || (upgradeSlots.countUpgrade(LevelupBlast.class) > 0 && getRecipe(RecipeType.BLASTING, inventory) != null)) {
            return ((SmeltingRecipe) recipeNow).getCookingTime() / 4;
        }
        return ((SmeltingRecipe) recipeNow).getCookingTime() / 2;
    }

}
