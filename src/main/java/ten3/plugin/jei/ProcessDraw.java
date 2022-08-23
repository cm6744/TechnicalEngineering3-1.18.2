package ten3.plugin.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.util.Mth;
import ten3.lib.client.RenderHelper;
import ten3.lib.client.element.ElementProgress;
import ten3.util.KeyUtil;

import static ten3.TConst.guiHandler;
import static ten3.TConst.jeiHandler;

public class ProcessDraw implements IDrawable {
    int u, v, wi, hi, ru, rv, rx, ry;
    double per;
    String n;
    double chance;
    public ProcessDraw(int u, int v, int w, int h, String name, int rowU, int rowV, int rowX, int rowY) {
        wi = w;hi = h;ru = rowU;rv = rowV;rx = rowX;ry = rowY;n = name;this.u=u;this.v=v;
        progress = new ElementProgress(rx, ry, 22, 16, ru, rv, guiHandler);
    }

    public void cacheTime(int t) {
        per = 1.0 / t;
    }

    public void cacheOpt(double opt) {
        chance = opt;
    }

    @Override
    public int getWidth()
    {
        return wi;
    }

    @Override
    public int getHeight()
    {
        return hi;
    }

    double p;
    ElementProgress progress;

    @Override
    public void draw(PoseStack matrixStack, int i, int i1)
    {
        p += per * 0.2;
        if(p >= 1) p = 0;

        RenderHelper.render(matrixStack, 0, 0, wi, hi, 256, 256, u, v, jeiHandler);

        progress.draw(matrixStack);
        progress.setPer(p);

        if(chance > 0) {
            RenderHelper.renderString(matrixStack, 2, 50, Mth.color(1f, 1f, 1f),
                    KeyUtil.translated("ten3.jei_addition_chance")
                            .append(KeyUtil.make((int)(chance * 100) + "%")));
        }
    }
}
