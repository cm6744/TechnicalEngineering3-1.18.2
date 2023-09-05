package ten3.lib.tile.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import ten3.core.client.IntMap;
import ten3.lib.capability.item.AdvancedInventory;
import ten3.lib.recipe.FormsCombinedIngredient;
import ten3.lib.recipe.FormsCombinedRecipe;
import ten3.lib.recipe.IBaseRecipeCm;
import ten3.lib.recipe.RandRecipe;
import ten3.init.RecipeInit;
import ten3.util.ComponentHelper;
import ten3.util.RecipeHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CmTileMachineRecipe<T extends Recipe<Container>> extends CmTileMachineProcess {

    public RecipeType<T> recipeType;

    public T recipeNow;
    T last;

    public SlotInfo slotInfo;

    public CmTileMachineRecipe(BlockPos pos, BlockState state, SlotInfo info) {

        super(pos, state);

        slotInfo = info;
    }

    @Nullable
    public<E extends Recipe<Container>> E getRecipe(RecipeType<E> type, ItemStack... stacks)
    {
        /*                                       @see FormsCombinedRecipe#matches*/
        return RecipeHelper.safeGetRecipe(level, type, new AdvancedInventory(stacks, slots, this)).orElse(null);
    }

    @Nullable
    public<E extends Recipe<Container>> E getRecipe(RecipeType<E> type, AdvancedInventory inv)
    {
        return RecipeHelper.safeGetRecipe(level, type, inv).orElse(null);
    }

    public int ticks()
    {
        return ((FormsCombinedRecipe) recipeNow).time();
    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot)
    {
        return getRecipe(recipeType, s) != null;
    }

    @Override
    public boolean customFitStackIn(FluidStack s, int tank)
    {
        return false;
    }

    //NEED TO OVERRIDE IF USE OTHER RECIPE
    public void shrinkItems()
    {
        List<Object> shrinked = new ArrayList<>();

        for(int i = slotInfo.i1(); i <= slotInfo.i2(); i++) {
            if(inventory.getContainerSize() == 0) break;

            ItemStack stack = inventory.getItem(i);

            if(stack.isEmpty()) continue;
            if(!canShrink(stack, i)) continue;
            if(shrinked.contains(stack.getItem())) continue;

            int c1 = ((IBaseRecipeCm<?>) recipeNow).inputLimit(stack);
            stack.shrink(c1);
            shrinked.add(stack.getItem());
        }
        for(int i = slotInfo.fi1(); i <= slotInfo.fi2(); i++) {
            if(tanks.isEmpty()) break;

            FluidStack stack = tanks.get(i).getFluid();

            if(stack.isEmpty()) continue;
            if(!canShrink(stack, i)) continue;
            if(shrinked.contains(stack.getFluid())) continue;

            int c1 = ((IBaseRecipeCm<?>) recipeNow).inputLimit(stack);
            stack.shrink(c1);
            shrinked.add(stack.getFluid());
        }
    }

    public boolean canShrink(ItemStack i, int slot)
    {
        return true;
    }

    public boolean canShrink(FluidStack i, int tank)
    {
        return true;
    }

    @SuppressWarnings("unchecked")
    public void initialRecipeType()
    {
        recipeType = (RecipeType<T>) RecipeInit.getRcpType(ComponentHelper.exceptMachineOrGiveCell(id));
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

    //NEED TO OVERRIDE IF USE OTHER RECIPE
    public boolean cooking()
    {
        List<FormsCombinedIngredient> fullAdt = ((FormsCombinedRecipe) recipeNow).output();

        boolean give = true;
        if(fullAdt == null || fullAdt.isEmpty()) {
            return false;
        }

        List<FormsCombinedIngredient> full = ((FormsCombinedRecipe) recipeNow).allOutputItems();
        List<FormsCombinedIngredient> fullFu = ((FormsCombinedRecipe) recipeNow).allOutputFluids();

        for(FormsCombinedIngredient ss : full) {
            if(!itr.selfGive(ss.symbolItem(), slotInfo.o1(), slotInfo.o2(), true)) {
                give = false;
            }
        }
        for(FormsCombinedIngredient ss : fullFu) {
            if(!ftr.selfGive(ss.symbolFluid(), slotInfo.fo1(), slotInfo.fo2(), true)) {
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

    //NEED TO OVERRIDE IF USE OTHER RECIPE
    public void cookEnd()
    {
        List<ItemStack> gen = ((FormsCombinedRecipe) recipeNow).generateItems();
        List<FluidStack> genFu = ((FormsCombinedRecipe) recipeNow).generateFluids();

        for(ItemStack s : gen) {
            itr.selfGive(s, slotInfo.o1(), slotInfo.o2(), false);
        }
        for(FluidStack s : genFu) {
            ftr.selfGive(s, slotInfo.fo1(), slotInfo.fo2(), false);
        }
        shrinkItems();
    }

}
