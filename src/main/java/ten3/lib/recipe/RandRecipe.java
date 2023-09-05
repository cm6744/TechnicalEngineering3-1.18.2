package ten3.lib.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public interface RandRecipe<T extends Container> extends IBaseRecipeCm<T> {

    default List<ItemStack> generateItems()
    {
        List<ItemStack> ss = new ArrayList<>();
        for(int i = 0; i < output().size(); i++) {
            FormsCombinedIngredient ing = output().get(i);
            ItemStack s = ing.genItem();
            ss.add(s);
        }
        return ss;
    }

    default List<FluidStack> generateFluids()
    {
        List<FluidStack> ss = new ArrayList<>();
        for(int i = 0; i < output().size(); i++) {
            FormsCombinedIngredient ing = output().get(i);
            ss.add(ing.genFluid());
        }
        return ss;
    }

    List<FormsCombinedIngredient> output();

    @Override
    default ItemStack getResultItem()
    {
        return output().get(0).symbolItem();
    }

}
