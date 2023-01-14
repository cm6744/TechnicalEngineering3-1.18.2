package ten3.lib.client.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import ten3.lib.client.RenderHelper;
import ten3.lib.tile.CmContainerMachine;
import ten3.util.KeyUtil;
import ten3.util.PatternUtil;

import java.util.List;

public class ElementFluid extends ElementBase {

    double p;
    boolean dv;
    int id;
    FluidStack stack;

    public ElementFluid(int id, int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation) {

        super(x, y, width, height, xOff, yOff, resourceLocation);
        this.id = id;
    }

    public ElementFluid(int id, int x, int y, int width, int height, int xOff, int yOff, ResourceLocation resourceLocation, boolean displayValue) {

        super(x, y, width, height, xOff, yOff, resourceLocation);
        dv = displayValue;
        this.id = id;
    }

    public void update(CmContainerMachine ct)
    {
        Fluid fid = Registry.FLUID.byId(ct.fluidData.get(id));
        int amt = ct.fluidAmount.get(id);
        stack = new FluidStack(fid, amt);
        setValue(amt, ct.tile.tanks.get(id).getCapacity());
        setPer(val / (double)m_val);
    }

    @Override
    public void draw(PoseStack matrixStack) {

        int h = (int) ((height - 2) * (1 - p));
        RenderHelper.render(matrixStack, x, y, width, height, textureW, textureH, xOff, yOff, resourceLocation);

        if(stack == null || stack.isEmpty()) return;
        RenderHelper.drawFlTil(matrixStack, stack.getFluid(), x + 1, y + 1 + h, width - 2, height - h - 2);
    }

    @Override
    public void addToolTip(List<Component> tooltips) {

        if(!dv) {
            tooltips.add(KeyUtil.make((int) (p * 100) + "%"));
        }
        else {
            tooltips.add(PatternUtil.joinmB(val, m_val));
        }

    }

    int val;
    int m_val;

    public void setValue(int v, int mv) {

        val = v;
        m_val = mv;

    }

    public void setPer(double per) {

        p = per;

    }

}
