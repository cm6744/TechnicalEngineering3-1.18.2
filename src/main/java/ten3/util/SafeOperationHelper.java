package ten3.util;

import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.IForgeRegistryEntry;
import ten3.lib.recipe.FormsCombinedIngredient;
import ten3.lib.recipe.FormsCombinedRecipe;

import java.util.*;

public class SafeOperationHelper
{

    public static String regNameOf(IForgeRegistryEntry<?> entry) {

        return Objects.requireNonNull(entry.getRegistryName()).getPath();

    }

    static Random random = new Random();

    @SuppressWarnings("all")
    public static <T> T randomInCollection(Collection<T> col) {

        if(col == null) return null;
        if(col.size() == 0) return null;

        Object[] items = col.toArray();
        Object j = Util.getRandom(items, random);
        return (T) j;

    }

    public static int safeInt(Integer i) {

        if(i == null) return 0;

        return i;

    }

}
