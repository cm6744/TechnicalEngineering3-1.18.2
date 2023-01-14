package ten3.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import ten3.lib.recipe.RandRecipe;
import ten3.lib.client.RenderHelper;

public abstract class TECategory<T extends RandRecipe<Container>> extends CmCtgr<T> {

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

    protected void chance(int x, int y, int i, T recipe, PoseStack s)
    {
        if(recipe.output().size() <= i) return;
        if(recipe.output().get(i).chance() >= 1) return;
        RenderHelper.renderCString
                (s, x, y,
                 Mth.color(1f, 0.9f, 0f),
                 new TextComponent(((int)(recipe.output().get(i).chance() * 100)) + "%")
                );
    }

}
