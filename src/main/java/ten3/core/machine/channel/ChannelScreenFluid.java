package ten3.core.machine.channel;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import ten3.TConst;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementFluid;
import ten3.lib.tile.CmContainerMachine;

public class ChannelScreenFluid extends ChannelScreen {

    public ChannelScreenFluid(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, "textures/gui/channel.png");
    }

    ElementFluid fluid1;
    ElementFluid fluid2;

    public void addWidgets() {

        super.addWidgets();
        widgets.add(fluid1 = new ElementFluid(7, 17, 18, 50, 0, 92, TConst.guiHandler, 0, true));
        widgets.add(fluid2 = new ElementFluid(25, 17, 18, 50, 0, 92, TConst.guiHandler, 1, true));
    }

    public void update() {

        super.update();
        fluid1.update(container);
        fluid2.update(container);

    }

}
