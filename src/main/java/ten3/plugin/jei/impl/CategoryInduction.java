package ten3.plugin.jei.impl;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeType;
import ten3.TConst;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementProgress;
import ten3.lib.recipe.FormsCombinedRecipe;
import ten3.plugin.jei.JeiCmCategory;

import static ten3.lib.tile.CmScreen.handler;

public class CategoryInduction extends JeiCmCategory
{

    ElementBurnLeft energy;
    ElementProgress progress;
    ElementBurnLeft left;

    public CategoryInduction(RecipeType<FormsCombinedRecipe> name)
    {
        super(name, 0, 0, 150, 50);
    }

    public void init(FormsCombinedRecipe recipe, IRecipeLayoutBuilder bd)
    {
        drawer.setHandler(TConst.jeiHandler2);
        drawer.add(progress = new ElementProgress(90, 19, 22, 16, 27, 0, handler));
        drawer.add(energy = new ElementBurnLeft(6, 2, 14, 46, 0, 0, handler));
        drawer.add(left = new ElementBurnLeft(51, 32, 13, 13, 14, 0, handler));
        input(30, 4, 0, recipe, bd);
        input(48, 4, 1, recipe, bd);
        input(66, 4, 2, recipe, bd);
        output(124, 18, 0, recipe, bd);
    }

    public void updateInfo(FormsCombinedRecipe recipe)
    {
        dynamic3e(progress, energy, left);
    }

}
