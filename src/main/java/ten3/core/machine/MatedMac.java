package ten3.core.machine;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class MatedMac extends Machine implements SimpleWaterloggedBlock {

    public MatedMac(String name) {

        this(false, name);

    }

    public MatedMac(boolean solid, String name) {

        this(Material.METAL, SoundType.METAL, name, solid);

    }

    public MatedMac(Material m, SoundType s, String name, boolean solid) {

        super(m, s, name, solid);
        registerDefaultState(getStateDefinition().any().setValue(WATERLOGGED, false));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        FluidState state = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context)
                .setValue(WATERLOGGED, state.getType() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState p_51581_) {
        return p_51581_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51581_);
    }

}
