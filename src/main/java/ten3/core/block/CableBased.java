package ten3.core.block;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.Nullable;
import ten3.core.block.mac.Machine4;
import ten3.util.DirectionHelper;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.minecraft.world.level.block.SculkSensorBlock.WATERLOGGED;

public class CableBased extends Machine4 implements SimpleWaterloggedBlock {

    VoxelShape shape = Block.box(3, 3, 3, 13, 13, 13);;

    public CableBased(Material m, SoundType s, String n) {

        super(m, s, n, false);
        registerDefaultState(getStateDefinition().any().setValue(BlockStateProperties.WATERLOGGED, false));

    }

    public static final Map<Direction, IntegerProperty> PROPERTY_MAP;

    static {
        Map<Direction, IntegerProperty> map = Maps.newEnumMap(Direction.class);
        map.put(Direction.NORTH, crt("north"));
        map.put(Direction.EAST, crt("east"));
        map.put(Direction.SOUTH, crt("south"));
        map.put(Direction.WEST, crt("west"));
        map.put(Direction.UP, crt("up"));
        map.put(Direction.DOWN, crt("down"));
        PROPERTY_MAP = Collections.unmodifiableMap(map);
    }

    private static IntegerProperty crt(String name) {
        return IntegerProperty.create(name, 0, 2);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_)
    {
        return shape;
    }

    public void update(Level world, BlockPos pos) {

        BlockState state = world.getBlockState(pos);

        for(Direction facing : Direction.values()) {
            state = state.setValue(PROPERTY_MAP.get(facing), connectType(world, facing, pos));
            //state = state.with!!
        }

        world.setBlock(pos, state, 3);

    }

    //0-none, 1-cable-to-cable 2 cable-to-machine
    public int connectType(@Nonnull Level world, @Nonnull Direction facing, BlockPos pos) {

        BlockState sf = world.getBlockState(pos.offset(facing.getNormal()));

        BlockEntity t = world.getBlockEntity(pos);
        BlockEntity tf = world.getBlockEntity(pos.offset(facing.getNormal()));

        if(tf == null) return 0;
        if(t == null) return 0;

        //pos<
        //t tf
        //->facing

        boolean k = tf.getCapability(CapabilityEnergy.ENERGY, DirectionHelper.safeOps(facing)).isPresent()
                && t.getCapability(CapabilityEnergy.ENERGY, facing).isPresent();

        if(k) {
            if(sf.getBlock() != this) {
                return 2;
            }
            else {
                return 1;
            }
        }

        return 0;

    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return List.of(asItem().getDefaultInstance());
    }

    //WATER LOGGED

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(PROPERTY_MAP.values().toArray(new Property<?>[0]));
        builder.add(Machine4.active);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = defaultBlockState();

        for(Direction facing : Direction.values()) {
            state = state.setValue(PROPERTY_MAP.get(facing), connectType(context.getLevel(), facing, context.getClickedPos()));
        }

        FluidState state2 = context.getLevel().getFluidState(context.getClickedPos());
        return state.setValue(Machine4.active, false)
                .setValue(WATERLOGGED, state2.getType() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState p_51581_) {
        return p_51581_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51581_);
    }

}
