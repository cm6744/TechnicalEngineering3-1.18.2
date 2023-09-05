package ten3.init.template;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import ten3.init.FluidInit;

import java.util.function.Supplier;

public class FluidBlock extends LiquidBlock
{

    public FluidBlock(String getter)
    {
        super(() -> FluidInit.getSource(getter), Properties.of(Material.WATER)
                .explosionResistance(100)
                .destroyTime(100)
                .noDrops());
    }

}
