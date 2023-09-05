package ten3.core.block.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import ten3.init.FluidInit;

import java.util.List;

public class NullFluidTicker implements FluidInit.FluidTicker
{

    public void tick(Level level, BlockPos pos, FluidState state)
    {
    }

}
