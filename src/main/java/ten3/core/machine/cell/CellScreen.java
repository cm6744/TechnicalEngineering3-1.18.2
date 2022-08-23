package ten3.core.machine.cell;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;
import ten3.lib.client.element.ElementBurnLeft;

public class CellScreen extends CmScreenMachine {

    public CellScreen(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/energy_cell.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

    ElementBurnLeft energy;

    public void addWidgets() {

        super.addWidgets();

        widgets.add(energy = new ElementBurnLeft(81, 18, 14, 46, 0, 0, handler, true));

    }

    public void update() {

        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());

    }

}
