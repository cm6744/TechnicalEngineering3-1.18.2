package ten3.core.machine.pipe;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreen;
import ten3.lib.tile.CmScreenMachine;

public class PipeScreen extends CmScreen<CmContainerMachine> {

    public PipeScreen(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {

        super(screenContainer, inv, titleIn, "textures/gui/pipe.png", 256, 256);
        xSize = 176;
        ySize = 166;

    }

}
