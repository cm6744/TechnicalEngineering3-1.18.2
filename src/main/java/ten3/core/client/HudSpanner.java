package ten3.core.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ten3.core.machine.pole.PoleTile;
import ten3.lib.tile.option.RedstoneMode;
import ten3.lib.tile.extension.CmTileMachineRadiused;
import ten3.util.*;
import ten3.core.item.Spanner;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.RenderHelper;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HudSpanner extends Screen {

    static int w;
    static int h;

    public HudSpanner() {
        super(ComponentHelper.make(""));
    }

    public void render(boolean catchIt, Player player, PoseStack s, BlockPos pos, BlockEntity t, Direction d) {

        s.pushPose();

        w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        init(Minecraft.getInstance(), w, h);//&*&

        Component tc = ComponentHelper.translated("ten3.info.spanner.mode", "ten3.info.mode." + ItemNBTHelper.getTag(player.getMainHandItem(), "mode"));

        int hp = player.isCreative() ? (int) (h / 3 * 2.6) : (int) (h / 3 * 2.42);
        //RenderHelper.render(s, w / 2 - 29, hp - 3, 58, 13, 256, 256, 0, 198, TConst.guiHandler);
        RenderHelper.renderCString(s, w / 2, hp, SafeOperationHelper.safeInt(ComponentHelper.GOLD.getColor()), tc);

        if(!catchIt) return;

        MutableComponent c1 = ComponentHelper.translated("ten3.info.spanner.dire.energy");
        MutableComponent c2 = ComponentHelper.translated("ten3.info.spanner.dire.item");
        MutableComponent c25 = ComponentHelper.translated("ten3.info.spanner.dire.fluid");
        MutableComponent c3 = ComponentHelper.translated("ten3.info.spanner.dire.redstone");
        MutableComponent c4 = ComponentHelper.translated("ten3.info.spanner.work_radius")
                .append(ComponentHelper.make(
                        String.valueOf(SafeOperationHelper.safeInt(ClientHolder.radius.get(pos)))
                ));
        MutableComponent c5 = ComponentHelper.translated("ten3.info.spanner.bind_pos")
                .append(ComponentHelper.make(
                        String.valueOf(ClientHolder.binds.get(pos))
                ));
        MutableComponent c0 = ((CmTileMachine) t).getDisplayWith()
                .append(ComponentHelper.make(" ("))
                .append(ComponentHelper.translated("dire." + d.getSerializedName()))
                .append(ComponentHelper.make(")"));

        int red = ClientHolder.redstone.get(pos);

        int di = DirectionHelper.direToInt(d);

        c1.append(ComponentHelper.translated("ten3.info." + FaceOption.toStr(ClientHolder.energy.getOrFill(pos, 6).get(di))));
        c2.append(ComponentHelper.translated("ten3.info." + FaceOption.toStr(ClientHolder.item.getOrFill(pos, 6).get(di))));
        c25.append(ComponentHelper.translated("ten3.info." + FaceOption.toStr(ClientHolder.fluid.getOrFill(pos, 6).get(di))));

        if(red == RedstoneMode.LOW) {
            c3.append(ComponentHelper.translated("ten3.info.low"));
        }
        else if(red == RedstoneMode.HIGH) {
            c3.append(ComponentHelper.translated("ten3.info.high"));
        }
        else {
            c3.append(ComponentHelper.translated("ten3.info.off"));
        }

        int x = w / 2;
        int y = h / 2 + h / 10;

        List<MutableComponent> components = new ArrayList<>();
        components.add(c0);
        components.add(c1);
        components.add(c2);
        components.add(c3);
        if(t instanceof CmTileMachineRadiused) {
            components.add(c4);
        }
        if(t instanceof PoleTile && ClientHolder.binds.get(pos) != null) {
            components.add(c5);
        }

        renderComponentTooltip(s, components, x, y, Minecraft.getInstance().font);

        s.popPose();

    }

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent e) {

        if(e.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        Player player = Minecraft.getInstance().player;
        if(player == null) return;

        ItemStack i = player.getMainHandItem();
        if(!(i.getItem() instanceof Spanner)) return;

        if(e.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        Level world = player.level;
        if(world == null) return;
        HitResult result = Minecraft.getInstance().hitResult;
        if(result instanceof BlockHitResult r) {
            Direction d = r.getDirection();
            BlockPos hitPos = r.getBlockPos();
            BlockEntity t = world.getBlockEntity(hitPos);
            new HudSpanner().render(t instanceof CmTileMachine, player, e.getMatrixStack(), hitPos, t, d);
            if(Minecraft.getInstance().isPaused()) return;
            ParticleSpawner.spawnClt(ParticleSpawner.RANGE,
                    hitPos.getX() + Math.random(),
                    hitPos.getY() + Math.random(),
                    hitPos.getZ() + Math.random(),
                    1);

        }

    }

}
