package ten3.core.machine.useenergy.beacon;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementProgress;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;

public class BeaconScreen extends CmScreenMachine {

    public BeaconScreen(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/beacon_simulator.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

    ElementBurnLeft energy;
    ElementProgress progress;

    public void addWidgets() {

        super.addWidgets();

        widgets.add(energy = getDefaultEne());
        widgets.add(progress = new ElementProgress(48, 65, 80, 5, 97, 0, handler, true));

    }

    public void update() {

        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());
        progress.setPer(pProgress());
    }

}
