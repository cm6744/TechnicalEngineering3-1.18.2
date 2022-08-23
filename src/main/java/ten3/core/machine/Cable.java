package ten3.core.machine;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.Nullable;
import ten3.core.machine.Machine;
import ten3.core.machine.MachinePostEvent;
import ten3.init.template.DefBlock;
import ten3.init.TileInit;
import ten3.lib.tile.CmTileEntity;
import ten3.util.DireUtil;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Cable extends DefBlock implements EntityBlock, IHasMachineTile {

    String name;
    VoxelShape shape = Block.box(3, 3, 3, 13, 13, 13);;

    public Cable(Material m, SoundType s, String n) {

        super(DefBlock.build(1.5, 2, m, s, 2, 0, false));
        name = n;

    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_)
    {
        return TileInit.getType(name).create(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> tile)
    {
        return (p1, p2, p3, p4) -> ((CmTileEntity) p4).serverTick();
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(PROPERTY_MAP.values().toArray(new Property<?>[0]));
        builder.add(Machine.active);
    }

    public void update(Level world, BlockPos pos) {

        BlockState state = world.getBlockState(pos);

        for(Direction facing : Direction.values()) {
            state = state.setValue(PROPERTY_MAP.get(facing), connectType(world, facing, pos));
            //state = state.with!!
        }

        world.setBlock(pos, state, 3);

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = defaultBlockState();

        for(Direction facing : Direction.values()) {
            state = state.setValue(PROPERTY_MAP.get(facing), connectType(context.getLevel(), facing, context.getClickedPos()));
        }

        return state.setValue(Machine.active, false);
    }

    //0-none, 1-cable-to-cable 2 cable-to-machine
    private int connectType(@Nonnull Level world, @Nonnull Direction facing, BlockPos pos) {

        BlockState sf = world.getBlockState(pos.offset(facing.getNormal()));

        BlockEntity t = world.getBlockEntity(pos);
        BlockEntity tf = world.getBlockEntity(pos.offset(facing.getNormal()));

        if(tf == null) return 0;
        if(t == null) return 0;

        //pos<
        //t tf
        //->facing

        boolean k = tf.getCapability(CapabilityEnergy.ENERGY, DireUtil.safeOps(facing)).isPresent()
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
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(handIn == InteractionHand.MAIN_HAND && !worldIn.isClientSide()) {
            if(MachinePostEvent.clickMachineEvent(worldIn, pos, player, hit)) {
                return InteractionResult.PASS;
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return List.of(asItem().getDefaultInstance());
    }

}
