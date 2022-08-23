package ten3.plugin.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import ten3.core.recipe.SingleRecipe;

public class TECategorySg extends TECategory<SingleRecipe> {

    public TECategorySg(String name, int ru, int rv)
    {
        super(name, 0, 0, 105, 60, ru, rv, 40, 23);
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends SingleRecipe> getRecipeClass()
    {
        return SingleRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SingleRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 8 + 1, 22 + 1).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 79 + 1, 22 + 1).addItemStack(recipe.assemble(null));
    }

}
