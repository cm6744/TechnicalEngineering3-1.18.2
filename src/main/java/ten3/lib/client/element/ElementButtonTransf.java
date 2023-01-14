package ten3.lib.client.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ten3.lib.client.RenderHelper;
import ten3.lib.tile.option.FaceOption;
import ten3.util.KeyUtil;

import java.util.List;

public class ElementButtonTransf extends ElementButton {

    public int mode;

    public ElementButtonTransf(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation, B_PACK run) {

        super(x, y, width, height, xOff, yOff, resourceLocation, run);

    }

    Component text;

    public void setTxt(String... key) {

        text = KeyUtil.translated(KeyUtil.GOLD, key);

    }

    @Override
    public void addToolTip(List<Component> tooltips) {
        if(text != null) {
            tooltips.add(text);
            tooltips.add(KeyUtil.translated("ten3.info." + FaceOption.toStr(mode)));
        }
    }

    @Override
    public void draw(PoseStack matrixStack) {

        switch(mode) {
            case FaceOption.NONE:
            case FaceOption.OFF:
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);
                break;
            case FaceOption.IN:
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height, resourceLocation);
                break;
            case FaceOption.OUT:
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height*2, resourceLocation);
                break;
            case FaceOption.BE_IN:
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height*3, resourceLocation);
                break;
            case FaceOption.BE_OUT:
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height*4, resourceLocation);
                break;
            case FaceOption.BOTH:
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height*5, resourceLocation);
                break;
        }

    }

}
