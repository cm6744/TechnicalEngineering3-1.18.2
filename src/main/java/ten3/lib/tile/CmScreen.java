package ten3.lib.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;
import ten3.TConst;
import ten3.lib.client.GuiHelper;
import ten3.lib.client.RenderHelper;
import ten3.lib.client.element.ElementBase;
import ten3.lib.wrapper.IntArrayCm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ten3.lib.tile.CmTileMachine.*;

public class CmScreen<T extends CmContainer> extends AbstractContainerScreen<T> {

    //all widgets
    public static final ResourceLocation handler = TConst.guiHandler;
    public final ResourceLocation BG;
    protected int texh;
    protected int texw;
    public int xSize;
    public int ySize;

    public int getYSize()
    {
        return ySize;
    }

    public int getXSize()
    {
        return xSize;
    }

    protected final ArrayList<ElementBase> widgets = new ArrayList<>();
    protected final List<Component> tooltips = new LinkedList<>();

    public T container;

    public CmScreen(T container, Inventory inv, Component titleIn, String path, int textureW, int textureH) {

        super(container, inv, titleIn);
        BG = new ResourceLocation(TConst.modid, path);
        texh = textureH;
        texw = textureW;
        this.container = container;

    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {

        renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, partialTick);

        RenderHelper.drawAll(widgets, matrixStack);
        RenderHelper.updateAll(widgets);
        RenderHelper.hangingAll(widgets, true, mouseX, mouseY);

        ElementBase element = getElementFromLocation(mouseX, mouseY);
        if (element != null) {
            element.addToolTip(tooltips);
        }

        renderComponentTooltip(matrixStack, tooltips, mouseX, mouseY);
        renderTooltip(matrixStack, mouseX, mouseY);

        tooltips.clear();

    }

    boolean init;

    public void addWidgets() {}

    @Override
    protected void init() {

        super.init();

        if(!init) addWidgets();
        init = true;

        int i = GuiHelper.getI(width, getXSize());
        int j = GuiHelper.getJ(height, getYSize());

        ElementBase e;
        for(int k = 0; k < widgets.size(); k++) {
            e = widgets.get(k);
            e.updateLocWhenFrameResize(i, j);
        }

    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {

        int i = GuiHelper.getI(width, getXSize());
        int j = GuiHelper.getJ(height, getYSize());

        renderBackground(matrixStack);

        RenderHelper.renderBackGround(matrixStack, i, j, texw, texh, BG);

    }

    public ElementBase getElementFromLocation(int mouseX, int mouseY) {

        for (int i = 0; i < widgets.size(); i++) {
            ElementBase element = widgets.get(i);
            if (element.checkInstr(mouseX, mouseY) && element.isVisible()) {
                return element;
            }
        }
        return null;

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if(button == GLFW.GLFW_MOUSE_BUTTON_1) {
            RenderHelper.clickAll(widgets, (int) mouseX, (int) mouseY);
        }

        return super.mouseClicked(mouseX, mouseY, button);

    }

    public double pFuel() {

        IntArrayCm data = container.data;

        if(data.get(MAX_FUEL) != 0) {
            return ((double) data.get(FUEL)) / data.get(MAX_FUEL);
        }

        return 0;

    }

    public double pProgress() {

        IntArrayCm data = container.data;

        if(data.get(MAX_PROGRESS) != 0) {
            return ((double) data.get(PROGRESS)) / data.get(MAX_PROGRESS);
        }

        return 0;

    }

    public double pEnergy() {

        IntArrayCm data = container.data;

        if(data.get(MAX_ENERGY) != 0) {
            return ((double) data.get(ENERGY)) / data.get(MAX_ENERGY);
        }

        return 0;

    }

    public int energy() {

        IntArrayCm data = container.data;

        return data.get(ENERGY);

    }

    public int maxEnergy() {

        IntArrayCm data = container.data;

        return data.get(MAX_ENERGY);

    }

}
