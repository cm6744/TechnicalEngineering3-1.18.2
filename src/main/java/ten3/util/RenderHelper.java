package ten3.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
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


    static ResourceLocation SHADER_BLOCK = new ResourceLocation("textures/atlas/blocks.png");

    public static TextureAtlasSprite getSprite(ResourceLocation rl)
    {
        return Minecraft.getInstance()
                .getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS)
                .getSprite(rl);
    }

    //* stole from CoFH Core *
    public static void drawFlTil(PoseStack matrixStack, Fluid fluid, int x, int y, int width, int height)
    {
        if(fluid == Fluids.EMPTY) return;
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        int color = fluid.getAttributes().getColor();
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        RenderSystem.setShaderColor(red, green, blue, 1.0F);
        RenderSystem.setShaderTexture(0, SHADER_BLOCK);

        int drawHeight;
        int drawWidth;

        for (int i = 0; i < width; i += 16) {
            for (int j = 0; j < height; j += 16) {
                drawWidth = Math.min(width - i, 16);
                drawHeight = Math.min(height - j, 16);
                drawSprite(matrixStack, getSprite(fluid.getAttributes().getStillTexture()), x + i, y + j, drawWidth, drawHeight);
            }
        }

        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawSprite(PoseStack matrixStack, TextureAtlasSprite icon, int x, int y, int width, int height)
    {
        float minU = icon.getU0();
        float maxU = icon.getU1();
        float minV = icon.getV0();
        float maxV = icon.getV1();

        float u = minU + (maxU - minU) * width / 16F;
        float v = minV + (maxV - minV) * height / 16F;

        Matrix4f matrix = matrixStack.last().pose();

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(matrix, x, y + height, 0).uv(minU, v).endVertex();
        buffer.vertex(matrix, x + width, y + height, 0).uv(u, v).endVertex();
        buffer.vertex(matrix, x + width, y, 0).uv(u, minV).endVertex();
        buffer.vertex(matrix, x, y, 0).uv(minU, minV).endVertex();
        Tesselator.getInstance().end();
    }

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
                if(widgets.getRecipe(i) instanceof ElementSlot) {
                    ItemStack in = ((ElementSlot) widgets.getRecipe(i)).item;
                    if(!stack.isEmpty() && in.isEmpty()) {
                        in = stack.copy();
                        ret = ItemStack.EMPTY;
                    }
                    else if(stack.isEmpty() && !in.isEmpty()) {
                        ret = in.copy();
                        in.setCount(0);
                    }
                    else if(!stack.isEmpty() && !in.isEmpty()) {
                        if(stack.getRecipe() != in.getRecipe()) {
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
