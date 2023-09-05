package ten3.plugin.jei.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import ten3.TConst;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementProgress;
import ten3.plugin.jei.JeiBaseCategory;
import ten3.plugin.jei.JeiVanillaCategory;

import static ten3.lib.tile.CmScreen.handler;

public class CategorySmelter extends JeiVanillaCategory<SmeltingRecipe>
{

    ElementBurnLeft energy;
    ElementProgress progress;
    ElementBurnLeft left;

    public CategorySmelter(RecipeType<SmeltingRecipe> name)
    {
        super(name, 0, 0, 150, 50);
    }

    public void init(SmeltingRecipe recipe, IRecipeLayoutBuilder bd)
    {
        drawer.setHandler(TConst.jeiHandler1);
        drawer.add(progress = new ElementProgress(73, 19, 22, 16, 27, 0, handler));
        drawer.add(energy = new ElementBurnLeft(6, 2, 14, 46, 0, 0, handler));
        drawer.add(left = new ElementBurnLeft(42, 32, 13, 13, 14, 0, handler));
        input(40, 4, 0, recipe, bd);
        output(112, 18, 0, recipe, bd);
    }

    public void updateInfo(SmeltingRecipe recipe)
    {
        dynamic3e(progress, energy, left);
    }

    @SuppressWarnings("removal")
    @Override
    public Class<SmeltingRecipe> getRecipeClass()
    {
        return SmeltingRecipe.class;
    }



}
