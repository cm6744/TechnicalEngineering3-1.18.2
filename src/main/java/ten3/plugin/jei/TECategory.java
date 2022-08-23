package ten3.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import ten3.core.recipe.OpportunityRecipe;

import java.util.ArrayList;
import java.util.List;

public abstract class TECategory<T extends OpportunityRecipe<Container>> extends CmCtgr<T> {

    public TECategory(String name, int u, int v, int w, int h, int ru, int rv, int rx, int ry)
    {
        super(name, u, v, w, h, ru, rv, rx, ry);
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
        dr.cacheTime(recipe.time());
        dr.draw(stack, 0, 0);
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation("ten3", n);
    }

}
