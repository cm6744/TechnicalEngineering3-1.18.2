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

import java.util.*;

public class ExcUtil {

    public static String regNameOf(IForgeRegistryEntry<?> entry) {

        return Objects.requireNonNull(entry.getRegistryName()).getPath();

    }

    public static<C extends Container, T extends Recipe<C>> Optional<T> safeGetRecipe(Level world, RecipeType<T> type, C i) {

        if(world == null) return Optional.empty();

        RecipeManager rm = world.getRecipeManager();
        return rm.getRecipeFor(type, i, world);

    }

    public static Collection<? extends Recipe<Container>> safeGetRecipes(Level world, RecipeType<? extends Recipe<Container>> type) {

        if(world == null) return new ArrayList<>();

        RecipeManager rm = world.getRecipeManager();
        return rm.getAllRecipesFor(type);

    }

    public static boolean hasRcpUseThisItem(Level world, RecipeType<? extends Recipe<Container>> type, ItemStack stack) {
        return hasRcpUseThisItem(world, type, safeGetRecipes(world, type), stack);
    }

    public static boolean hasRcpUseThisItem(Level world, RecipeType<? extends Recipe<Container>> type, Collection<? extends Recipe<Container>> recipes, ItemStack stack) {
        final boolean[] ret = new boolean[1];
        recipes.forEach((r) -> {
            NonNullList<Ingredient> ings = r.getIngredients();
            ings.forEach((i) -> {
                if(i.test(stack)) {
                    ret[0] = true;
                }
            });
        });
        return ret[0];
    }

    public static Collection<Recipe<Container>> getRcpUseThisItem(Level world, RecipeType<? extends Recipe<Container>> type, ItemStack stack) {
        Collection<? extends Recipe<Container>> recipes = ExcUtil.safeGetRecipes(world, type);
        Collection<Recipe<Container>> ret = new ArrayList<>();
        recipes.forEach((r) -> {
            NonNullList<Ingredient> ings = r.getIngredients();
            ings.forEach((i) -> {
                if(i.test(stack)) {
                    ret.add(r);
                }
            });
        });
        return ret;
    }

    static Random random = new Random();

    @SuppressWarnings("all")
    public static <T> T randomInCollection(Collection<T> col) {

        Object[] items = col.toArray();
        Object j = Util.getRandom(items, random);
        return (T) j;

    }

    public static int safeInt(Integer i) {

        if(i == null) return 0;

        return i;

    }

}
