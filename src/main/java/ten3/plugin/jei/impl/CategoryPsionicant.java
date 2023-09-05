package ten3.plugin.jei.impl;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import ten3.TConst;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementProgress;
import ten3.lib.recipe.FormsCombinedRecipe;
import ten3.plugin.jei.JeiCmCategory;
import ten3.plugin.jei.JeiVanillaCategory;

import static ten3.lib.tile.CmScreen.handler;

public class CategoryPsionicant extends JeiCmCategory
{

    ElementBurnLeft energy;
    ElementProgress progress;
    ElementBurnLeft left;

    public CategoryPsionicant(RecipeType<FormsCombinedRecipe> name)
    {
        super(name, 0, 51, 150, 50);
    }

    public void init(FormsCombinedRecipe recipe, IRecipeLayoutBuilder bd)
    {
        drawer.setHandler(TConst.jeiHandler1);
        drawer.add(progress = new ElementProgress(73, 19, 22, 16, 27, 0, handler));
        drawer.add(energy = new ElementBurnLeft(6, 2, 14, 46, 0, 0, handler));
        drawer.add(left = new ElementBurnLeft(42, 32, 13, 13, 14, 0, handler));
        input(31, 4, 0, recipe, bd);
        input(49, 4, 1, recipe, bd);
        output(112, 18, 0, recipe, bd);
    }

    public void updateInfo(FormsCombinedRecipe recipe)
    {
        dynamic3e(progress, energy, left);
    }

}
