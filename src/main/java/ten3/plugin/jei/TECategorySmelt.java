package ten3.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class TECategorySmelt extends CmCtgr<SmeltingRecipe> {

    public TECategorySmelt(String name, int ru, int rv)
    {
        super(name, 0, 0, 105, 60, ru, rv, 40, 23);
    }

    @Override
    public void draw(SmeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
        dr.cacheTime(recipe.getCookingTime());
        dr.draw(stack, 0, 0);
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid()
    {
        return new ResourceLocation("ten3", n);
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends SmeltingRecipe> getRecipeClass()
    {
        return SmeltingRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SmeltingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 8 + 1, 22 + 1)
                .addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 79 + 1, 22 + 1)
                .addItemStack(recipe.assemble(null).copy());
    }

}
