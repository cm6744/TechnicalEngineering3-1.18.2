package ten3.core.machine.useenergy.compressor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import ten3.lib.recipe.IBaseRecipeCm;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.lib.tile.extension.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.TagUtil;

public class CompressorTile extends CmTileMachineRecipe
{

    public CompressorTile(BlockPos pos, BlockState state) {

        super(pos, state, new SlotInfo(0, 1, 2, 2));

        info.setCap(kFE(20));
        setEfficiency(15);

        addSlot(new SlotCustomCm(inventory, 0, 43, 15, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 1, 43, 51, (s) -> TagUtil.containsItem(s.getItem(), "ten3:moulds"), false, false));
        addSlot(new SlotCm(inventory, 2, 115, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    public RecipeCheckType slotType(int slot)
    {
        if(slot == 0 || slot == 1) return RecipeCheckType.INPUT;
        if(slot == 2) return RecipeCheckType.OUTPUT;
        return RecipeCheckType.IGNORE;
    }

    public RecipeCheckType tankType(int tank)
    {
        return RecipeCheckType.IGNORE;
    }

    public void shrinkItems()
    {
        for(int i = slotInfo.ins; i <= slotInfo.ine; i++) {
            if(i == 1) continue;//not shrink mould...
            ItemStack stack = inventory.getItem(i);
            int c1 = ((IBaseRecipeCm<?>) recipeNow).inputLimit(stack);
            if(!stack.getContainerItem().isEmpty()) {
                ItemStack s2 = stack.getContainerItem();
                s2.setCount(c1);
                Block.popResource(level, worldPosition, s2);
            }
            stack.shrink(c1);
        }
    }

    @Override
    public int ticks() {
        return ((IBaseRecipeCm<Container>) recipeNow).time();
    }

}
