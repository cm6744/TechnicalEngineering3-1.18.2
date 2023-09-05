package ten3.lib.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.tile.mac.CmTileMachine;

public interface IBaseRecipeCm<T extends Container> extends Recipe<T> {

    int time();

    int inputLimit(ItemStack stack);

    int inputLimit(FluidStack stack);

    default boolean isSpecial()
    {
        return true;
    }

    @Override
    default boolean canCraftInDimensions(int p_43999_, int p_44000_)
    {
        return true;
    }

}
