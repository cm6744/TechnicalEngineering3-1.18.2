package ten3.core.machine.useenergy.condenser;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementFluid;
import ten3.lib.client.element.ElementProgress;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;

public class CondenserScreen extends CmScreenMachine {

    public CondenserScreen(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/matter_condenser.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

    ElementBurnLeft energy;
    //ElementBurnLeft left;
    ElementProgress progress;
    ElementFluid fluido;

    public void addWidgets() {

        super.addWidgets();

        widgets.add(energy = getDefaultEne());
        //widgets.add(left = new ElementBurnLeft(45, 36, 13, 13, 14, 0, handler));
        widgets.add(progress = new ElementProgress(48, 57, 80, 5, 97, 0, handler, true));
        widgets.add(fluido = new ElementFluid(143, 17, 18, 50, 0, 92, handler, 0, true));
    }

    public void update() {

        fluido.update(container);
        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());
        //left.setPer(pEnergy());
        progress.setPer(pProgress());

    }

}
