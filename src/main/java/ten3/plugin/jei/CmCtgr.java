package ten3.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import ten3.TConst;
import ten3.util.KeyUtil;

import java.util.List;

public abstract class CmCtgr<T> implements IRecipeCategory<T> {

    String n;
    ProcessDraw dr;
    IDrawable icon;

    public CmCtgr(String name, int u, int v, int w, int h, int ru, int rv, int rx, int ry)
    {
        n = name;
        dr = new ProcessDraw(u, v, w, h, name, ru, rv, rx, ry);
    }

    @Override
    public Component getTitle()
    {
        return KeyUtil.translated(TConst.modid + ".machine_" + n);
    }

    @Override
    public IDrawable getBackground()
    {
        return dr;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

}
