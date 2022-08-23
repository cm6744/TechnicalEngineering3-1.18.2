package ten3.core.machine.useenergy.farm;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;

public class FarmScreen extends CmScreenMachine {

    public FarmScreen(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/farm_manager.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

    ElementBurnLeft energy;

    public void addWidgets() {

        super.addWidgets();

        widgets.add(energy = getDefaultEne());

    }

    public void update() {

        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());

    }

}
