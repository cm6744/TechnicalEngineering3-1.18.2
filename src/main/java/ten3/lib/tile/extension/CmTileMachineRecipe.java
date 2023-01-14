package ten3.lib.tile.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.recipe.FormsCombinedIngredient;
import ten3.lib.recipe.IBaseRecipeCm;
import ten3.lib.recipe.RandRecipe;
import ten3.init.RecipeInit;
import ten3.util.KeyUtil;

import java.util.List;

public abstract class CmTileMachineRecipe extends CmTileMachineProcess {

    public enum RecipeCheckType
    {
        INPUT,
        OUTPUT,
        IGNORE
    }

    public RecipeType<? extends Recipe<Container>> recipeType;

    public Recipe<Container> recipeNow;
    Recipe<Container> last;

    public SlotInfo slotInfo;
    boolean hasAdt;

    public CmTileMachineRecipe(BlockPos pos, BlockState state, SlotInfo info) {

        super(pos, state);

        slotInfo = info;
        hasAdt = true;
    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot)
    {
        return getRecipe(recipeType, s) != null;
    }

    public abstract RecipeCheckType slotType(int slot);

    public abstract RecipeCheckType tankType(int tank);

    public void shrinkItems()
    {
        for(int i = slotInfo.ins; i <= slotInfo.ine; i++) {
            ItemStack stack = inventory.getItem(i);
            int c1 = ((IBaseRecipeCm<?>) recipeNow).inputLimit(stack);
            if(!stack.getContainerItem().isEmpty()) {
                ItemStack s2 = stack.getContainerItem();
                s2.setCount(c1);
                Block.popResource(level, worldPosition, s2);
            }
            stack.shrink(c1);
        }
    }

    public void initialRecipeType()
    {
        recipeType = (RecipeType<? extends Recipe<Container>>) RecipeInit.getRcpType(KeyUtil.exceptMachineOrGiveCell(id));
    }

    @Override
    public void update()
    {
        doBaseData();

        if(recipeType == null) {
            initialRecipeType();
        }
        if(reflection.isActive()) {
            recipeNow = getRecipe(recipeType, inventory);
        }
        else if(getTileAliveTime() % 24 == 0) {
            recipeNow = getRecipe(recipeType, inventory);
        }
        if(last != recipeNow) {
            reflection.setActive(false);
            data.set(PROGRESS, 0);
        }

        last = recipeNow;//mustn't return before it

        if(recipeNow == null) {
            reflection.setActive(false);
            data.set(PROGRESS, 0);
            return;
        }

        process();
    }

    public boolean cooking()
    {
        List<FormsCombinedIngredient> fullAdt = ((RandRecipe<Container>) recipeNow).output();

        boolean give = true;
        if(fullAdt == null || fullAdt.isEmpty()) {
            return false;
        }

        List<ItemStack> full = ((RandRecipe<Container>) recipeNow).allOutputItems();
        List<FluidStack> fullFu = ((RandRecipe<Container>) recipeNow).allOutputFluids();

        for(ItemStack ss : full) {
            if(!itr.selfGive(ss, slotInfo.ots, slotInfo.ote, true)) {
                give = false;
            }
        }
        for(FluidStack ss : fullFu) {
            if(!ftr.selfGive(ss, true)) {
                give = false;
            }
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
        List<ItemStack> gen = ((RandRecipe<Container>) recipeNow).generateItems();
        List<FluidStack> genFu = ((RandRecipe<Container>) recipeNow).generateFluids();

        for(ItemStack s : gen) {
            itr.selfGive(s, slotInfo.ots, slotInfo.ote, false);
        }
        for(FluidStack s : genFu) {
            ftr.selfGive(s, false);
        }
        shrinkItems();
    }

}
