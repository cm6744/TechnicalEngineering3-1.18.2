package ten3.core.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ten3.core.item.Spanner;
import ten3.core.machine.pole.PoleTile;
import ten3.core.network.Network;
import ten3.core.network.packets.PTCBindPack;
import ten3.core.network.packets.PTCInfoClientPack;
import ten3.util.ItemUtil;

import java.util.*;

public class Pole extends Machine implements EntityBlock, IHasMachineTile {

    VoxelShape shape = Block.box(4, 0, 4, 12, 16, 12);;

    public Pole(Material m, SoundType s, String n) {

        super(m, s, n, false);

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

    static Map<UUID, BlockPos> poses = new HashMap<>();
    static Map<UUID, PoleTile> poles = new HashMap<>();

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if(handIn == InteractionHand.MAIN_HAND && !worldIn.isClientSide()) {
            MachinePostEvent.clickMachineEvent(worldIn, pos, player, hit);
            ItemStack stack = player.getMainHandItem();
            PoleTile tile = (PoleTile) worldIn.getBlockEntity(pos);
            if(stack.getItem() instanceof Spanner && tile != null) {
                UUID uuid = player.getUUID();
                if(ItemUtil.getTag(stack, "mode") == Spanner.Modes.ENERGY.getIndex()) {
                    BlockPos lastPos = poses.get(uuid);
                    if(lastPos == null) {
                        poles.put(uuid, tile);
                        poses.put(uuid, pos);
                    } else {
                        tile.bind = lastPos;//this tile
                        Network.sendToClient(new PTCBindPack(lastPos, pos));
                        poles.get(uuid).bind = pos;//last tile
                        Network.sendToClient(new PTCBindPack(pos, lastPos));
                        poles.remove(uuid);
                        poses.remove(uuid);
                    }
                }
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
