package ten3.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import ten3.init.template.DefBlock;

import java.util.List;
import java.util.Random;

public class OreCm extends DefBlock {

    public OreCm(double hs) {

        super(build(hs, hs, Material.STONE, SoundType.STONE, 2, 0, true));

    }

}
