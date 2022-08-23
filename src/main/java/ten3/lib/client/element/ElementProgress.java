package ten3.lib.client.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.client.RenderHelper;
import ten3.lib.wrapper.IntArrayCm;

import java.util.List;

public class ElementProgress extends ElementBase {

    double p;

    public ElementProgress(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(x, y, width, height, xOff, yOff, resourceLocation);

    }

    @Override
    public void draw(PoseStack matrixStack) {

        RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);

        RenderHelper.bindTexture(resourceLocation);
        RenderHelper.render(matrixStack, x, y, (int) (p * width), height, textureW, textureH, xOff, yOff + height, resourceLocation);

    }

    @Override
    public void addToolTip(List<Component> tooltips) {
        //for JEI, disabled
        //tooltips.add(new StringTextComponent((int)(p * 100) + "%"));
    }

    int ie;
    int je;

    public void setEs(int i1, int i2, CmContainerMachine c) {

        IntArrayCm ia = c.data;
        ie = ia.get(i1);
        je = ia.get(i2);

    }

    public void setPer(double per) {

        p = per;

    }

}
