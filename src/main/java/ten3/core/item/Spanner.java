package ten3.core.item;
;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import ten3.core.machine.IHasMachineTile;
import ten3.init.tab.DefGroup;
import ten3.init.template.DefItem;
import ten3.util.ItemNBTHelper;

public class Spanner extends DefItem implements IModeChangable {

    public Spanner() {

        super(build(1, DefGroup.TOOL));

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

    public void change(Player player)
    {
        if(player != null) {
            if(!player.level.isClientSide()) {
                if(!player.isShiftKeyDown()) {
                    ItemStack stack = player.getMainHandItem();
                    int i = ItemNBTHelper.getTag(stack, "mode");
                    i++;
                    if(i >= Modes.size()) {
                        i = 0;
                    }
                    ItemNBTHelper.setTag(stack, "mode", i);
                }
            }
        }
    }

    public enum Modes {
        ENERGY(0),
        ITEM(1),
        REDSTONE(2);
        //BIND(3);

        int index;
        Modes(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public static int size() {
            return 3;
        }
    }

}
