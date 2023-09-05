package ten3.lib.client.element;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import ten3.util.ComponentHelper;

import java.util.List;

public class ElementButtonSlot extends ElementButton {

    public ElementButtonSlot(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation, B_PACK run) {

        super(x, y, width, height, xOff, yOff, resourceLocation, run);

    }

    @Override
    public void addToolTip(List<Component> tooltips) {
        if(!state) {
            tooltips.add(ComponentHelper.translated(ComponentHelper.RED, "ten3.locked_slot"));
        }
    }

}
