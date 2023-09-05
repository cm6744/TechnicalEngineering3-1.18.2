package ten3.init.template;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import ten3.util.ComponentHelper;
import ten3.util.SafeOperationHelper;

import java.util.function.ToIntFunction;

public class DefBlock extends Block {

    public static Properties build(double h, double r, Material m, SoundType s, ToIntFunction<BlockState> fcl, boolean solid) {

        Properties p = Properties
                .of(m, m.getColor())
                .destroyTime((float) h)
                .explosionResistance((float) r)
                .requiresCorrectToolForDrops()
                .lightLevel(fcl)
                .sound(s);

        if(!solid) p.noOcclusion();

        return p;

    }

    public static Properties build(double h, double r, Material m, SoundType s, int light, boolean solid) {

        return build(h, r, m, s, (sts) -> light, solid);

    }

    public DefBlock(double h, double r, Material m, SoundType s, int light, boolean solid) {

        super(build(h, r, m, s, light, solid));

    }

    public DefBlock(Properties p) {
        super(p);
    }

    @Override
    public String getDescriptionId()
    {
        return ComponentHelper.getKey(SafeOperationHelper.regNameOf(this));
    }

}
