package ten3.core.machine.useenergy.refiner;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementFluid;
import ten3.lib.client.element.ElementProgress;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;

public class RefinerScreen extends CmScreenMachine {

    public RefinerScreen(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/one_to_one_fluid.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

    ElementBurnLeft energy;
    ElementBurnLeft left;
    ElementProgress progress;
    ElementFluid fluidi;
    ElementFluid fluido;

    public void addWidgets() {

        super.addWidgets();

        widgets.add(energy = getDefaultEne());
        widgets.add(left = new ElementBurnLeft(60, 56, 13, 13, 14, 0, handler));
        widgets.add(progress = new ElementProgress(84, 35, 22, 16, 27, 159, handler, false));
        widgets.add(fluidi = new ElementFluid(37, 17, 18, 50, 0, 92, handler, 0, true));
        widgets.add(fluido = new ElementFluid(143, 17, 18, 50, 0, 92, handler, 1, true));
    }

    public void update() {

        fluidi.update(container);
        fluido.update(container);
        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());
        left.setPer(pEnergy());
        progress.setPer(pProgress());

    }

}
