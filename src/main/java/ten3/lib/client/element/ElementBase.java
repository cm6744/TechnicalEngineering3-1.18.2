package ten3.lib.client.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ElementBase {

    protected int ix;
    protected int iy;
    protected int x;
    protected int y;
    protected int textureW;
    protected int textureH;
    protected int xOff;
    protected int yOff;
    protected int width;
    protected int height;
    protected boolean visible = true;
    public boolean hanging;

    protected ResourceLocation resourceLocation;

    public void updateLocWhenFrameResize(int i, int j) {

        x = ix + i;
        y = iy + j;

    }

    //image
    public ElementBase(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation) {

        this.x = ix = x;
        this.y = iy = y;

        this.width = width;
        this.height = height;
        this.xOff = xOff;
        this.yOff = yOff;
        this.textureW = 256;
        this.textureH = 256;
        this.resourceLocation = resourceLocation;

    }

    public void draw(PoseStack matrixStack) {}

    public void update() {}

    public void addToolTip(List<Component> tooltips) {}

    public void onMouseClicked(int mouseX, int mouseY) {}

    public void hangingEvent(boolean hang, int mouseX, int mouseY) {

        hanging = hang && checkInstr(mouseX, mouseY);

    }

    public boolean checkInstr(int mouseX, int mouseY) {

        return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;

    }

    public void setVisible(boolean v) {

        this.visible = v;

    }

    public boolean isVisible() {

        return visible;

    }

}
