package ten3.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementProgress;

public abstract class JeiCategory<T extends Recipe<Container>> extends JeiBaseCategory<T>
{

    public JeiCategory(RecipeType<T> name, int u, int v, int w, int h)
    {
        super(name, u, v, w, h);
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
        updateInfo(recipe);
        drawer.draw(stack, 0, 0);
    }

    public void dynamic3e(ElementProgress progress, ElementBurnLeft energy, ElementBurnLeft left)
    {
        energy.setPer(energy.getPer() - 0.002);
        if(energy.getPer() <= 0) {
            energy.setPer(1);
        }
        left.setPer(left.getPer() - 0.002);
        if(left.getPer() <= 0) {
            left.setPer(1);
        }
        progress.setPer(progress.getPer() + 0.005);
        if(progress.getPer() >= 1) {
            progress.setPer(0);
        }
    }

    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses)
    {
        super.setRecipe(builder, recipe, focuses);
        drawer.elements.clear();
        init(recipe, builder);
    }

    public abstract void updateInfo(T recipe);

    public abstract void init(T recipe, IRecipeLayoutBuilder bd);

    public abstract void input(int x, int y, int i, T recipe, IRecipeLayoutBuilder bd);

    public abstract void output(int x, int y, int i, T recipe, IRecipeLayoutBuilder bd);

}
