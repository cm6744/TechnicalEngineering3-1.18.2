package ten3.lib.client.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import ten3.util.RenderHelper;
import ten3.util.ComponentHelper;
import ten3.util.DisplayHelper;

import java.util.List;

public class ElementBurnLeft extends ElementBase {

    double p;
    boolean dv;

    public ElementBurnLeft(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(x, y, width, height, xOff, yOff, resourceLocation);

    }

    public ElementBurnLeft(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation, boolean displayValue) {

        super(x, y, width, height, xOff, yOff, resourceLocation);
        dv = displayValue;

    }

    @Override
    public void draw(PoseStack matrixStack) {

        int h = (int) (height * (1 - p));
        RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);

        //RenderHelper.bindTexture(resourceLocation);
        RenderHelper.render(matrixStack, x, y + h, width, height - h, textureW, textureH, xOff, yOff + height + (int) (height * (1 - p)), resourceLocation);

    }

    @Override
    public void addToolTip(List<Component> tooltips) {

        if(!dv) {
            tooltips.add(ComponentHelper.make((int) (p * 100) + "%"));
        }
        else {
            tooltips.add(DisplayHelper.join(val, m_val));
        }

    }

    int val;
    int m_val;

    public void setValue(int v, int mv) {
        val = v;
        m_val = mv;
    }

    public void setPer(double per) {
        p = per;
    }

    public double getPer()
    {
        return p;
    }

}
