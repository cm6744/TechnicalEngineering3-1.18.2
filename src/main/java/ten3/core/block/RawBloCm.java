package ten3.core.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import ten3.init.template.DefBlock;

public class RawBloCm extends DefBlock {

    public RawBloCm(double hs) {

        super(build(hs, hs, Material.STONE, SoundType.STONE, 0, true));

    }

}
