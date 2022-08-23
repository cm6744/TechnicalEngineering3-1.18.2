package ten3.core.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface OpportunityRecipe<T extends Container> extends IBaseRecipeCm<T> {

    default ItemStack generateAddition() {
        if(Math.random() <= chance()) {
            return getAdditionOutput().copy();
        }
        return ItemStack.EMPTY;
    }

    ItemStack getAdditionOutput();

    double chance();

}
