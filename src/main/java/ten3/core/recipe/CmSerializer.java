package ten3.core.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface CmSerializer<T extends Recipe<?>> extends RecipeSerializer<T> {

    public static int fallBackTime = 150;

    String id();

}
