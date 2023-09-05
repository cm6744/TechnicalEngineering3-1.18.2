package ten3.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ten3.core.block.mac.WateredMachine4;
import ten3.core.block.mac.WateredMachine6;

public class Channel extends WateredMachine6
{

    final VoxelShape CEILING = Block.box
            (1, 13, 1, 15, 16, 15);
    final VoxelShape FLOOR = Block.box
            (1, 0, 1, 15, 3, 15);
    final VoxelShape NORTH = Block.box
            (1, 1, 0, 15, 15, 3);
    final VoxelShape SOUTH = Block.box
            (1, 1, 13, 15, 15, 16);
    final VoxelShape WEST = Block.box
            (0, 1, 1, 3, 15, 15);
    final VoxelShape EAST = Block.box
            (13, 1, 1, 16, 15, 15);

    public Channel(String name)
    {
        super(name);
    }

    public VoxelShape nshape(BlockState s)
    {
        switch(s.getValue(direction)) {
            case NORTH -> {
                return NORTH;
            }
            case EAST -> {
                return EAST;
            }
            case WEST -> {
                return WEST;
            }
            case SOUTH -> {
                return SOUTH;
            }
            case UP -> {
                return CEILING;
            }
            case DOWN -> {
                return FLOOR;
            }
        }
        return NORTH;
    }

    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        return nshape(p_60555_);
    }

    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_)
    {
        return nshape(p_60572_);
    }

}
