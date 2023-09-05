package ten3.core.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ten3.core.item.Connector;
import ten3.util.RenderHelper;
import ten3.util.ComponentHelper;
import ten3.util.DisplayHelper;
import ten3.util.SafeOperationHelper;
import ten3.util.ItemNBTHelper;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HudConnector extends Screen {

    static int w;
    static int h;

    public HudConnector() {
        super(ComponentHelper.make(""));
    }

    public void render(Player player, PoseStack s) {

        s.pushPose();

        w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        init(Minecraft.getInstance(), w, h);//&*&

        Component in = ComponentHelper.translated("ten3.channel_connector.mode.in");
        Component out = ComponentHelper.translated("ten3.channel_connector.mode.out");
        Component rem = ComponentHelper.translated("ten3.channel_connector.mode.rem");
        Component trueVal = ComponentHelper.make("");
        Component pos = ComponentHelper.make("");
        switch(Connector.Modes.parse(player.getMainHandItem())) {
            case IN -> trueVal = in;
            case OUT -> trueVal = out;
            case REMOVE -> trueVal = rem;
            //default -> trueVal = rem;
        }
        if(ItemNBTHelper.getTag(player.getMainHandItem(), "hasLast") == 1) {
            Component i1 = ComponentHelper.make(DisplayHelper.toString(
                    BlockPos.of((long) ItemNBTHelper.getTagD(player.getMainHandItem(), "last"))
            ));
            pos = ComponentHelper.translated("ten3.channel.pointer_last").append(i1);
        }

        int hp = player.isCreative() ? (int) (h / 3 * 2.6) : (int) (h / 3 * 2.42);
        //RenderHelper.render(s, w / 2 - 29, hp - 3, 58, 13, 256, 256, 0, 198, TConst.guiHandler);
        RenderHelper.renderCString(s, w / 2, hp, SafeOperationHelper.safeInt(ComponentHelper.GOLD.getColor()), trueVal);
       // RenderHelper.renderCString(s, w / 2, h / 2 + 8, SafeOperationHelper.safeInt(ComponentHelper.GOLD.getColor()), pos);

        s.popPose();

    }

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent e) {

        if(e.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        Player player = Minecraft.getInstance().player;
        if(player == null) return;

        ItemStack i = player.getMainHandItem();
        if(!(i.getItem() instanceof Connector)) return;

        new HudConnector().render(player, e.getMatrixStack());
    }

}
