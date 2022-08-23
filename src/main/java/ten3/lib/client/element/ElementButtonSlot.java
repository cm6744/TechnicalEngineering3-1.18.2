package ten3.lib.client.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import ten3.lib.client.RenderHelper;
import ten3.util.KeyUtil;

import java.util.List;

public class ElementButtonSlot extends ElementButton {

    public ElementButtonSlot(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation, B_PACK run) {

        super(x, y, width, height, xOff, yOff, resourceLocation, run);

    }

    @Override
    public void addToolTip(List<Component> tooltips) {
        if(!state) {
            tooltips.add(KeyUtil.translated(KeyUtil.RED, "ten3.locked_slot"));
        }
    }

}
