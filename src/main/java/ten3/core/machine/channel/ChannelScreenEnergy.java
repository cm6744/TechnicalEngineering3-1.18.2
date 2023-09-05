package ten3.core.machine.channel;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import ten3.TConst;
import ten3.core.client.ClientHolder;
import ten3.lib.client.element.ElementBase;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementButton;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;
import ten3.util.ComponentHelper;
import ten3.util.DisplayHelper;
import ten3.util.RenderHelper;

import java.util.ArrayList;
import java.util.List;

public class ChannelScreenEnergy extends ChannelScreen {

    public ChannelScreenEnergy(CmContainerMachine screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, "textures/gui/channel.png");
    }

    ElementBurnLeft energy;

    public void addWidgets() {

        super.addWidgets();
        widgets.add(energy = getDefaultEne());
    }

    public void update() {

        super.update();
        energy.setPer(pEnergy());
        energy.setValue(energy(), maxEnergy());

    }

}
