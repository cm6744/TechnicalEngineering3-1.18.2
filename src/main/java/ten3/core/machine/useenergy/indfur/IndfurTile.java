package ten3.core.machine.useenergy.indfur;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import ten3.lib.recipe.IBaseRecipeCm;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.lib.tile.extension.SlotInfo;
import ten3.lib.wrapper.SlotCm;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ExcUtil;

import java.util.Collection;
import java.util.List;

import static ten3.util.ExcUtil.hasRcpUseThisItem;

public class IndfurTile extends CmTileMachineRecipe
{

    public IndfurTile(BlockPos pos, BlockState state) {

        super(pos, state, new SlotInfo(0, 2, 3, 3));

        info.setCap(kFE(20));
        setEfficiency(35);

        addSlot(new SlotCustomCm(inventory, 0, 33, 20, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 1, 51, 20, (s) -> true, false, true));
        addSlot(new SlotCustomCm(inventory, 2, 69, 20, (s) -> true, false, true));
        addSlot(new SlotCm(inventory, 3, 127, 34, SlotCm.RECEIVE_ALL_INPUT, true, false).withIsResultSlot());
    }

    @Override
    public boolean customFitStackIn(ItemStack s, int slot)
    {
        List<ItemStack> lst = inventory.getStackInRange(0, 2);
        for(ItemStack s2 : lst) {
            if(s2.getItem() == s.getItem()) return false;
        }
        if(lst.size() == 0) {
            return ExcUtil.hasRcpUseThisItem(level, recipeType, s);
        }
        Collection<Recipe<Container>> recs = ExcUtil.getRcpUseThisItem(level, recipeType, lst.get(0));
        return ExcUtil.hasRcpUseThisItem(level, recipeType, recs, s);
    }

    public RecipeCheckType slotType(int slot)
    {
        if(slot == 0 || slot == 1 || slot == 2) return RecipeCheckType.INPUT;
        if(slot == 3) return RecipeCheckType.OUTPUT;
        return RecipeCheckType.IGNORE;
    }

    public RecipeCheckType tankType(int tank)
    {
        return RecipeCheckType.IGNORE;
    }

    @Override
    public int ticks() {
        return ((IBaseRecipeCm<?>) recipeNow).time();
    }

}
