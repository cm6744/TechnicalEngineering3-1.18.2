package ten3.core.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import ten3.init.template.DefBlock;

public class OreBloCm extends DefBlock {

    public OreBloCm(double hs) {

        super(build(hs, hs, Material.METAL, SoundType.METAL, 0, true));

    }

}
