package ten3.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import ten3.lib.recipe.FormsCombinedRecipe;

public class TECategorySgAddition extends TECategory<FormsCombinedRecipe> {

    public TECategorySgAddition(String name, int ru, int rv)
    {
        super(name, 0, 64, 105, 60, ru, rv, 34, 23);
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends FormsCombinedRecipe> getRecipeClass()
    {
        return FormsCombinedRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FormsCombinedRecipe recipe, IFocusGroup group)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 8 + 1, 22 + 1)
                .addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 65 + 1, 13 + 1)
                .addItemStack(recipe.output().get(0).symbolItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 83 + 1, 13 + 1)
                .addItemStack(recipe.output().get(1).symbolItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 65 + 1, 31 + 1)
                .addItemStack(recipe.output().get(2).symbolItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 83 + 1, 31 + 1)
                .addItemStack(recipe.output().get(3).symbolItem());
    }

    @Override
    public void draw(FormsCombinedRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
        super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        chance(74, 6, 0, recipe, stack);
        chance(92, 6, 1, recipe, stack);
        chance(74, 48, 2, recipe, stack);
        chance(92, 48, 3, recipe, stack);
    }

}
