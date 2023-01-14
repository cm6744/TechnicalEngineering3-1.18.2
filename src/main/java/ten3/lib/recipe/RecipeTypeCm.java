package ten3.lib.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeCm<T extends Recipe<?>> implements RecipeType<T> {

    String resourceLocation;

    public RecipeTypeCm(String rl) {
        resourceLocation = rl;
    }

    @Override
    public String toString()
    {
        return resourceLocation;
    }

}
