package ten3.plugin.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import ten3.core.recipe.MTSRecipe;

public class TECategorySg3 extends TECategory<MTSRecipe> {

    public TECategorySg3(String name, int ru, int rv)
    {
        super(name, 0, 128, 105, 60, ru, rv, 45, 23);
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends MTSRecipe> getRecipeClass()
    {
        return MTSRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MTSRecipe recipe, IFocusGroup group)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 11 + 1, 13 + 1).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 2 + 1, 31 + 1).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 20 + 1, 31 + 1).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 79 + 1, 22 + 1).addItemStack(recipe.assemble(null));
    }

}
