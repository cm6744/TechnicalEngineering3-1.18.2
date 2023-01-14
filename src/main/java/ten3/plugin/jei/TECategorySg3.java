package ten3.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import ten3.lib.recipe.FormsCombinedRecipe;

public class TECategorySg3 extends TECategory<FormsCombinedRecipe> {

    public TECategorySg3(String name, int ru, int rv)
    {
        super(name, 0, 128, 105, 60, ru, rv, 45, 23);
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends FormsCombinedRecipe> getRecipeClass()
    {
        return FormsCombinedRecipe.class;
    }

    public void draw(FormsCombinedRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
        super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        chance(82, 18, 0, recipe, stack);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FormsCombinedRecipe recipe, IFocusGroup group)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 11 + 1, 13 + 1)
                .addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 2 + 1, 31 + 1)
                .addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 20 + 1, 31 + 1)
                .addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 79 + 1, 22 + 1)
                .addItemStack(recipe.output().get(0).symbolItem());
    }

}
