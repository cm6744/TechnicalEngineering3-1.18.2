package ten3.lib.client.element;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ten3.util.ComponentHelper;

import java.util.ArrayList;
import java.util.List;

public class ElementBarEnergy extends ElementImage {

    List<Component> list = new ArrayList<>();
    int auc;
    int mxe;
    int eneI;
    int eneO;
    int itmI;
    int itmO;
    int fluI;
    int fluO;

    public ElementBarEnergy(int xr, int y, int w, int h, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(xr, y, w, h, xOff, yOff, resourceLocation);

    }

    public void update(int ch, int fet, int ei, int eo, int ii, int io, int fi, int fo) {
        auc = fet;
        mxe = ch;
        eneI = ei;
        eneO = eo;
        itmI = ii;
        itmO = io;
        fluI = fi;
        fluO = fo;
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY) {
        //no act
    }

    @Override
    public void addToolTip(List<Component> tooltips) {

        list.add(ComponentHelper.translated(ComponentHelper.GOLD, "ten3.info.bar_energy"));

        list.add(ComponentHelper.translated("ten3.info.bar_energy_fact"));
        list.add(ComponentHelper.translated(ComponentHelper.RED, Math.abs(mxe) + " FE/t"));
        list.add(ComponentHelper.translated("ten3.info.bar_energy_max"));
        list.add(ComponentHelper.translated(ComponentHelper.RED, auc + " FE/t"));

        list.add(ComponentHelper.translated("ten3.info.bar_energy_in_max"));
        list.add(ComponentHelper.translated(ComponentHelper.RED, eneI + " FE/t"));
        list.add(ComponentHelper.translated("ten3.info.bar_energy_out_max"));
        list.add(ComponentHelper.translated(ComponentHelper.RED, eneO + " FE/t"));

        list.add(ComponentHelper.translated("ten3.info.bar_item_in_max"));
        list.add(ComponentHelper.translated(ComponentHelper.RED, itmI + " IS/t"));
        list.add(ComponentHelper.translated("ten3.info.bar_item_out_max"));
        list.add(ComponentHelper.translated(ComponentHelper.RED, itmO + " IS/t"));

        list.add(ComponentHelper.translated("ten3.info.bar_fluid_in_max"));
        list.add(ComponentHelper.translated(ComponentHelper.RED, fluI + " mB/t"));
        list.add(ComponentHelper.translated("ten3.info.bar_fluid_out_max"));
        list.add(ComponentHelper.translated(ComponentHelper.RED, fluO + " mB/t"));

        tooltips.addAll(list);

        list.clear();

    }

}
