package ten3.core.machine.channel;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import ten3.lib.client.element.ElementFluid;
import ten3.lib.tile.CmContainerMachine;

public class ChannelScreenItem extends ChannelScreen {

    public ChannelScreenItem(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, "textures/gui/channel_item.png");
    }

}
