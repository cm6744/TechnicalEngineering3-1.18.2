package ten3.lib.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.gui.GuiUtils;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import ten3.lib.client.element.ElementBase;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.gui.GuiComponent.*;

public class RenderHelper {

    public static void drawAll(ArrayList<ElementBase> widgets, PoseStack matrixStack) {

        for(int i = 0; i < widgets.size(); i++) {
            if(widgets.get(i).isVisible())
                widgets.get(i).draw(matrixStack);
        }

    }

    public static void updateAll(ArrayList<ElementBase> widgets) {

        for(int i = 0; i < widgets.size(); i++) {
            if(widgets.get(i).isVisible())
            widgets.get(i).update();
        }

    }

    public static void hangingAll(ArrayList<ElementBase> widgets, boolean hang, int mouseX, int mouseY) {

        for(int i = 0; i < widgets.size(); i++) {
            if(widgets.get(i).isVisible())
            widgets.get(i).hangingEvent(hang, mouseX, mouseY);
        }

    }

    public static void clickAll(ArrayList<ElementBase> widgets, int mouseX, int mouseY) {

        //ItemStack ret = stack;

        for(int i = 0; i < widgets.size(); i++) {
            if (widgets.get(i).checkInstr(mouseX, mouseY) && widgets.get(i).isVisible()) {
                /*
                if(widgets.getRcp(i) instanceof ElementSlot) {
                    ItemStack in = ((ElementSlot) widgets.getRcp(i)).item;
                    if(!stack.isEmpty() && in.isEmpty()) {
                        in = stack.copy();
                        ret = ItemStack.EMPTY;
                    }
                    else if(stack.isEmpty() && !in.isEmpty()) {
                        ret = in.copy();
                        in.setCount(0);
                    }
                    else if(!stack.isEmpty() && !in.isEmpty()) {
                        if(stack.getRcp() != in.getRcp()) {
                            ItemStack cac = stack.copy();
                            ret = in.copy();
                            in = cac;
                        }
                    }
                }
                 */
                widgets.get(i).onMouseClicked(mouseX, mouseY);
            }
        }

        //return ret;

    }

    public static void bindTexture(ResourceLocation resourceLocation) {

        RenderSystem.setShaderTexture(0, resourceLocation);

    }

    public static void renderBackGround(PoseStack matrixStack, int i, int j, int textureW, int textureH, ResourceLocation resourceLocation) {

        bindTexture(resourceLocation);
        render(matrixStack,i, j, 176, 166, textureW, textureH, 0, 0, resourceLocation);

    }

    public static void renderBackGround(PoseStack matrixStack, int w, int h, int i, int j, int textureW, int textureH, ResourceLocation resourceLocation) {

        bindTexture(resourceLocation);
        render(matrixStack,i, j, w, h, textureW, textureH, 0, 0, resourceLocation);

    }

    public static void render(PoseStack matrixStack, int x, int y, int width, int height, int textureW, int textureH, int xOff, int yOff, ResourceLocation resourceLocation) {

        bindTexture(resourceLocation);
        blit(matrixStack, x, y, xOff, yOff, width, height, textureW, textureH);

    }

    public static void renderString(PoseStack matrixStack, int x, int y, int color, Component str) {

        Font font = Minecraft.getInstance().font;
        drawString(matrixStack, font, str, x, y, color);

    }

    public static void renderCString(PoseStack matrixStack, int x, int y, int color, Component str) {

        Font font = Minecraft.getInstance().font;
        drawCenteredString(matrixStack, font, str, x, y, color);

    }

}
