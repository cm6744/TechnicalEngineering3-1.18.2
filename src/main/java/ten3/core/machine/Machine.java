package ten3.core.machine;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import ten3.core.item.energy.EnergyItemHelper;
import ten3.init.template.DefBlock;
import ten3.init.TileInit;
import ten3.lib.tile.CmTileEntity;
import ten3.lib.tile.CmTileMachine;

import java.util.List;

public class Machine extends DefBlock implements EntityBlock, IHasMachineTile {

    public static DirectionProperty dire = DirectionProperty.create("facing", (direction) -> direction != Direction.UP && direction != Direction.DOWN);
    public static BooleanProperty active = BooleanProperty.create("active");

    String tileName;

    public Machine(String name) {

        this(Material.METAL, SoundType.STONE, name, true);
        tileName = name;

    }

    public Machine(Material m, SoundType s, String name, boolean solid) {

        super(3, 5, m, s, 2, 0, solid);
        tileName = name;

    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return TileInit.getType(tileName).create(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_)
    {
        return (p1, p2, p3, p4) -> ((CmTileEntity) p4).serverTick();
    }

    //See:TileMachine
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        CmTileMachine tile = ((CmTileMachine) builder.getParameter(LootContextParams.BLOCK_ENTITY));
        
        if(tile == null) {
            return Lists.newArrayList(asItem().getDefaultInstance());
        }

        ItemStack stack = EnergyItemHelper.fromMachine(tile, asItem().getDefaultInstance());

        List<ItemStack> ret = Lists.newArrayList(stack);
        ret.addAll(tile.drops());

        return ret;

    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player)
    {
        return EnergyItemHelper.fromMachine((CmTileMachine) newBlockEntity(pos, state), asItem().getDefaultInstance());
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState p_49849_, @Nullable LivingEntity p_49850_, ItemStack stack)
    {
        super.setPlacedBy(worldIn, pos, p_49849_, p_49850_, stack);
        CmTileMachine tile = (CmTileMachine) worldIn.getBlockEntity(pos);
        if(tile != null) {
            EnergyItemHelper.pushToTile(tile, stack);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(active);
        builder.add(dire);
    }

    //with facing on place


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState()
                .setValue(dire, context.getHorizontalDirection().getOpposite())
                .setValue(active, false);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(handIn == InteractionHand.MAIN_HAND && !worldIn.isClientSide()) {
            if(MachinePostEvent.clickMachineEvent(worldIn, pos, player, hit)) {
                CmTileMachine tile = (CmTileMachine)worldIn.getBlockEntity(pos);
                if(tile == null) {
                    return InteractionResult.FAIL;
                }

                NetworkHooks.openGui((ServerPlayer) player, tile, (FriendlyByteBuf packerBuffer) -> {
                    packerBuffer.writeBlockPos(tile.getBlockPos());
                });
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction)
    {
        return true;
    }

}
