package ten3.util;

import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.capability.fluid.Tank;
import ten3.lib.recipe.FormsCombinedIngredient;
import ten3.lib.recipe.FormsCombinedRecipe;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.lib.tile.mac.CmTileMachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class RecipeHelper
{

    public static int recipeNeedCount(Item item, FormsCombinedRecipe recipe)
    {
        int c = 0;
        for(FormsCombinedIngredient ing : recipe.input()) {
            if(ing.contains(item)) {
                c += ing.amountOrCount();
            }
        }
        return c;
    }

    public static int recipeNeedCount(Fluid fluid, FormsCombinedRecipe recipe)
    {
        int c = 0;
        for(FormsCombinedIngredient ing : recipe.input()) {
            if(ing.contains(fluid)) {
                c += ing.amountOrCount();
            }
        }
        return c;
    }

    //REMEMBER:
    //a recipe should not have 2 slots same stacks.
    public static boolean checkItemNotExistInTile(CmTileMachineRecipe<?> tile, ItemStack stack)
    {
        List<ItemStack> ss = tile.inventory.getStackInRange(tile.slotInfo.i1(), tile.slotInfo.i2());
        for(ItemStack s : ss)
        {
            if(s.getItem() == stack.getItem()
            && s.getCount() + stack.getCount() > s.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    //REMEMBER:
    //a recipe should not have 2 tanks same stacks.
    public static boolean checkFluidNotExistInTile(CmTileMachineRecipe<?> tile, FluidStack stack)
    {
        for(int i = tile.slotInfo.fi1(); i <= tile.slotInfo.fi2(); i++) {
            Tank tk = tile.tanks.get(i);
            if(tk.getFluid().getFluid() == stack.getFluid()
            && tk.getFluidAmount() + stack.getAmount() > tk.getCapacity()) {
                return false;
            }
        }
        return true;
    }

    public static<C extends Container, T extends Recipe<C>> Optional<T>
    safeGetRecipe(Level world, RecipeType<T> type, C i) {

        if(world == null) return Optional.empty();

        RecipeManager rm = world.getRecipeManager();
        return rm.getRecipeFor(type, i, world);

    }

    public static<T extends Recipe<Container>> Collection<T>
    safeGetRecipes(Level world, RecipeType<T> type) {

        if(world == null) return new ArrayList<>();

        RecipeManager rm = world.getRecipeManager();
        return rm.getAllRecipesFor(type);

    }

    public static List<FormsCombinedRecipe>
    getRecipeUsing(Level w, RecipeType<FormsCombinedRecipe> rty, ItemStack stack)
    {
        return getRecipeUsing(safeGetRecipes(w, rty), stack);
    }

    public static List<FormsCombinedRecipe>
    getRecipeUsing(Collection<FormsCombinedRecipe> org, ItemStack stack)
    {
        List<FormsCombinedRecipe> ret = new ArrayList<>();
        org.forEach((r) -> {
            for(FormsCombinedIngredient ing : r.input()) {
                if(stack.isEmpty() || ing.contains(stack.getItem())) {
                    ret.add(r);
                    break;
                }
            }
        });
        return ret;
    }

    public static List<FormsCombinedRecipe>
    getRecipeUsing(Level w, RecipeType<FormsCombinedRecipe> rty, FluidStack stack)
    {
        return getRecipeUsing(safeGetRecipes(w, rty), stack);
    }

    public static List<FormsCombinedRecipe>
    getRecipeUsing(Collection<FormsCombinedRecipe> org, FluidStack stack)
    {
        List<FormsCombinedRecipe> ret = new ArrayList<>();
        org.forEach((r) -> {
            for(FormsCombinedIngredient ing : r.input()) {
                if(stack.isEmpty() || ing.contains(stack.getFluid())) {
                    ret.add(r);
                }
            }
        });
        return ret;
    }

}
