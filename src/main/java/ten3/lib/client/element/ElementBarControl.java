package ten3.lib.client.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ten3.util.ComponentHelper;

import java.util.List;

public class ElementBarControl extends ElementImage {

    public boolean show;
    public ElementButton panel;

    public ElementBarControl(int xr, int y, int w, int h, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(xr, y, w, h, xOff, yOff, resourceLocation);
        panel = new ElementButton(
                -60 - 1, y, 60, 85, 91, 40, resourceLocation, () -> {
                    show = !show;
        }
        ) {
            @Override
            public boolean checkInstr(int mouseX, int mouseY)
            {
                return mouseX >= x + width - 10 && mouseX <= x + width
                        && mouseY >= y && mouseY <= y + 10;
            }
        };

    }

    @Override
    public void updateLocWhenFrameResize(int i, int j)
    {
        super.updateLocWhenFrameResize(i, j);
        panel.updateLocWhenFrameResize(i, j);
    }

    @Override
    public boolean checkInstr(int mouseX, int mouseY)
    {
        if(!show)
        return super.checkInstr(mouseX, mouseY);
            return panel.checkInstr(mouseX, mouseY);
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY) {
        show = !show;
    }

    @Override
    public void draw(PoseStack matrixStack)
    {
        if(!show) {
            super.draw(matrixStack);
        }
        else {
            panel.draw(matrixStack);
        }
    }

    @Override
    public void addToolTip(List<Component> tooltips) {
        tooltips.add(ComponentHelper.translated(ComponentHelper.GOLD, "ten3.info.bar_control"));
    }

}
