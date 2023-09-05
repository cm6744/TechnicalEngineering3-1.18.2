package ten3.core.machine.channel;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import ten3.TConst;
import ten3.core.client.ClientHolder;
import ten3.lib.tile.option.FaceOption;
import ten3.util.RenderHelper;
import ten3.lib.client.element.ElementBase;
import ten3.lib.client.element.ElementBurnLeft;
import ten3.lib.client.element.ElementButton;
import ten3.lib.tile.CmContainerMachine;
import ten3.lib.tile.CmScreenMachine;
import ten3.util.ComponentHelper;
import ten3.util.DisplayHelper;

import java.util.ArrayList;
import java.util.List;

public class ChannelScreen extends CmScreenMachine {

    public static class ChannelEntry extends ElementBase {

        boolean isIn;
        BlockPos pos;
        int index;

        public ChannelEntry(int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation)
        {
            super(x, y, width, height, xOff, yOff, resourceLocation);
        }

        public ChannelEntry set(boolean isInput, BlockPos p, int i) {
            isIn = isInput;
            pos = p;
            index = i;
            return this;
        }

        public void draw(PoseStack matrixStack)
        {
            RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);
            //RenderHelper.renderString(matrixStack, x + 12, y + 2, Mth.color(1f, 1f, 1f),
            //                          isIn ?
            //                                  (ComponentHelper.translated("ten3.channel.in"))
            //                                : ComponentHelper.translated("ten3.channel.out"));
            RenderHelper.renderString(matrixStack, x + 6, y + 3, Mth.color(1f, 1f, 1f),
                                      ComponentHelper.translated("ten3.channel")
                                              .append(ComponentHelper.make("#", String.valueOf(index)))
                                              .withStyle(isIn ? ChatFormatting.RED : ChatFormatting.GREEN)
            );
        }

        public void addToolTip(List<Component> tooltips)
        {
            tooltips.add(ComponentHelper.translated("ten3.channel.pos").append(ComponentHelper.make(DisplayHelper.toString(pos))));
            tooltips.add(ComponentHelper.translated(isIn ? "ten3.channel.in" : "ten3.channel.out"));
        }

        public void locate(int x, int y) {
            this.ix = x;
            this.iy = y;
        }

    }

    static ResourceLocation handler =
    new ResourceLocation(TConst.modid, "textures/gui/channel.png");

    public ChannelScreen(CmContainerMachine screenContainer, Inventory inv, Component titleIn, String path) {

        super(screenContainer, inv, titleIn, path, 256, 256);
        xSize = 176;
        ySize = 166;

    }

    protected void setSides()
    {
        front.mode = getModeClient((d) -> d);
        back.mode = FaceOption.NONE;
        left.mode = FaceOption.NONE;
        right.mode = FaceOption.NONE;
        up.mode = FaceOption.NONE;
        down.mode = FaceOption.NONE;
    }

    List<ChannelEntry> entries = new ArrayList<>();
    int cursorFrom;

    public void addWidgets() {

        super.addWidgets();

        int i = 0;
        for(BlockPos pos : ClientHolder.channelInputs.get(container.pos)) {
            if(pos == null) continue;
            entries.add(new ChannelEntry(
                    53, 7 + i * 14, 48, 13, 0, 166,
                    handler
            ).set(true, pos, i));
            i++;
        }
        for(BlockPos pos : ClientHolder.channelOutputs.get(container.pos)) {
            if(pos == null) continue;
            entries.add(new ChannelEntry(
                    53, 7 + i * 14, 48, 13, 0, 179,
                    handler
            ).set(false, pos, i));
            i++;
        }
        widgets.addAll(entries);
        widgets.add(new ElementButton(107, 5, 12, 12, 84, 166, handler, () -> {
            cursorFrom--;
            if(cursorFrom < 0) cursorFrom = 0;
        }));
        widgets.add(new ElementButton(107, 64, 12, 12, 96, 166, handler, () -> {
            if(entries.size() <= 5) return;
            cursorFrom++;
            if(cursorFrom + 5 >= entries.size()) cursorFrom = entries.size() - 5;
        }));
    }

    public void update() {

        for(ChannelEntry e : entries)
            e.setVisible(false);
        for(int i = 0; i < 5; i++)
        {
            if(i + cursorFrom < entries.size()) {
                entries.get(i + cursorFrom).setVisible(true);
                entries.get(i + cursorFrom).locate(53, 7 + i * 14);
            }
        }
        updateIJ();//must invoke

    }

}
