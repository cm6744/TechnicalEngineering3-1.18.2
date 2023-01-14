package ten3.core.machine.useenergy.mobrip;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementProgress;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;

public class MobRipScreen extends CmScreenMachine {

    public MobRipScreen(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/mob_ripper.png", 256, 256);
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
