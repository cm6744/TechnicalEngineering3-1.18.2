package ten3.lib.client.element;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import ten3.util.KeyUtil;

import java.util.ArrayList;
import java.util.List;

public class ElementBarIdeas extends ElementImage {

    List<Component> list = new ArrayList<>();

    public ElementBarIdeas(int xr, int y, int w, int h, int xOff, int yOff, ResourceLocation resourceLocation, String key) {

        super(xr, y, w, h, xOff, yOff, resourceLocation);

        list.add(KeyUtil.translated(KeyUtil.GOLD, "ten3.info.bar_ideas"));

        for(int i = 0; true; i++) {
            String k = "ten3.info." + KeyUtil.exceptMachineOrGiveCell(key) + "."+i;
            Component ttc = KeyUtil.translated(k);
            if(ttc.getString().equals(k)) break;

            list.add(ttc);
        }

    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY) {
        //no act
    }

    @Override
    public void addToolTip(List<Component> tooltips) {

        tooltips.addAll(list);

    }

}
