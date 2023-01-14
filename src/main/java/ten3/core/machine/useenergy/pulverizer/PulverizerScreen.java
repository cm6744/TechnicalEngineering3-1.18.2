package ten3.core.machine.useenergy.pulverizer;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementFluid;
import ten3.lib.client.element.ElementProgress;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;

public class PulverizerScreen extends CmScreenMachine {

    public PulverizerScreen(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/pulverizer.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

    ElementBurnLeft energy;
    ElementBurnLeft left;
    ElementProgress progress;
    ElementFluid fluid;

    public void addWidgets() {

        super.addWidgets();

        widgets.add(energy = getDefaultEne());
        widgets.add(left = new ElementBurnLeft(45, 48, 13, 13, 14, 0, handler));
        widgets.add(progress = new ElementProgress(76, 35, 22, 16, 27, 32, handler));
        widgets.add(fluid = new ElementFluid(0, -60, 0, 18, 50, 0, 92, handler, true));
    }

    public void update() {
        fluid.update(container);
        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());
        left.setPer(pEnergy());
        progress.setPer(pProgress());

    }

}
