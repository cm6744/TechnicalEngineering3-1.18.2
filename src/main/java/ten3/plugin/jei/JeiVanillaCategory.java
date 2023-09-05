package ten3.plugin.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import ten3.lib.recipe.FormsCombinedRecipe;

import java.util.List;

public abstract class JeiVanillaCategory<T extends Recipe<Container>> extends JeiCategory<T>
{

    public JeiVanillaCategory(RecipeType<T> name, int u, int v, int w, int h)
    {
        super(name, u, v, w, h);
    }

    public void input(int x, int y, int i, T recipe, IRecipeLayoutBuilder bd)
    {
        bd.addSlot(RecipeIngredientRole.INPUT, x + 1, y + 1)
                .addIngredients(recipe.getIngredients().get(i));
    }

    public void output(int x, int y, int i, T recipe, IRecipeLayoutBuilder bd)
    {
        bd.addSlot(RecipeIngredientRole.OUTPUT, x + 1, y + 1)
                .addItemStack(recipe.getResultItem().copy());
    }

}
