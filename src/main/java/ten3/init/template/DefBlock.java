package ten3.init.template;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import ten3.util.KeyUtil;
import ten3.util.ExcUtil;

import java.util.ArrayList;
import java.util.List;

public class DefBlock extends Block {

    public static Properties build(double h, double r, Material m, SoundType s, int l, int light, boolean solid) {

        Properties p = Properties
                .of(m, m.getColor())
                .destroyTime((float) h)
                .explosionResistance((float) r)
                .requiresCorrectToolForDrops()
                .lightLevel((state) -> light)
                .sound(s);

        if(!solid) p.noOcclusion();

        return p;

    }

    public DefBlock(double h, double r, Material m, SoundType s, int l, int light, boolean solid) {

        super(build(h, r, m, s, l, light, solid));

    }

    public DefBlock(Properties p) {
        super(p);
    }

    @Override
    public MutableComponent getName() {

        return KeyUtil.getKey(ExcUtil.regNameOf(this));

    }

}
