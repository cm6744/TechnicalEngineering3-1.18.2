package ten3.lib.client.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ten3.util.RenderHelper;
import ten3.util.ComponentHelper;

import java.util.List;

public class ElementBar extends ElementBase {

    public boolean state;
    static final int barSize = 21;
    static final int barMax = 90;
    int h = barSize;
    int wait;
    int bw;

    public ElementBar(int xr, int y, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(xr, y, barSize, barSize, xOff, yOff, resourceLocation);
        bw = barSize;

    }

    public ElementBar(int xr, int y, int h, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(xr, y, barSize, h, xOff, yOff, resourceLocation);
        bw = barSize;
        this.h = h;

    }

    Component text;

    public void setTxt(String key) {

        text = ComponentHelper.translated(ComponentHelper.GOLD, key);

    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY) {
        if(wait <= 0) {
            state = !state;
            wait = 4;
        }
    }

    @Override
    public void draw(PoseStack matrixStack) {

        RenderHelper.bindTexture(resourceLocation);
        RenderHelper.render(matrixStack, x - bw, y, bw, h, textureW, textureH, xOff, yOff, resourceLocation);

        wait--;
        if(state) {
            if(bw < barMax) {
                bw = Math.min(barMax, bw + 6);
            }
            else {
                drawAdd(matrixStack);
            }
        } else {
            if(bw > barSize) {
                bw = Math.max(barSize, bw - 6);
            }
            else {
                bw = barSize;
            }
        }

    }

    @Override
    public void addToolTip(List<Component> tooltips) {
        if(text != null)
        tooltips.add(text);
    }

    @Override
    public boolean checkInstr(int mouseX, int mouseY) {

        return (mouseX >= x - bw && mouseY >= y && mouseX <= x - bw + barSize && mouseY <= y + height) ||
                (mouseX >= x - barSize && mouseY >= y && mouseX <= x && mouseY <= y + height);

    }

    public boolean isOpen() {

        return bw >= barMax;

    }

    public void drawAdd(PoseStack s) {



    }

}
