package ten3.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import ten3.core.recipe.MTSRecipe;
import ten3.core.recipe.SingleRecipe;

public class TECategorySgAddition extends TECategory<SingleRecipe> {

    public TECategorySgAddition(String name, int ru, int rv)
    {
        super(name, 0, 64, 105, 60, ru, rv, 34, 23);
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends SingleRecipe> getRecipeClass()
    {
        return SingleRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SingleRecipe recipe, IFocusGroup group)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 8 + 1, 22 + 1).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 65 + 1, 13 + 1).addItemStack(recipe.assemble(null));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 83 + 1, 13 + 1).addItemStack(recipe.getAdditionOutput().copy());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 65 + 1, 31 + 1);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 83 + 1, 31 + 1);
    }

    @Override
    public void draw(SingleRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
        dr.cacheTime(recipe.time());
        dr.cacheOpt(recipe.chance());
        dr.draw(stack, 0, 0);
    }

}
