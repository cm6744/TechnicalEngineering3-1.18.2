package ten3.core.item;
;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.machine.IHasMachineTile;
import ten3.core.machine.Machine;
import ten3.core.machine.Cable;
import ten3.init.template.DefItem;
import ten3.util.ItemUtil;

public class Spanner extends DefItem {

    public Spanner() {

        super(1);

    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return (state.getBlock() instanceof IHasMachineTile) ? 16 : 1;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state)
    {
        return state.getBlock() instanceof IHasMachineTile;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx)
    {
        Player player = ctx.getPlayer();
        if(player != null) {
            if(ctx.getHand() == InteractionHand.MAIN_HAND && !player.level.isClientSide()) {
                if(player.isShiftKeyDown()) {
                    ItemStack stack = player.getMainHandItem();
                    int i = ItemUtil.getTag(stack, "mode");
                    i++;
                    if(i >= Modes.size()) {
                        i = 0;
                    }
                    ItemUtil.setTag(stack, "mode", i);
                    ItemUtil.setTag(stack, "bindX", Integer.MAX_VALUE);
                    ItemUtil.setTag(stack, "bindY", Integer.MAX_VALUE);
                    ItemUtil.setTag(stack, "bindZ", Integer.MAX_VALUE);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.FAIL;
    }

    public enum Modes {
        ENERGY(0),
        ITEM(1),
        REDSTONE(2),
        RANGE(3);

        int index;
        Modes(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public static int size() {
            return 4;
        }
    }

}
