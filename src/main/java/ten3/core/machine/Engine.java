package ten3.core.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ten3.core.machine.Machine;

public class Engine extends Machine {

    static VoxelShape bound = Block.box(0, 0, 0, 16, 9, 16);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        return bound;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_)
    {
        return bound;
    }

    public Engine(String name) {

        this(false, name);

    }

    public Engine(boolean solid, String name) {

        this(Material.METAL, SoundType.STONE, name, solid);

    }

    public Engine(Material m, SoundType s, String name, boolean solid) {

        super(m, s, name, solid);

    }

}
