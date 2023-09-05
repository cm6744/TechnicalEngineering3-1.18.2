package ten3.lib.client.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import ten3.util.RenderHelper;
import ten3.util.ComponentHelper;

import java.util.List;

public class ElementButton extends ElementBase {

    B_PACK run_met;
    public boolean state;

    public ElementButton(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation, B_PACK run) {

        super(x, y, width, height, xOff, yOff, resourceLocation);

        run_met = run;

    }

    Component text;

    public void setTxt(String... key) {

        text = ComponentHelper.translated(ComponentHelper.GOLD, key);

    }

    @Override
    public void addToolTip(List<Component> tooltips) {
        if(text != null)
        tooltips.add(text);
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY) {
        run_met.click();
    }

    boolean nc;

    public ElementButton withNoChange() {
        nc = true;
        return this;
    }

    @Override
    public void draw(PoseStack matrixStack) {

        if(nc) {
            RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);
            return;
        }

        if(hanging) {
            if(state) {
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height * 3, resourceLocation);
            }
            else {
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height, resourceLocation);
            }
        }
        else {
            if(state) {
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff + height * 2, resourceLocation);
            }
            else {
                RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);
            }
        }

    }

    public interface B_PACK {

        void click();

    }

}
